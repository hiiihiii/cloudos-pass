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
            data: {
                roleInfos: []
            },
            mounted: function () {
                var _self = this;
                _self.getData();
                Vue.nextTick(function () {
                    common_module.dataTables("#role_table");
                });
            },
            methods: {
                getData: function () {
                    var _self = this;
                    $.ajax({
                        url: '../role/info',
                        type: 'get',
                        dataType: 'json',
                        async: false,
                        success: function (data) {
                            if(data.code === 'success'){
                                _self.roleInfos = data.data;
                                console.log(_self.roleInfos);
                                common_module.notify('[角色]', '获取角色数据成功', 'success');
                            } else {
                                common_module.notify('[角色]', '获取角色数据失败', 'danger');
                            }
                        },
                        error: function () {
                            common_module.notify('[角色]', '获取角色数据失败', 'danger');
                        }
                    })
                }
            }
        });
    }
});