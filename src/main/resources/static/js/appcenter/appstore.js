"use strict";
define([
    'jquery',
    "vue",
    "echarts",
    "common-module",
    "bootstrap"
],function ($, Vue, echarts, common_module, bootstrap) {
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
                    $("#upload_image").modal("show");
                }
            }
        });
    }
});