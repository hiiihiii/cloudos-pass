"use strict";
define([
    'jquery',
    'vue',
    'bootstrap',
    'jquery-validate',
    'validate-extend'
], function ($, Vue, bootstrap, bootstrapSwitch, jquery_validate, validate_extend) {
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
                        "targetPort": ''
                    }],
                    envs:[]
                }
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
                        $(this).parent().detach();
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
                        /*detach方法可以删除绑定的时间，而remove不能*/
                        $(this).parents("tr").detach();
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
                        '<td><input class="form-control" type="text" id="targetPort' + count + '" maxlength="5" name="targetPort"/></td>' +
                        '<td><span class="modal-table-operation"><i class="fa fa-trash-o"></i></span></td>' +
                        '</tr>';
                    var portTbody = $("#upload_image #port_table tbody");
                    portTbody.append(portStr);
                    _self.bindData.ports.push({
                        "portName": '',
                        "protocol": '',
                        "containerPort": '',
                        "port": '',
                        "targetPort": ''
                    });
                    var trs = portTbody.find("tr");
                    $(trs[trs.length-1]).on("click", ".modal-table-operation", function () {
                        /*detach方法可以删除绑定的时间，而remove不能*/
                        $(this).parents("tr").detach();
                        _self.bindData.ports.pop();
                    });
                },

                //选择文件
                fileChange: function (id) {
                    var _self = this;
                    switch (id){
                        case "logo":
                            var logoFile = document.getElementById("logo");
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
                    debugger
                    var _self = this;
                    var tag = $("#upload_image_form select[name='appTag']").val();
                    _self.bindData.appTag_input = tag;
                }
            }
        });

        // 上传镜像校验
        var validator = $("#upload_image_form").validate({
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
                    notEmpty: true
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
                targetPort: {
                    required: true,
                    notEmpty: true
                }
            },
            messages: {
                appName: {

                }
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
                "targetPort": ''
            }];
            upload_image.bindData.envs = [];

            $("#upload_image_form input[type='text']").val("");
            $("#upload_image_form select[name='protocol']").val("TCP");
            $("#upload_image_form select[name='minMemoryUnit']").val("MB");
            $("#upload_image_form select[name='maxMemoryUnit']").val("MB");
            $("#upload_image_form textarea").val("");
        });
        $("#upload_image_form select[name='appTag']").on("change",function(){
            upload_image.bindData.appTag_input = $("#upload_image_form select[name='appTag']").val();
        })
    }
});