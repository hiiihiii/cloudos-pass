"use strict";
define([
    "jquery",
    "vue",
    "datatables",
    "common-module"
],function ($, Vue, DataTables, common_module) {
    if($("#userlog")[0]){
        var roleVue = new Vue({
            el:"#userlog",
            data: {
                logList: [],
                logTableObj: ''
            },
            mounted: function () {
                var _self = this;
                _self.getLogData();
                Vue.nextTick(function () {
                    _self.logTableObj = common_module.dataTables("#log_table");
                });
            },
            methods: {
                checkAll: function (event) {
                    var id = $(event.target).parents('table').attr("id");
                    common_module.checkAll("#" + id);
                },
                checkOne: function (event) {
                    common_module.checkOne(event);
                },
                getLogData: function () {
                    var _self = this;
                    $.ajax({
                        url: '../userlog/info',
                        type: 'get',
                        dataType: 'json',
                        async: false,
                        success: function (data) {
                            if(data.code === 'success'){
                                _self.logList = data.data;
                                console.log(_self.logList);
                                common_module.notify('[日志]', '获取日志数据成功', 'success');
                            } else {
                                common_module.notify('[日志]', '获取日志数据失败', 'danger');
                            }
                        },
                        error: function () {
                            common_module.notify('[日志]', '获取日志数据失败', 'danger');
                        }
                    })
                },
                refreshTable: function () {
                    var _self = this;
                    if(_self.logTableObj != null) {
                        _self.logTableObj.destroy();
                    }
                    _self.getLogData();
                    Vue.nextTick(function () {
                        _self.logTableObj = common_module.dataTables("#log_table");
                    });
                },
                deleteByIds: function (event, type) {
                    var _self = this;
                    var ids = [];
                    if('one' === type) {
                        ids.push($(event.target).parents('tr').attr('id'));
                    } else if('multiple' === type) {
                        $("#log_table tbody").children('tr').each(function(index,element) {
                            var checkbox = $(element).find("input[type='checkbox']")[0];
                            if($(checkbox).prop("checked")) {
                                ids.push($(element).attr('id'));
                            }
                        });
                    }
                    if(ids.length === 0){
                        common_module.notify('[日志]', '请选择要删除的日志', 'danger');
                        return;
                    }
                    $.ajax({
                        url: '../userlog/delete',
                        type: 'post',
                        data: {
                            ids: ids
                        },
                        dataType: 'json',
                        success: function (data) {
                            if(data.code == "success"){
                                //销毁表格
                                if(_self.logTableObj != null) {
                                    _self.logTableObj.destroy();
                                }
                                _self.getUserData();
                                Vue.nextTick(function () {
                                    _self.logTableObj = common_module.dataTables("#log_table");
                                });
                                console.log(data.data);
                                if(data.data.success == ids.length) {
                                    common_module.notify('[用户]',
                                        "删除日志成功" + data.data.success + "条，失败" + data.data.fail + "条", 'success');
                                } else {
                                    common_module.notify('[日志]',
                                        "删除日志成功" + data.data.success  +"条，失败" + data.data.fail + "条", 'warning');
                                }
                            } else {
                                common_module.notify('[日志]',"删除日志失败", 'danger');
                            }
                        },
                        error: function () {
                            common_module.notify('[日志]',"删除日志失败", 'danger');
                        }
                    })
                }

            }
        });
    }
});