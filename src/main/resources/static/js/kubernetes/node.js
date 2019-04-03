"use strict";
define([
    "jquery",
    "vue",
    "datatables",
    "bootstrap",
    "common-module",
    'jquery-validate',
    'validate-extend'
], function ($, Vue, DataTables, bootstrap, common_module, jquery_validate, validate_extend) {
    if ($("#k8s_node")[0]) {
        var nodeVue = new Vue({
            el:"#k8s_node",
            data:{
                nodes: [],
                nodeTableObj: null
            },
            mounted: function () {
                var _self = this;
                _self.getNodes();
            },
            methods: {
                refreshTable: function () {
                    var _self = this;
                    if(_self.nodeTableObj != null) {
                        _self.nodeTableObj.destroy();
                    }
                    _self.getNodes();
                },
                checkAll: function (event) {
                    var id = $(event.target).parents('table').attr("id");
                    common_module.checkAll("#" + id);
                },
                checkOne: function (event) {
                    common_module.checkOne(event);
                },
                deleteNode: function (event) {

                },
                getNodes: function () {
                    var _self = this;
                    _self.nodeTableObj = null;
                    $.ajax({
                        url:"../kubernetes/nodesinfo",
                        type:"get",
                        dataType:"json",
                        success: function (result) {
                            debugger
                            if(result.code == 'success'){
                                _self.nodes = result.data;
                                console.log(result.data);
                                common_module.notify("[Kubernetes]","获取Kubernetes节点信息成功",'success');
                            } else {
                                common_module.notify("[Kubernetes]","获取Kubernetes节点信息失败",'danger');
                            }
                            Vue.nextTick(function () {
                                _self.nodeTableObj = common_module.dataTables("#node_table");
                            });
                        },
                        error:function () {
                            common_module.notify("[Kubernetes]","获取Kubernetes节点信息失败",'danger');
                        }
                    });
                },
                showAddDialog: function () {

                    $("#add_node").modal({backdrop: 'static', keyboard: false});
                }
            }
        });

        var validator = $("#add_node_form").validate({
            submitHandler: function (form) {

            },
            ignore: "",
            errorElement: 'div',
            onkeyup: false,
            rules: {
                nodename:{
                    required: true,
                    notEmpty: true
                },
                nodeip: {
                    required: true,
                    notEmpty: true
                }
            },
            messages: {},
            errorPlacement: function (error, element) {
                error.appendTo(element.parent());
            }
        });
    }
});
