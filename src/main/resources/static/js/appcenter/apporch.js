/**
 * Created by tanli on 2018/7/10 0010.
 */
"use strict"
define([
    'jquery',
    "vue",
    "echarts",
    "common-module",
    "bootstrap",
    "bootstrapSwitch",
    "select2",
    "datatables"
], function ($, Vue, echarts, common_module, bootstrap, bootstrapSwitch, select2, DataTables) {
    if($("#apporch")[0]){
        var orchVue = new Vue({
            el: "#apporch",
            data:{},
            mounted: function(){
                common_module.dataTables("#template_table");
            },
            methods: {

            }
        });
    }
});
