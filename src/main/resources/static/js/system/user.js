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
            data: {
                userInfos: []
            },
            mounted: function () {
                var _self = this;
                _self.getData();
                Vue.nextTick(function () {
                    common_module.dataTables("#user_table");
                });
            },
            methods: {
                getData: function () {
                    var _self = this;
                    $.ajax({
                        url: '../user/info',
                        type: 'get',
                        dataType: 'json',
                        async: false,
                        success: function (data) {
                            if (data.code === 'success') {
                                _self.userInfos = data.data;
                                console.log(data.data);
                                common_module.notify('[用户]', '获取用户数据成功', 'success');
                            } else {
                                common_module.notify('[用户]', '获取用户数据失败', 'danger');
                            }
                        },
                        error: function () {
                            common_module.notify('[用户]', '获取用户数据失败', 'danger');
                        }
                    });
                }
            }
        });
    }
});