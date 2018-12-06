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
                templates: []
            },
            mounted: function () {
                var _self = this;
                _self.showPublic = true;
                _self.showPrivate = false;
                //设置分类全部选中
                $(".app-items .cloud-checkbox").attr("checked", "true");
                //为分类选择绑定事件
                _self.classifyBind();
                //todo 这里不应该是image，而是all
                _self.getAppData("public", "image");

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

                //获取应用数据
                getAppData: function (repoType, appType) {
                    var _self = this;
                    $.ajax({
                        url: "../appcenter/appinfo",
                        type: "get",
                        data: {
                            repoType: repoType,
                            appType: appType
                        },
                        dataType: "json",
                        async: false,
                        // processData: false,
                        // contentType: false,
                        success: function (data) {
                            _self.imageInfos = _self.convertData(data.data);
                            console.log(_self.imageInfos);
                            common_module.notify("[应用中心]","获取镜像数据成功", "success");
                        },
                        error: function () {
                            common_module.notify("[应用中心]","获取镜像数据失败", "danger");
                        }
                    })
                },

                //获取模板信息

                convertData: function(imageArray){
                    var _self = this;
                    for(var i = 0; i < imageArray.length; i++){
                        imageArray[i].createType = JSON.parse(imageArray[i].createType);
                        imageArray[i].metadata = JSON.parse(imageArray[i].metadata);
                        imageArray[i].source_url = JSON.parse(imageArray[i].source_url);
                        imageArray[i].v_description = JSON.parse(imageArray[i].v_description);
                        imageArray[i].version = JSON.parse(imageArray[i].version);
                        imageArray[i].logo_url = "ftp://docker:dockerfile@" + imageArray[i].logo_url;
                    }
                    return imageArray;
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
                changeRepo: function (repoType) {
                    var _self = this;
                    _self.appType = repoType;
                    if(repoType === 'public'){
                        _self.showPrivate = false;
                        _self.showPublic = true;
                    } else{
                        _self.showPrivate = true;
                        _self.showPublic = false;
                    }
                }
            }
        });

    }
});