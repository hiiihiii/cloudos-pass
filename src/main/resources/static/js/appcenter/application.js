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
            el:"#application",
            data: {
                appInsInfos: [],
                appInsTableObj: null,
                serviceInfos: [],
                serviceTableObj: null,
            },
            mounted: function () {
                var _self = this;
                _self.getAppInfo();
                Vue.nextTick(function () {
                    _self.appInsTableObj = common_module.dataTables("#app_table");
                });
            },
            methods:{
                checkAll: function (event) {
                    var id = $(event.target).parents('table').attr("id");
                    common_module.checkAll("#" + id);
                },

                checkOne: function (event) {
                    common_module.checkOne(event);
                },

                getAppInfo: function () {
                    var _self = this;
                    $.ajax({
                        type: "get",
                        url: "../application/info",
                        async: false,
                        dataType: "json",
                        success: function (data) {
                            if(data.code == "success") {
                                debugger;
                                _self.appInsInfos = data.data;
                                console.log(_self.appInsInfos);
                                common_module.notify("[应用实例]", "获取应用信息成功","success");
                            } else {
                                common_module.notify("[应用实例]", "获取应用信息失败","success");
                            }
                        },
                        error: function () {
                            common_module.notify("[应用实例]", "获取应用信息失败","danger");
                        }
                    });
                },

                getServiceDetail: function (event, deployid) {
                    debugger
                    var _self = this;
                    $.ajax({
                        type: "get",
                        url: "../application/serviceInfo",
                        data: {
                            deployId: deployid
                        },
                        dataType: "json",
                        async: false,
                        success: function (data) {
                            if(data.code == 'success') {
                                console.log(data.data);
                                _self.serviceInfos = data.data;
                            } else {

                            }
                        },
                        error: function () {

                        }
                    });
                    Vue.nextTick(function () {
                        _self.serviceTableObj = common_module.dataTables("#service_table");
                    });
                }
            }
        });
    }
});