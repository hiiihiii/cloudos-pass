"use strict";
define([
    'jquery',
    "vue",
    "echarts",
    "common-module",
    "bootstrap",
    "bootstrapSwitch"
],function ($, Vue, echarts, common_module, bootstrap, bootstrapSwitch) {
    if($("#appstore")[0]){
        var appstore = new Vue({
            el: "#appstore",
            data: {
                appType: "public",
                showPublic: true,
                showPrivate: false,
            },
            mounted: function () {
                var _self = this;
                _self.showPublic = true;
                _self.showPrivate = false;
            },
            methods: {
                showUploadDialog: function () {
                    $("#isPublic").bootstrapSwitch({
                        onText:'是',
                        offText:'否',
                        // onColor:"success",
                        // offColor:"info",
                        onSwitchChange: function (event, state) {
                            if(state){

                            } else {

                            }
                        }
                    });
                    $("#upload_image").modal({backdrop: 'static', keyboard: false});
                }
            }
        });

        $('#upload_image').on('shown.bs.modal', function () {

        });
    }
});