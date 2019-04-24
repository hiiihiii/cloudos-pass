"use strict";
define([
    'jquery',
    'vue',
    'bootstrap',
    'bootstrapSwitch',
    "common-module",
    'jquery-validate',
    'validate-extend'
], function ($, Vue, bootstrap, bootstrapSwitch, common_module, jquery_validate, validate_extend) {
    if($("#upload_image_dialog")[0]){
        var upload_image = new Vue({
            el: "#upload_image",
            data: {
                definedAppTag: {
                    "WebServer": "WebServer",
                    "DBMS": "数据库",
                    "Application": "应用"
                },
                infoTag: "baseInfo",
                nextStep: true,
                previousStep: false,
                submitTag: false,
                bindData: {
                    logoFileName: "",
                    sourceFileName: "",
                    appTag_input: "",
                    ports:[{
                        "portName": '',
                        "protocol": '',
                        "containerPort": '',
                        "port": '',
                        "nodePort": ''
                    }],
                    envs:[]
                },
                uploadType: "private",
                imageInfos: [],
                templateInfos: []
            },
            methods: {
                //下一步
                toNext: function () {
                    var _self = this;
                    if(_self.infoTag == 'baseInfo'){
                        _self.infoTag = 'configInfo';
                        _self.previousStep = true;
                        _self.nextStep = true;
                    } else if(_self.infoTag == 'configInfo'){
                        _self.infoTag = 'runtimeInfo';
                        _self.previousStep = true;
                        _self.nextStep = false;
                    }
                    if(_self.infoTag == 'runtimeInfo'){
                        _self.submitTag = true;
                    } else {
                        _self.submitTag = false;
                    }
                },

                //上一步
                toPrevious: function () {
                    var _self = this;
                    if(_self.infoTag == 'configInfo'){
                        _self.infoTag = 'baseInfo';
                        _self.previousStep = false;
                        _self.nextStep = true;
                    } else if(_self.infoTag == 'runtimeInfo'){
                        _self.infoTag = 'configInfo';
                        _self.previousStep = true;
                        _self.nextStep = true;
                    }
                    if(_self.infoTag == 'runtimeInfo'){
                        _self.submitTag = true;
                    } else {
                        _self.submitTag = false;
                    }
                },

                //增加命令参数
                addCmdParam: function () {
                    var cmdParamStr =
                        '<div class="cmdParam-item">' +
                        '<input class="form-control" type="text" name="cmdParam"/>' +
                        '<span class="delete-cmd-btn"><i class="fa fa-trash-o"></i></span>'+
                        '</div>';
                    var cmdParam_box = $("#upload_image #cmdParam-box");
                    cmdParam_box.append(cmdParamStr);
                    var cmdParam_items = cmdParam_box.find(".delete-cmd-btn");
                    $(cmdParam_items[cmdParam_items.length-1]).on("click", function () {
                        $(this).parent().remove();
                    });
                },

                //增加环境变量
                addEnv: function () {
                    var _self = this;
                    var count = _self.bindData.envs.length;
                    var envStr =
                        '<tr>' +
                        '<td><input class="form-control" type="text" name="envKey" id="envKey' + count + '"/></td>' +
                        '<td><input class="form-control" type="text" name="envValue" id="envValue' + count + '"/></td>' +
                        '<td><span class="modal-table-operation"><i class="fa fa-trash-o"></i></span></td>' +
                        '</tr>';
                    var envTbody = $("#upload_image #env_table tbody");
                    envTbody.append(envStr);
                    _self.bindData.envs.push({
                        "envKey": "",
                        "envValue": ""
                    });
                    var trs = envTbody.find("tr");
                    $(trs[trs.length-1]).on("click", ".modal-table-operation", function () {
                        /*detach方法可以删除绑定的事件，而remove能*/
                        $(this).parents("tr").remove();
                        _self.bindData.envs.pop();
                    });
                },

                //增加端口映射
                addPort: function () {
                    var _self = this;
                    var count = _self.bindData.ports.length;
                    var portStr = '<tr>' +
                        '<td><input class="form-control" type="text" id="portName' + count + '" name="portName"/></td>' +
                        '<td>' +
                        '<select class="form-control" name="protocol" id="protocol' + count + '">' +
                        '<option value="TCP">TCP</option>' +
                        '<option value="UDP">UDP</option>' +
                        '</select>' +
                        '</td>' +
                        '<td><input class="form-control" type="text" id="containerPort' + count + '" maxlength="5" name="containerPort"/></td>' +
                        '<td><input class="form-control" type="text" id="port' + count + '" maxlength="5" name="port"/></td>' +
                        '<td><input class="form-control" type="text" id="nodePort' + count + '" maxlength="5" name="nodePort"/></td>' +
                        '<td><span class="modal-table-operation"><i class="fa fa-trash-o"></i></span></td>' +
                        '</tr>';
                    var portTbody = $("#upload_image #port_table tbody");
                    portTbody.append(portStr);
                    _self.bindData.ports.push({
                        "portName": '',
                        "protocol": '',
                        "containerPort": '',
                        "port": '',
                        "nodePort": ''
                    });
                    var trs = portTbody.find("tr");
                    $(trs[trs.length-1]).on("click", ".modal-table-operation", function () {
                        /*detach方法可以删除绑定的事件，而remove能*/
                        $(this).parents("tr").remove();
                        _self.bindData.ports.pop();
                    });
                },

                //选择文件
                fileChange: function (id) {
                    var _self = this;
                    switch (id){
                        case "logo":
                            var logoFile = document.getElementById("logo_upload");
                            if(logoFile.files[0]){
                                _self.bindData.logoFileName = logoFile.files[0].name;
                            }
                            break;
                        case "source":
                            var sourceFile = document.getElementById("source");
                            if(sourceFile.files[0]){
                                _self.bindData.sourceFileName = sourceFile.files[0].name;
                            }
                            break;
                    }
                },

                //选择标签
                tagChange: function () {
                    var _self = this;
                    var tag = $("#upload_image_form select[name='appTag']").val();
                    _self.bindData.appTag_input = tag;
                },

                //提交上传
                submitUpload: function () {
                    debugger
                    var test = $("#upload_image_form").valid();
                    if(!test){
                        return;
                    }
                    var _self = this;
                    var formData = new FormData();
                    formData.append("appName", $("#upload_image_form #appName").val());
                    var isPublic = $('#upload_image_form #isPublic input').bootstrapSwitch('state');
                    if(isPublic){
                        formData.append("type", "public");
                    } else {
                        formData.append("type", "private");
                    }
                    formData.append("logoFile", $("#upload_image_form #logo_upload")[0].files[0]);
                    formData.append("version",$("#upload_image_form #version").val());
                    formData.append("appTag", _self.bindData.appTag_input);
                    formData.append("description", $("#upload_image_form textarea[name='description']").val());
                    formData.append("v_description", $("#upload_image_form textarea[name='v_description']").val());
                    formData.append("sourceFile", $("#upload_image_form #source")[0].files[0]);
                    formData.append("createType","upload");//表示镜像是用户自己上传的

                    //构造metadata
                    var metadata = JSON.stringify(_self.createMetadata());
                    formData.append("metadata", metadata);
                    $(".loading").css("display", "block");
                    $.ajax({
                        url: "../appcenter/upload",
                        type: "post",
                        data: formData,
                        processData: false,
                        contentType: false,
                        success: function(data){
                            debugger
                            console.log(data);
                            common_module.notify("[应用中心]", "上传应用成功", "success");
                            $("#upload_image").modal('hide');
                            //获取镜像和模板信息
                            if(isPublic) {
                                _self.getImageData("public");
                                _self.getTemplateData("public");
                            } else {
                                _self.getImageData("private");
                                _self.getTemplateData("private");
                            }
                            Vue.nextTick(function(){
                                _self.initJpages("#appholder", "appcontainer");
                            });
                            setTimeout(function () {
                                $(".loading").css("display", "none");
                            },1000);
                        },
                        error: function(){
                            common_module.notify("[应用中心]","上传应用失败", "danger");
                            setTimeout(function () {
                                $(".loading").css("display", "none");
                            },1000);
                        }
                    });
                },

                getImageData: function (type) {
                    var _self = this;
                    $.ajax({
                        url: "../appcenter/imageinfo",
                        type: "get",
                        data: {
                            repoType: type
                        },
                        dataType: "json",
                        async: false,
                        success: function (data) {
                            _self.imageInfos = _self.convertData(data.data, 'image');
                            console.log(_self.imageInfos);
                            common_module.notify("[应用中心]","获取镜像数据成功", "success");
                        },
                        error: function () {
                            common_module.notify("[应用中心]","获取镜像数据失败", "danger");
                        }
                    })
                },
                getTemplateData: function (type) {
                    var _self = this;
                    $.ajax({
                        url: "../appcenter/templateinfo",
                        type: 'get',
                        dataType: 'json',
                        data: {
                            repoType: type
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

                //构造上传时的metadata, 数据格式见static/json/metadata.json
                createMetadata: function(){
                    var metadata = {};

                    metadata.volume = $("#upload_image_form input[name='volumeDir']").val();
                    //命令及命令参数
                    var cmdParams = [];
                    $("#upload_image_form input[name='cmdParam']").each(function(){
                        cmdParams.push($(this).val());
                    });
                    metadata.cmd = $("#upload_image_form input[name='cmd']").val(); //命令
                    metadata.cmdParams = cmdParams;//命令参数
                    //环境变量
                    var envs = [];
                    $("#upload_image_form input[name='envKey']").each(function(){
                        var temp = {};
                        var $this = $(this);
                        var $tr = $this.parents("tr");
                        temp.name = $this.val();
                        temp.value = $tr.find("input[name='envValue']").val();
                        envs.push(temp);
                    })
                    metadata.env = envs;
                    //端口
                    var ports = [];
                    $("#upload_image_form input[name='portName']").each(function(){
                        var temp = {};
                        var $this = $(this);
                        var $tr = $this.parents("tr");
                        temp.portName = $this.val();
                        temp.protocol = $tr.find("select[name='protocol']").val();
                        temp.containerPort = $tr.find("input[name='containerPort']").val();
                        temp.port = $tr.find("input[name='port']").val();
                        temp.nodePort = $tr.find("input[name='nodePort']").val();
                        ports.push(temp);
                    })
                    metadata.ports = ports;
                    //启动限制
                    metadata.requests = {
                        "cpu": $("#upload_image_form input[name='mincpu']").val(),
                        "memory": $("#upload_image_form input[name='minMemory']").val() + $("#upload_image_form select[name='minMemoryUnit']").val()
                    };
                    //运行限制
                    metadata.limits = {
                        "cpu": $("#upload_image_form input[name='maxcpu']").val(),
                        "memory": $("#upload_image_form input[name='maxMemory']").val() + $("#upload_image_form select[name='maxMemoryUnit']").val()
                    };

                    return metadata;
                }
            }
        });

        // 上传镜像校验
        var validator = $("#upload_image_form").validate({
            submitHandler: function(form){
                debugger
                upload_image.submitUpload();
            },
            ignore: "",
            rules: {
                appName: {
                    required: true,
                    notEmpty: true
                },
                logoFileName: {
                    required: true
                },
                version: {
                    required: true,
                    notEmpty: true,
                    imageUnique: true
                },
                appTag_input: {
                    required: true,
                    notEmpty: true
                },
                sourceFileName: {
                    required: true
                },
                portName: {
                    required: true,
                    notEmpty: true
                },
                protocol: {
                    required: true,
                    notEmpty: true
                },
                containerPort: {
                    required: true,
                    notEmpty: true
                },
                port: {
                    required: true,
                    notEmpty: true
                },
                nodePort: {
                    required: true,
                    notEmpty: true
                },
                mincpu: {
                    required: true
                },
                minMemory: {
                    required: true
                },
                maxMemory: {
                    required: true
                },
                maxcpu: {
                    required: true
                }
            },
            messages: {
                appName: {

                }
            },
            errorPlacement: function (error, element) {
                error.appendTo(element.parent());
            }
        });

        $("#upload_image_dialog").on("hidden.bs.modal", function () {
            upload_image.infoTag = "baseInfo";
            upload_image.nextStep = true;
            upload_image.previousStep = false;
            upload_image.submitTag =  false;
            upload_image.bindData.logoFileName = "";
            upload_image.bindData.sourceFileName = "";
            upload_image.bindData.appTag_input = "";
            upload_image.bindData.ports = [{
                "portName": '',
                "protocol": '',
                "containerPort": '',
                "port": '',
                "nodePort": ''
            }];
            upload_image.bindData.envs = [];

            $("#upload_image_form input[type='text']").val("");
            $("#upload_image_form select[name='protocol']").val("TCP");
            $("#upload_image_form select[name='minMemoryUnit']").val("Mi");
            $("#upload_image_form select[name='maxMemoryUnit']").val("Mi");
            $("#upload_image_form textarea").val("");
        });

        $('#upload_image').on('show.bs.modal', function () {
            console.log("upload-image-show");
        });

        $('#upload_image').on('shown.bs.modal', function () {
            console.log("upload-image-shown");
        });

        $("#upload_image_form select[name='appTag']").on("change",function(){
            upload_image.bindData.appTag_input = $("#upload_image_form select[name='appTag']").val();
        })
    }
});