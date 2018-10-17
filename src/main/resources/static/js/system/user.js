"use strict";
define([
    "jquery",
    "vue",
    "datatables",
    "common-module"
], function ($, Vue, DataTables, common_module) {
    if($("#system_user")[0]){
        var roleVue = new Vue({
            el:"#system_user",
            data: {},
            mounted: function () {
                debugger;
                common_module.dataTables("#user_table");
            },
            methods: {
                getData: function () {

                }
            }
        });
    }
});