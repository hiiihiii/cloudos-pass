"use strict";
define([
    'jquery',
    "vue",
    "echarts",
    "common-module",
    "bootstrap",
    "bootstrapSwitch",
    "select2",
    "jpages"
],function ($, Vue, echarts, common_module, bootstrap, bootstrapSwitch, select2, jpages) {
    if($("#appstore")[0]){
        var appstore = new Vue({
            el: "#appstore",
            data: {
                appType: "public",
                showPublic: true,
                showPrivate: false,
                imageInfos: [],
                templateInfos: [],
                selectedImage: '',
                selectedTemplate: '',
                imageDetailBind: {
                    sourceUrl:"",
                    requests:{},
                    limits :{},
                    env :[],
                    ports:[],
                    volume:"",
                    cmd:"",
                    cmdParams:[],
                    v_description:""
                },
                templateDetailBind: {
                    appVersion :"",
                    sourceUrl:'',
                    requests:'',
                    limits:'',
                    volume:'',
                    cmd:'',
                    cmdParams:[],
                    env:[],
                    ports:[]
                },
                imageDetail: false,
                templateDetail: false
            },
            mounted: function () {
                var _self = this;
                _self.showPublic = true;
                _self.showPrivate = false;
                //设置分类全部选中
                $(".app-items .cloud-checkbox").attr("checked", "true");
                //为分类选择绑定事件
                _self.classifyBind();

                //获取镜像和模板信息
                _self.getImageData("public");
                _self.getTemplate("public");
                Vue.nextTick(function(){
                    _self.initJpages("#appholder", "appcontainer");
                });
            },
            methods: {
                //显示上传镜像框框
                showUploadDialog: function () {
                    //select2初始化
                    $("#upload_image_form select[name='appTag']").select2({
                        dropdownParent: $('#upload_image'),
                        minimumResultsForSearch: -1//去除搜索框
                    });
                    $("#upload_image_form select[name='appTag']").val("");
                    //开关按钮初始化
                    $("#upload_image_form input[type='checkbox']").bootstrapSwitch({
                        onText: '',
                        offText: '',
                        onSwitchChange: function (event, state) {
                            if(state){

                            } else {

                            }
                        }
                    });
                    $("#upload_image").modal({backdrop: 'static', keyboard: false});
                },

                //显示部署镜像框框
                showDeployImage: function (appid) {
                    var _self = this;
                    for(var i = 0; i < _self.imageInfos.length; i++){
                        if(_self.imageInfos[i].app_id == appid) {
                            //与部署模态框共享信息
                            sessionStorage.setItem("deployImage", JSON.stringify(_self.imageInfos[i]));
                            break;
                        }
                    }
                    $("#deploy_image").modal({backdrop: 'static', keyboard: false});
                },

                //显示部署镜像框框
                showDeployTemplate: function (templateid) {
                    var _self = this;
                    for(var i = 0; i < _self.templateInfos.length; i++){
                        if(_self.templateInfos[i].uuid === templateid) {
                            sessionStorage.setItem("deployTemplate", JSON.stringify(_self.templateInfos[i]));
                            break;
                        }
                    }
                    $("#deploy_template").modal({backdrop: 'static', keyboard: false});
                },

                //设置默认图标
                setDefault: function(event){
                    var $this = $(event.target);
                    $this.attr("src", "../images/app-default.png");
                },

                classifyBind: function(){
                    $("#appstore .nav").on("change", "input[type='checkbox']", function(){
                        var status = this.getAttribute("checked");//选中时status为"checked"
                        var isPublic = false;
                        var isAll = false;
                        if($(this).parent().parent().attr("id") === "public_repo") {
                            isPublic = true;
                        }
                        if($(this).hasClass("app-type-all")){
                            isAll = true;
                        }
                    });
                },

                //获取镜像数据
                getImageData: function (repoType) {
                    var _self = this;
                    $(".loading").css("display", "block");
                    $.ajax({
                        url: "../appcenter/imageinfo",
                        type: "get",
                        data: {
                            repoType: repoType
                        },
                        dataType: "json",
                        async: false,
                        success: function (data) {
                            _self.imageInfos = _self.convertData(data.data, 'image');
                            console.log(_self.imageInfos);
                            common_module.notify("[应用中心]","获取镜像数据成功", "success");
                            setTimeout(function () {
                                $(".loading").css("display", "none");
                            }, 1000);
                        },
                        error: function () {
                            common_module.notify("[应用中心]","获取镜像数据失败", "danger");
                            setTimeout(function () {
                                $(".loading").css("display", "none");
                            }, 1000);
                        }
                    })
                },

                //获取模板信息
                getTemplate: function (repoType) {
                    var _self = this;
                    $.ajax({
                        url: "../appcenter/templateinfo",
                        type: 'get',
                        dataType: 'json',
                        data: {
                            repoType: repoType
                        },
                        async: false,
                        success: function (data) {
                            if(data.code === "success"){
                                _self.templateInfos = _self.convertData(data.data, 'template');
                                console.log(_self.templateInfos);
                                common_module.notify('[应用中心]', '获取模板信息成功', 'success');
                            } else {
                                common_module.notify('[应用中心]', '获取模板信息失败', 'fail');
                            }
                        },
                        error: function () {
                            common_module.notify('[应用中心]', '获取模板信息失败', 'fail');
                        }
                    });
                },

                convertData: function(dataArray, type){
                    if(type === 'image'){
                        for(var i = 0; i < dataArray.length; i++){
                            dataArray[i].createType = JSON.parse(dataArray[i].createType);
                            dataArray[i].metadata = JSON.parse(dataArray[i].metadata);
                            dataArray[i].source_url = JSON.parse(dataArray[i].source_url);
                            dataArray[i].v_description = JSON.parse(dataArray[i].v_description);
                            dataArray[i].version = JSON.parse(dataArray[i].version);
                            dataArray[i].logo_url = "ftp://docker:dockerfile@" + dataArray[i].logo_url;
                            dataArray[i].appType = "docker";
                            if(dataArray[i].appName.length > 6) {
                                dataArray[i].temp_name = dataArray[i].appName.slice(0,6) + '...';
                            } else {
                                dataArray[i].temp_name = dataArray[i].appName;
                            }
                        }
                    } else {
                        for(var i = 0; i < dataArray.length; i++) {
                            dataArray[i].relation = JSON.parse(dataArray[i].relation);
                            dataArray[i].config = JSON.parse(dataArray[i].config);
                            dataArray[i].temp_logo_url = "ftp://docker:dockerfile@" + dataArray[i].logo_url;
                            dataArray[i].appType = 'template';
                            if(dataArray[i].templateName.length > 6) {
                                dataArray[i].temp_name = dataArray[i].templateName.slice(0,6) + '...';
                            } else {
                                dataArray[i].temp_name = dataArray[i].templateName;
                            }
                        }
                    }
                    return dataArray;
                },

                //初始化jpages插件
                initJpages:function (holderid, containerid) {
                    $(holderid).jPages({
                        containerID : containerid,
                        first: "《",
                        last: "》",
                        previous: "《",
                        next: "》",
                        perPage : 8, //每页显示数据为多少个
                        startPage : 1, //起始页
                        startRange : 2, //开始页码为2个
                        midRange : 3, //最多显示几个页码页码,其余用..代替
                        endRange : 2 //结束页码为2个,
                    });
                },

                //切换仓库
                changeRepo: function (repoType, event) {
                    var _self = this;
                    $("#appstore .app-detail").css("display", "none");
                    _self.appType = repoType;
                    $(event.target).next().find("input").each(function (i, element) {
                        $(element).prop("checked",true);
                    });
                    if(repoType === 'public'){
                        _self.showPrivate = false;
                        _self.showPublic = true;
                        _self.getImageData("public");
                        _self.getTemplate("public");
                    } else{
                        _self.showPrivate = true;
                        _self.showPublic = false;
                        _self.getImageData("private");
                        _self.getTemplate("private");
                    }
                    Vue.nextTick(function(){
                        _self.initJpages("#appholder", "appcontainer");
                    });
                },

                // 全选或反选
                checkAll: function (event, type) {
                    var _self = this;
                    var input = $(event.target);
                    var isSelected = $(input).prop("checked");
                    var parent = $(event.target).parents("div.app-items");
                    $(parent).find("input[type='checkbox']").each(function (i, element) {
                        $(element).prop("checked", isSelected);
                    });
                    if(isSelected) {
                        _self.getImageData(type);
                        _self.getTemplate(type);
                    } else {
                        _self.imageInfos = [];
                        _self.templateInfos = [];
                    }
                    Vue.nextTick(function(){
                        _self.initJpages("#appholder", "appcontainer");
                    });
                },
                
                checkOne: function (event, repoType, appType) {
                    var _self = this;
                    var target = $(event.target);
                    var appTypeInput = $(target).parents("div.app-items").find("input.app-type");
                    var selectedCount = 0;
                    var select = {}; //记录选择情况
                    appTypeInput.each(function (i, element) {
                        var checked = $(element).prop("checked");
                        select[$(element).val()] = checked;
                        if(checked){
                            selectedCount += 1;
                        }
                    });
                    var appAllInput = $(target).parents("div.app-items").find("input.app-type-all")[0];
                    if(selectedCount === appTypeInput.length) { //全选
                        $(appAllInput).prop("checked", true);
                        _self.getImageData(repoType);
                        _self.getTemplate(repoType);
                    } else { //非全选
                        $(appAllInput).prop("checked", false);
                        for (var key in select) {
                            switch (key) {
                                case 'image':
                                    if(select[key]) {
                                        _self.getImageData(repoType);
                                    } else {
                                        _self.imageInfos = [];
                                    }
                                    break;
                                case 'template':
                                    if(select[key]) {
                                        _self.getTemplate(repoType);
                                    } else {
                                        _self.templateInfos = [];
                                    }
                                    break;
                            }
                        }
                    }
                    Vue.nextTick(function(){
                        _self.initJpages("#appholder", "appcontainer");
                    });
                },

                // 刷新
                refreshRepo: function () {
                    var _self = this;
                    $("#appstore .app-detail").css("display", "none");
                    _self.showPrivate = false;
                    _self.showPublic = true;
                    _self.getImageData("public");
                    _self.getTemplate("public");
                    Vue.nextTick(function(){
                        _self.initJpages("#appholder", "appcontainer");
                    });
                },

                getInfoByVersion: function (imageInfo, version) {
                    var _self = this;
                    var metadata = imageInfo.metadata[version];
                    _self.imageDetailBind.sourceUrl = "docker pull " + imageInfo.source_url[version];
                    _self.imageDetailBind.requests = "cpu(核):" + metadata.requests.cpu + ", 内存:" + metadata.requests.memory;
                    _self.imageDetailBind.limits = "cpu(核):" + metadata.limits.cpu + ", 内存:" + metadata.limits.memory;
                    _self.imageDetailBind.env = metadata.env;
                    _self.imageDetailBind.ports = metadata.ports;
                    _self.imageDetailBind.volume = metadata.volume;
                    _self.imageDetailBind.cmd = metadata.cmd;
                    _self.imageDetailBind.cmdParams = metadata.cmdParams;
                    _self.imageDetailBind.v_description = imageInfo.v_description[version];
                },

                getInfoByAppName: function (templateInfo, appname) {
                    var _self = this;
                    var config = [];
                    for(var i = 0; i < templateInfo.config.length; i++) {
                        if(templateInfo.config[i].appName === appname){
                            config = templateInfo.config[i];
                            break;
                        }
                    }
                    var metadata = config["metadata"];
                    _self.templateDetailBind.appVersion = config["version"];
                    _self.templateDetailBind.sourceUrl = "docker pull " + config["source_url"];
                    _self.templateDetailBind.requests = "cpu(核):" + metadata["requests"].cpu + ", 内存:" + metadata["requests"].memory;
                    _self.templateDetailBind.limits = "cpu(核):" + metadata["limits"].cpu + ", 内存:" + metadata["limits"].memory;
                    _self.templateDetailBind.volume = metadata["volume"];
                    _self.templateDetailBind.cmd = metadata["cmd"];
                    _self.templateDetailBind.cmdParams = metadata['cmdParams'];
                    _self.templateDetailBind.env = metadata['env'];
                    _self.templateDetailBind.ports = metadata["ports"];
                },

                changeVersion: function (event) {
                    debugger
                    var _self = this;
                    var version = $(event.target).text();
                    $(event.target).parent().children(".version").each(function (i, element) {
                        $(element).removeClass("version-selected");
                    });
                    $(event.target).addClass("version-selected");
                    _self.getInfoByVersion(_self.selectedImage, version);
                },

                changeAppName: function (event) {
                    var _self = this;
                    var appname = $(event.target).text();
                    $(event.target).parent().children(".config").each(function (i, element) {
                        $(element).removeClass("config-selected");
                    });
                    $(event.target).addClass("config-selected");
                    _self.getInfoByAppName(_self.selectedTemplate, appname);
                },

                // 展示应用详情
                // todo 展示还有一点小bug
                showAppDetail: function (event, id, type, index) {
                    var _self = this;
                    $(event.target).parents("ul").find("li").each(function (index, element) {
                        $(element).removeClass("li-selected");
                    });
                    var li = $(event.target).parents("li") || $(event.target);
                    $(li).addClass("li-selected");
                    switch (type) {
                        case 'image':
                            _self.imageDetail = true;
                            _self.templateDetail = false;
                            _self.selectedImage = _self.imageInfos[index];
                            _self.getInfoByVersion(_self.selectedImage, _self.selectedImage.version[0]);
                            break;
                        case 'template':
                            _self.imageDetail = false;
                            _self.templateDetail = true;
                            _self.selectedTemplate = _self.templateInfos[index];
                            _self.getInfoByAppName(_self.selectedTemplate, _self.selectedTemplate.config[0].appName);
                            break;
                    }
                    Vue.nextTick(function () {
                        $("#appstore .app-detail").css("display", "block");
                    });
                },

                showImageVersions: function () {
                    var _self = this;
                    var image = _self.selectedImage;
                    console.log(image);
                    var versionStr = "";
                    for(var i = 0; i< image.version.length; i++ ) {
                        versionStr +=
                            "<div class='version-item'>" +
                                "<input type='checkbox' class='cloud-checkbox' value='" + image.version[i] + "'/>"+
                                "<span>" + image.version[i] + "</span>" +
                            "</div>";
                    }
                    $("#imageVersion .version-box").append(versionStr);
                    $("#imageVersion").modal({backdrop: 'static', keyboard: false});
                },

                submitDelete: function (){
                    var _self = this;
                    var versions = [];
                    $("#imageVersion .version-item").each(function (index, item) {
                        var input = $(item).find("input")[0];
                        if($(input).prop("checked")){
                            versions.push($(input).val());
                        }
                    });
                    $(".loading").css('display', 'block');
                    $.ajax({
                        type: "post",
                        url: "../appcenter/imageInfo/delete",
                        data: {
                            imageId: _self.selectedImage.app_id,
                            versions: versions
                        },
                        dataType: "json",
                        success: function (result) {
                            if(result.code == "success"){
                                if(result.data.success == versions.length) {
                                    common_module.notify('[应用中心]',
                                        "删除镜像成功" + result.data.success + "个，失败" + result.data.fail + "个", 'success');
                                } else {
                                    common_module.notify('[应用中心]',
                                        "删除镜像成功" + result.data.success  +"个，失败" + result.data.fail + "个", 'warning');
                                }
                            } else {
                                common_module.notify('[应用中心]',"删除镜像失败", 'danger');
                            }
                            //刷新数据
                            if(_self.showPublic) {
                                _self.getImageData("public");
                                _self.getTemplate("public");
                            } else {
                                _self.getImageData("private");
                                _self.getTemplate("private");
                            }
                            Vue.nextTick(function(){
                                _self.initJpages("#appholder", "appcontainer");
                            });
                            setTimeout(function () {
                                $(".loading").css('display', 'none');
                            },1000);
                        },
                        error: function () {
                            common_module.notify('[应用中心]',"删除镜像失败", 'danger');
                            setTimeout(function () {
                                $(".loading").css('display', 'none');
                            },1000);
                        }
                    })
                },

                closeAppDetail: function () {
                    $("#appstore .app-detail").css("display", "none");
                }
            }
        });

        $("#imageVersion_dialog").on("hidden.bs.modal", function () {
            $("#imageVersion .version-box").empty();
        });
    }
});