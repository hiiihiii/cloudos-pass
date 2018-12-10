"use strict";
define([
    'jquery',
    'vue',
    'bootstrap',
    'jquery-validate',
    'validate-extend'
], function ($, Vue, bootstrap, bootstrapSwitch, jquery_validate, validate_extend) {
    if($("#deploy_template")[0]){
        var deployTemplate = new Vue({
            el: '#deploy_template',
            data: {
                infoTag: "baseInfo",
                nextStep: true,
                previousStep: false,
                submitTag: false,
                deployTemplateObj: {
                    templateName: "",
                    appType: '',
                    config: [],
                    relation: {}
                }
            },
            mounted: function () {

            },
            methods: {
                //上一步
                toPrevious: function () {
                    var _self = this;
                    if(_self.infoTag == 'resourceInfo'){
                        _self.infoTag = 'baseInfo';
                        _self.previousStep = false;
                        _self.nextStep = true;
                    } else if(_self.infoTag == 'configInfo'){
                        _self.infoTag = 'resourceInfo';
                        _self.previousStep = true;
                        _self.nextStep = true;
                    }
                    if(_self.infoTag == 'configInfo'){
                        _self.submitTag = true;
                    } else {
                        _self.submitTag = false;
                    }
                },

                //下一步
                toNext:function () {
                    var _self = this;
                    if(_self.infoTag == 'baseInfo'){
                        _self.infoTag = 'resourceInfo';
                        _self.previousStep = true;
                        _self.nextStep = true;
                    } else if(_self.infoTag == 'resourceInfo'){
                        _self.infoTag = 'configInfo';
                        _self.previousStep = true;
                        _self.nextStep = false;
                    }
                    if(_self.infoTag == 'configInfo'){
                        _self.submitTag = true;
                    } else {
                        _self.submitTag = false;
                    }
                },

                //初始化数据
                initData: function () {
                    var _self = this;
                    for(var i = 0; i < _self.deployTemplateObj.config.length; i++){
                        //调整 运行环境 格式
                        var requests = _self.deployTemplateObj.config[i].metadata.requests;
                        _self.deployTemplateObj.config[i].metadata.requests = {
                            cpu: requests.cpu,
                            memory: requests.memory.slice(0, requests.memory.length - 2),
                            memoryUnit: requests.memory.slice(requests.memory.length - 2, requests.memory.length)
                        };
                        var limits = _self.deployTemplateObj.config[i].metadata.limits;
                        _self.deployTemplateObj.config[i].metadata.limits = {
                            cpu: limits.cpu,
                            memory: limits.memory.slice(0, limits.memory.length - 2),
                            memoryUnit: limits.memory.slice(limits.memory.length - 2, limits.memory.length)
                        };
                        debugger
                        var name = _self.deployTemplateObj.config[i].appName;
                        $("#deploy_template #" + name + "cmdParam-box" + " .cmdParam-item").remove();
                        $("#deploy_template #" + name + "env_table tbody tr").remove();
                        $("#deploy_template #" + name + "port_table tbody tr").remove();
                        var cmdParams = _self.deployTemplateObj.config[i].metadata.cmdParams;
                        _self.addCmdParam(name, cmdParams);
                        var env = _self.deployTemplateObj.config[i].metadata.env;
                        _self.addEnv(name, env);
                        var ports = _self.deployTemplateObj.config[i].metadata.ports;
                        _self.addPort(name, ports, 'js');
                    }
                },

                //增加命令参数
                addCmdParam: function (appName, cmdParamArray) {
                    debugger
                    cmdParamArray = cmdParamArray || [''];
                    for(var i = 0; i < cmdParamArray.length; i++) {
                        var cmdParam = cmdParamArray[i];
                        var cmdParamStr =
                            '<div class="cmdParam-item">' +
                            '<input class="form-control" type="text" name="cmdParam" value="'+ cmdParam +'" />' +
                            '<span class="delete-cmd-btn"><i class="fa fa-trash-o"></i></span>'+
                            '</div>';
                        var id = "#deploy_template #" + appName + "cmdParam-box";
                        var cmdParam_box = $(id);
                        cmdParam_box.append(cmdParamStr);
                        var cmdParam_items = cmdParam_box.find(".delete-cmd-btn");
                        $(cmdParam_items[cmdParam_items.length-1]).on("click", function () {
                            $(this).parent().remove();
                        });
                    }
                },

                //增加环境变量
                addEnv: function (appName, envObj) {
                    debugger
                    if(envObj.length==0){
                        envObj = [{name:'', value: ''}];
                    }
                    for(var i = 0; i < envObj.length; i++){
                        var envStr =
                            '<tr>' +
                            '<td><input class="form-control" type="text" value="'+ envObj[i].name +'" name="envKey" /></td>' +
                            '<td><input class="form-control" type="text" value="' + envObj[i].value + '" name="envValue" /></td>' +
                            '<td><span class="modal-table-operation"><i class="fa fa-trash-o"></i></span></td>' +
                            '</tr>';
                        var id = "#deploy_template #" + appName + "env_table tbody";
                        var envTbody = $(id);
                        envTbody.append(envStr);
                        var trs = envTbody.find("tr");
                        $(trs[trs.length-1]).on("click", ".modal-table-operation", function () {
                            /*detach方法可以删除绑定的事件，而remove能*/
                            $(this).parents("tr").remove();
                        });
                    }
                },

                //增加端口映射
                addPort: function (appName, portObj, source) {
                    debugger
                    if(portObj.length == 0 ){
                        portObj = [{portName:'', protocol:'TCP',containerPort:'',port:'',nodePort:''}];
                    }
                    for(var i = 0; i < portObj.length; i++){
                        var portStr = '<tr>' +
                            '<td><input class="form-control" type="text" value="' + portObj[i].portName + '" name="portName"/></td>' +
                            '<td>' +
                            '<select class="form-control" name="protocol" value="' + portObj[i].protocol + '">' +
                            '<option value="TCP">TCP</option>' +
                            '<option value="UDP">UDP</option>' +
                            '</select>' +
                            '</td>' +
                            '<td><input class="form-control" type="text" maxlength="5" value="'+portObj[i].containerPort+'" name="containerPort"/></td>' +
                            '<td><input class="form-control" type="text" maxlength="5" value="'+portObj[i].port+'" name="port"/></td>' +
                            '<td><input class="form-control" type="text" maxlength="5" value="'+portObj[i].nodePort+'" name="nodePort"/></td>';
                        if(source === 'js' && i === 0){
                            portStr += '</tr>';
                        } else {
                            portStr +=
                                '<td><span class="modal-table-operation"><i class="fa fa-trash-o"></i></span></td>' +
                                '</tr>';
                        }
                        var id = "#deploy_template #" + appName + "port_table tbody";
                        var portTbody = $(id);
                        portTbody.append(portStr);
                        var trs = portTbody.find("tr");
                        $(trs[trs.length-1]).on("click", ".modal-table-operation", function () {
                            /*detach方法不能删除绑定的事件，而remove能*/
                            $(this).parents("tr").remove();
                        });
                    }
                }
            }
        });

        $('#deploy_template').on('show.bs.modal', function () {
            var templateInfo = JSON.parse(sessionStorage.getItem("deployTemplate"));
            deployTemplate.deployTemplateObj.templateName = templateInfo.templateName;
            deployTemplate.deployTemplateObj.appType = templateInfo.appType;
            deployTemplate.deployTemplateObj.config = templateInfo.config;
            deployTemplate.deployTemplateObj.relation = templateInfo.relation;
            Vue.nextTick(function () {
                deployTemplate.initData();
            });
        });
    }
});