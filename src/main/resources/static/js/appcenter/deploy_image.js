"use strict";
define([
    'jquery',
    'vue',
    'bootstrap',
    'jquery-validate',
    'validate-extend'
], function ($, Vue, bootstrap, bootstrapSwitch, jquery_validate, validate_extend) {
    if($("#deploy_image_dialog")[0]){
        var deploy_image = new Vue({
            el: '#deploy_image',
            data: {
                infoTag: "baseInfo",
                nextStep: true,
                previousStep: false,
                submitTag: false,
                deployImageObj: '',
                selectedVersion: {
                    version: '',
                    limits: {},
                    requests: {},
                    volume: '',
                    cmd: '',
                    cmdParams: [],
                    env: [],
                    ports: []
                }

            },
            mounted: function () {

            },
            methods: {
                //下一步
                toNext: function () {
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

                //增加命令参数
                addCmdParam: function () {},

                //增加环境变量
                addEnv: function () {},

                //增加端口映射
                addPort: function () {},

                //根据版本获取镜像信息
                getImageInfoByVersion: function(version){
                    debugger
                    var _self = this;
                    var metadata = _self.deployImageObj.metadata[version];
                    _self.selectedVersion.version = version;
                    _self.selectedVersion.limits = {
                        cpu: metadata.limits.cpu,
                        memory: metadata.limits.memory.slice(0, metadata.limits.memory.length - 2),
                        memoryUnit: metadata.limits.memory.slice(metadata.limits.memory.length - 2, metadata.limits.memory.length)
                    };
                    _self.selectedVersion.requests = {
                        cpu: metadata.requests.cpu,
                        memory: metadata.requests.memory.slice(0, metadata.requests.memory.length - 2),
                        memoryUnit: metadata.requests.memory.slice(metadata.requests.memory.length - 2, metadata.requests.memory.length)
                    };
                    _self.selectedVersion.volume = metadata.volume;
                    _self.selectedVersion.cmd = metadata.cmd;
                    _self.selectedVersion.cmdParams = metadata.cmdParams;
                    _self.selectedVersion.env = metadata.env;
                    _self.selectedVersion.ports = metadata.ports;
                }
            }
        });

        $('#deploy_image').on('show.bs.modal', function () {
            console.log("deploy-image-show");
            debugger;
            var imageInfo = JSON.parse(sessionStorage.getItem("deployImage"));
            deploy_image.deployImageObj = imageInfo;
            var version = imageInfo.version[0];
            deploy_image.getImageInfoByVersion(version);
        });
    }
});