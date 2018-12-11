"use strict";
define([
    "jquery",
    "vue",
    "datatables",
    "bootstrap",
    "common-module"
], function ($, Vue, DataTables, bootstrap, common_module) {
    if($("#system_user")[0]){
        var userVue = new Vue({
            el:"#system_user",
            data: {
                userInfos: [],
                roleInfos: [],
                userTableObj: null
            },
            mounted: function () {
                var _self = this;
                _self.getUserData();
                Vue.nextTick(function () {
                    _self.userTableObj = common_module.dataTables("#user_table");
                });
            },
            methods: {
                getUserData: function () {
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
                },

                getRole: function () {
                    var _self = this;
                    $.ajax({
                        url: '../role/info',
                        type: 'get',
                        dataType: 'json',
                        async: false,
                        success: function (data) {
                            if (data.code === 'success') {
                                _self.roleInfos = data.data;
                                console.log(data.data);
                                common_module.notify('[用户]', '获取角色数据成功', 'success');
                            } else {
                                common_module.notify('[用户]', '获取角色数据失败', 'danger');
                            }
                        },
                        error: function () {
                            common_module.notify('[用户]', '获取角色数据失败', 'danger');
                        }
                    });
                },

                refreshTable: function () {
                    var _self = this;
                    if(_self.userTableObj != null) {
                        _self.userTableObj.destroy();
                    }
                    _self.getUserData();
                    Vue.nextTick(function () {
                        _self.userTableObj = common_module.dataTables("#user_table");
                    });
                },

                showAddDialog: function () {
                    var _self = this;
                    _self.getRole();
                    $("#add_user").modal({backdrop: 'static', keyboard: false});
                },

                submitAdd: function () {
                    var _self = this;
                    var formdata = new FormData();
                    formdata.append("userName", $("#add_user_form input[name='username']").val());
                    formdata.append("role_uuid", $("#add_user_form select").val());
                    formdata.append("password", $("#add_user_form input[name='password']").val());
                    formdata.append("email",$("#add_user_form input[name='email']").val());
                    formdata.append("telephone",$("#add_user_form input[name='telephone']").val());
                    $.ajax({
                        url: '../user/add',
                        type: 'post',
                        data: formdata,
                        dataType: 'json',
                        processData: false,
                        contentType: false,
                        success: function (data) {
                            if(data.code == 'success') {
                                //销毁表格
                                if(_self.userTableObj != null) {
                                    _self.userTableObj.destroy();
                                }
                                _self.getUserData();
                                Vue.nextTick(function () {
                                    _self.userTableObj = common_module.dataTables("#user_table");
                                });
                                common_module.notify('[用户]', '添加用户成功', 'success');
                                $("#add_user").modal('hide');
                            } else {
                                common_module.notify('[用户]', '添加用户失败', 'danger');
                            }
                        },
                        error: function () {
                            common_module.notify('[用户]', '添加用户失败', 'danger');
                        }
                    });
                },

                //todo 后期将参数id类型要改为数组，以适应多删除
                deleteById: function (ids) {
                    var _self = this;
                    $.ajax({
                        url: '../user/delete',
                        type: 'post',
                        data: {

                        },
                        dataType: 'json',
                        success: function (data) {
                            if(data.code == "success"){} else {}
                        },
                        error: function () {

                        }
                    })
                }
            }
        });

        $('#add_user').on('hide.bs.modal', function () {
            $("#add_user_form input").val("");
            $("#add_user_form select").val("");
        });
    }
});