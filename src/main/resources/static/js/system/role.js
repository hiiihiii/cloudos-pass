"use strict";
define([
    "jquery",
    "vue",
    "datatables",
    "common-module"
],function ($, Vue, DataTables, common_module) {
    if($("#system_role")[0]){
        var roleVue = new Vue({
            el:"#system_role",
            data: {},
            mounted: function () {
                common_module.dataTables("#role_table");
            },
            methods: {
                getData: function () {

                }
            }
        });
    }
});