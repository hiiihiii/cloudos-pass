"use strict";
define([
    'jquery',
    'vue',
    'bootstrap'
], function ($, Vue, bootstrap, bootstrapSwitch) {
    if($("#upload_image_dialog")[0]){
        var upload_image = new Vue({
            el: "#upload_image",
            data: {},
            mounted: function () {

            },
            methods: {

            }
        });
    }
});