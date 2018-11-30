"use strict";
define([
    'jquery',
    'vue',
    'bootstrap',
    'jquery-validate',
    'validate-extend'
], function ($, Vue, bootstrap, bootstrapSwitch, jquery_validate, validate_extend) {
    if($("#deploy_image_dialog")[0]){
        new Vue({
            el: 'deploy_image',
            data: {},
            mounted: function () {
                debugger
            },
            methods: {

            }
        });
    }
});