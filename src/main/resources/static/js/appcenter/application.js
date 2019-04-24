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
                serviceScaleObj: ""
            },
            mounted: function () {
                var _self = this;
                $(".loading").css("display", "block");
                _self.getAppInfo();
                Vue.nextTick(function () {
                    _self.appInsTableObj = common_module.dataTables("#app_table");
                });
                setTimeout(function () {
                    $(".loading").css("display", "none");
                }, 1000);
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

                // 获取应用实例
                getAppInfo: function () {
                    var _self = this;
                    $.ajax({
                        type: "get",
                        url: "../application/info",
                        async: false,
                        dataType: "json",
                        success: function (data) {
                            if(data.code == "success") {
                                _self.appInsInfos = data.data;
                                common_module.notify("[应用实例]", "获取应用实例成功","success");
                            } else {
                                common_module.notify("[应用实例]", "获取应用实例失败","success");
                            }
                        },
                        error: function () {
                            common_module.notify("[应用实例]", "获取应用实例失败","danger");
                        }
                    });
                },

                // 获取服务详情
                getServiceDetail: function ( deployid) {
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
                                common_module.notify("[应用实例]","获取服务实例详情成功", "success");
                            } else {
                                common_module.notify("[应用实例]","获取服务实例详情失败", "danger");
                            }
                        },
                        error: function () {
                            common_module.notify("[应用实例]","获取服务实例详情失败", "danger");
                        }
                    });
                    Vue.nextTick(function () {
                        _self.serviceTableObj = common_module.dataTables("#service_table");
                    }, 0);
                },

                // 隐藏详情框
                hideServiceDetail: function () {
                    var _self = this;
                    _self.serviceDetailFlag = false;
                },

                // 应用启停
                stopOrStart: function (event, deploymentid, type) {
                    var _self = this;
                    var url = "../application/" + type;
                    var typeCN = (type === 'start') ? "启动" : "停止";
                    $.ajax({
                        type: "post",
                        url: url,
                        data: {
                            deploymentId: deploymentid
                        },
                        dataType: "json",
                        success: function (result) {
                            if(result.code==='success') {
                                common_module.notify("[应用实例]", typeCN + "应用实例成功", "success");
                            } else {
                                common_module.notify("[应用实例]", typeCN + "应用实例失败", "danger");
                            }
                        },
                        error: function () {
                            common_module.notify("[应用实例]", typeCN + "应用实例失败", "danger");
                        }
                    })
                },

                // 打开手动伸缩模态框
                showManualScaleDialog: function (event, serviceObj) {
                    var _self = this;
                    _self.serviceScaleObj = serviceObj;
                    $("#manualScale").modal({backdrop: 'static', keyboard: false});
                },

                submitScale: function () {
                    var _self = this;
                    var insNum = $("#manualScale #insNum").val();
                    $(".loading").css("display", 'block');
                    $.ajax({
                        type: "post",
                        url: "../application/scale",
                        data: {
                            serviceName:_self.serviceScaleObj.name,
                            instanceNum: insNum
                        },
                        dataType: "json",
                        success: function (result) {
                            if(result.code =='success') {
                                common_module.notify("[应用实例]","伸缩服务实例成功", "success");
                                $('#manualScale').modal("hide");
                                _self.getServiceDetail(_self.serviceScaleObj.deploy_uuid);
                            } else {
                                common_module.notify("[应用实例]","伸缩服务实例失败", "danger");
                            }
                            setTimeout(function () {
                                $(".loading").css("display", 'none');
                            },100);
                        },
                        error: function () {
                            common_module.notify("[应用实例]","伸缩服务实例失败", "danger");
                            setTimeout(function () {
                                $(".loading").css("display", 'none');
                            },100);
                        }
                    });
                }
            }
        });
    }
});