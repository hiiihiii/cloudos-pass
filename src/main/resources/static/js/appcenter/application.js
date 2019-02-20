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
                                // _self.appInsInfos = _self.convertData(data.data);
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

                //解析数据
                convertData: function (appInfoArray) {
                    for(var i = 0; i < appInfoArray.length; i++){
                        appInfoArray[i].relation = JSON.parse(templateArray[i].relation);
                        appInfoArray[i].config = JSON.parse(templateArray[i].config);
                        appInfoArray[i].temp_logo_url = "ftp://docker:dockerfile@" + templateArray[i].logo_url;
                    }
                    return appInfoArray;
                }
            }
        });
    }
});