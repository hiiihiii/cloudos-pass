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
                roleInfos: [],
                roleTableObj: ''
            },
            mounted: function () {
                var _self = this;
                $(".loading").css('display','block');
                _self.getRoleData();
                Vue.nextTick(function () {
                    _self.roleTableObj = common_module.dataTables("#role_table");
                });
                setTimeout(function () {
                    $(".loading").css('display','none');
                }, 1000);
            },
            methods: {
                checkAll: function (event) {
                    var id = $(event.target).parents('table').attr("id");
                    common_module.checkAll("#" + id);
                },
                checkOne: function (event) {
                    common_module.checkOne(event);
                },
                getRoleData: function () {
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
                },
                refreshTable: function () {
                    var _self = this;
                    $(".loading").css('display','block');
                    if(_self.roleTableObj != null) {
                        _self.roleTableObj.destroy();
                    }
                    _self.getRoleData();
                    Vue.nextTick(function () {
                        _self.roleTableObj = common_module.dataTables("#role_table");
                    });
                    setTimeout(function () {
                        $(".loading").css('display','none');
                    },1000);
                }
            }
        });
    }
});