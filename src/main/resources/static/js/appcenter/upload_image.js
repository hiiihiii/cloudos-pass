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
                toNext: function () {
                    debugger
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