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
                deployTemplateObj: ''
            },
            mounted: function () {

            },
            methods: {
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
                }
            }
        });

        $('#deploy_template').on('show.bs.modal', function () {
            var templateInfo = JSON.parse(sessionStorage.getItem("deployTemplate"));
            deployTemplate.deployTemplateObj = templateInfo;
            // var version = imageInfo.version[0];
            // deployTemplate.getImageInfoByVersion(version);
        });
    }
});