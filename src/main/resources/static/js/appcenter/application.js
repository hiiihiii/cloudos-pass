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
                serviceDetailFlag: false,
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
                creating: function (status) {
                    if(status == '正在创建'){
                        return true;
                    }
                    return false;
                },
                running: function (status) {
                    if(status == '运行中'){
                        return true;
                    }
                    return false;
                },
                unknown: function (status) {
                    if(status == '异常'){
                        return true;
                    }
                    return false;
                },
                stop: function (status) {
                    if(status == '停止'){
                        return true;
                    }
                    return false;
                },
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
                    _self.serviceDetailFlag = true;
                    if(_self.serviceTableObj != null) {
                        _self.serviceTableObj.destroy();
                    }
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
                },
                hideServiceDetail: function () {
                    var _self = this;
                    _self.serviceDetailFlag = false;
                    if(_self.serviceTableObj != null) {
                        _self.serviceTableObj.destroy();
                    }
                },
                stopOrStart: function (event, deploymentid, type) {
                    var _self = this;
                    var url = "../application/" + type;
                    $.ajax({
                        type: "post",
                        url: url,
                        data: {
                            deploymentId: deploymentid
                        },
                        dataType: "json",
                        success: function (result) {

                        },
                        error: function () {

                        }
                    })
                }
            }
        });
    }
});