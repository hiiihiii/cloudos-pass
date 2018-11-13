"use struct"
define([
    'jquery',
    'vue',
    'bootstrap',
    'jquery-validate',
    'validate-extend'
], function ($, Vue, bootstrap, bootstrapSwitch, jquery_validate, validate_extend) {
    if($("#add_template_dialog")[0]){
        var add_template = new Vue({
            el: "#add_template",
            data: {},
            mounted: function(){

            },
            methods: {}
        });
    }
});