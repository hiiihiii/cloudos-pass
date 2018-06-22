"use strict";
define([
    'jquery',
    'vue',
    'bootstrap'
], function ($, Vue, bootstrap, bootstrapSwitch) {
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
                submitTag: false

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
                    var envStr =
                        '<tr>' +
                        '<td><input class="form-control" type="text" name="envKey"/></td>' +
                        '<td><input class="form-control" type="text" name="envValue"/></td>' +
                        '<td><span class="modal-table-operation"><i class="fa fa-trash-o"></i></span></td>' +
                        '</tr>';
                    var envTbody = $("#upload_image #env_table tbody");
                    envTbody.append(envStr);
                    var trs = envTbody.find("tr");
                    $(trs[trs.length-1]).on("click", ".modal-table-operation", function () {
                        /*detach方法可以删除绑定的时间，而remove不能*/
                        $(this).parents("tr").detach();
                    });
                },

                //增加端口映射
                addPort: function () {
                    var portStr = '<tr>' +
                        '<td><input class="form-control" type="text" id="portName0" name="portName"/></td>' +
                        '<td>' +
                        '<select class="form-control" name="protocol" id="protocol0">' +
                        '<option value="TCP">TCP</option>' +
                        '<option value="UDP">UDP</option>' +
                        '</select>' +
                        '</td>' +
                        '<td><input class="form-control" type="text" id="containerPort0" maxlength="5" name="containerPort"/></td>' +
                        '<td><input class="form-control" type="text" id="port0" maxlength="5" name="port"/></td>' +
                        '<td><input class="form-control" type="text" id="targetPort0" maxlength="5" name="targetPort"/></td>' +
                        '<td><span class="modal-table-operation"><i class="fa fa-trash-o"></i></span></td>' +
                        '</tr>';
                    var portTbody = $("#upload_image #port_table tbody");
                    portTbody.append(portStr);
                    var trs = portTbody.find("tr");
                    $(trs[trs.length-1]).on("click", ".modal-table-operation", function () {
                        /*detach方法可以删除绑定的时间，而remove不能*/
                        $(this).parents("tr").detach();
                    });
                }
            }
        });
        $("#upload_image_dialog").on("hidden.bs.modal", function () {
            upload_image.infoTag = "baseInfo";
            upload_image.nextStep = true;
            upload_image.previousStep = false;
            upload_image.submitTag =  false;
        })
    }
});