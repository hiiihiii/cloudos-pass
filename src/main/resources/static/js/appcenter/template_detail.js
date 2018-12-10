/**
 * Created by tanli on 2018/12/10 0010.
 */
"use struct"
define([
    'jquery',
    'vue',
    'bootstrap',
    'jquery-validate',
    'validate-extend',
    'common-module',
    'twaver'
], function ($, Vue, bootstrap, jquery_validate, validate_extend, common_module, Twaver) {
    if($("#template_detail")[0]){
        var templateDetail = new Vue({
            el: '#template_detail',
            data: {},
            mounted: function () {

            },
            methods: {

            }
        });
    }
});