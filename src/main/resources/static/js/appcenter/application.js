/**
 * Created by tanli on 2018/7/10 0010.
 */
define([
    "jquery",
    "vue",
    "datatables",
    "bootstrap",
    "common-module"
], function ($, Vue, DataTables, bootstrap, common_module) {
    if($("#application")[0]){
        var application = new Vue({
            el:"#system_user",
            data: {
                appInsInfos: [],
                appInsTableObj: null
            },
            mounted: function () {

            },
            methods:{
                checkAll: function (event) {
                    var id = $(event.target).parents('table').attr("id");
                    common_module.checkAll("#" + id);
                },
                checkOne: function (event) {
                    common_module.checkOne(event);
                }
            }
        });
    }
});