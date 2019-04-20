/**
 * Created by tanli on 2018/7/10 0010.
 */
"use strict"
define([
    'jquery',
    "vue",
    "echarts",
    "common-module",
    "bootstrap",
    "bootstrapSwitch",
    "select2",
    "datatables"
], function ($, Vue, echarts, common_module, bootstrap, bootstrapSwitch, select2, DataTables) {
    if($("#apporch")[0]){
        var orchVue = new Vue({
            el: "#apporch",
            data:{
                templateList: [],
                templateTableObj: '',
                editTemplate: {
                    uuid:'',
                    templateName:'',
                    logo_url: '',
                    description:''
                },
                logoFileName:'',
            },
            mounted: function(){
                var _self = this;
                $(".loading").css("display", "block");
                _self.getTemplate();
                Vue.nextTick(function () {
                    _self.templateTableObj = common_module.dataTables("#template_table");
                });
                setTimeout(function () {
                    $(".loading").css("display", "none");
                }, 1000);
            },
            methods: {
                //全选或反选
                checkAll: function(event){
                    var id = $(event.target).parents('table').attr("id");
                    common_module.checkAll("#" + id);
                },
                //单选
                checkOne: function (event) {
                    common_module.checkOne(event);
                },
                //获取镜像模板信息
                getTemplate: function () {
                    var _self = this;
                    $.ajax({
                        url: "../apporch/templateinfo",
                        type: "get",
                        async: false,
                        dataType: "json",
                        success: function(data){
                            if(data.code === "success"){
                                debugger
                                _self.templateList = _self.convertData(data.data);
                                console.log(_self.templateList);
                                common_module.notify("[应用编排]", "获取模板信息成功","success");
                            } else{
                                common_module.notify("[应用编排]", "获取模板信息失败","danger");
                            }
                        },
                        error: function () {
                            common_module.notify("[应用编排]", "获取模板信息失败","danger");
                        }
                    });
                },
                //跳去模板详情页面
                toDetail: function (event) {
                    var id = $(event.target).attr("id");
                    sessionStorage.setItem("templateId", id);
                    //在新页面打开
                    window.open('../apporch/templatedetail')
                },
                //解析数据
                convertData: function (templateArray) {
                    for(var i = 0; i < templateArray.length; i++){
                        templateArray[i].relation = JSON.parse(templateArray[i].relation);
                        templateArray[i].config = JSON.parse(templateArray[i].config);
                        templateArray[i].temp_logo_url = "ftp://docker:dockerfile@" + templateArray[i].logo_url;
                    }
                    return templateArray;
                },
                //发布镜像模板
                publishTemplate: function (templateId, templateName) {
                    console.log(templateId);
                    var _self = this;
                    $.ajax({
                        url: "../apporch/publish",
                        type: "post",
                        data: {
                            templateId: templateId
                        },
                        dataType: 'json',
                        success: function (data) {
                            if(data.code == 'success'){
                                //销毁表格
                                if(_self.templateTableObj != null) {
                                    _self.templateTableObj.destroy();
                                }
                                _self.getTemplate();
                                Vue.nextTick(function () {
                                    _self.templateTableObj = common_module.dataTables("#template_table");
                                });
                                common_module.notify('[应用编排]','发布镜像' + templateName + '成功','success');
                            } else {
                                common_module.notify('[应用编排]','发布镜像' + templateName + '失败','danger');
                            }
                        },
                        error: function () {
                            common_module.notify('[应用编排]','发布镜像' + templateName + '失败','danger');
                        }
                    });
                },
                //删除模板，包括单删和多删
                deleteByIds: function (event, type) {
                    var _self = this;
                    var ids = [];
                    if('one' === type) {
                        ids.push($(event.target).parents('tr').attr('id'));
                    } else if('multiple' === type) {
                        $("#template_table tbody").children('tr').each(function(index,element) {
                            var checkbox = $(element).find("input[type='checkbox']")[0];
                            if($(checkbox).prop("checked")) {
                                ids.push($(element).attr('id'));
                            }
                        });
                    }
                    if(ids.length === 0){
                        common_module.notify('[应用模板]', '请选择要删除的模板', 'danger');
                        return;
                    }
                    $.ajax({
                        url: '../apporch/delete',
                        type: 'post',
                        data: {
                            ids: ids
                        },
                        dataType: 'json',
                        success: function (data) {
                            if(data.code == "success"){
                                //销毁表格
                                if(_self.templateTableObj != null) {
                                    _self.templateTableObj.destroy();
                                }
                                _self.getTemplate();
                                Vue.nextTick(function () {
                                    _self.templateTableObj = common_module.dataTables("#template_table");
                                });
                                console.log(data.data);
                                if(data.data.success == ids.length) {
                                    common_module.notify('[应用模板]',
                                        "删除模板成功" + data.data.success + "个，失败" + data.data.fail + "个", 'success');
                                } else {
                                    common_module.notify('[应用模板]',
                                        "删除模板成功" + data.data.success  +"个，失败" + data.data.fail + "个", 'warning');
                                }
                            } else {
                                common_module.notify('[应用模板]',"删除模板失败", 'danger');
                            }
                        },
                        error: function () {
                            common_module.notify('[应用模板]',"删除模板失败", 'danger');
                        }
                    })
                },
                //表格刷新
                refreshTable: function () {
                    var _self = this;
                    if(_self.templateTableObj != null) {
                        _self.templateTableObj.destroy();
                    }
                    _self.getTemplate();
                    Vue.nextTick(function () {
                        _self.templateTableObj = common_module.dataTables("#template_table");
                    });
                },

                showEditTemplate: function (templateid) {
                    var _self = this;
                    for(var i = 0; i < _self.templateList.length; i++) {
                        if(_self.templateList[i].uuid == templateid) {
                            _self.editTemplate = _self.templateList[i];
                            _self.logoFileName = _self.templateList[i].logo_url;
                            break;
                        }
                    }
                    console.log(_self.editTemplate);
                    $("#edit_template").modal({backdrop: 'static', keyboard: false});
                },

                fileChange: function (event) {
                    var _self = this;
                    var logoFile = document.getElementById("logo");
                    if(logoFile.files[0]){
                        _self.logoFileName = logoFile.files[0].name;
                    }
                },
                submitEdit: function () {
                    var _self = this;
                    var formdata = new FormData();
                    formdata.append("uuid", _self.editTemplate.uuid);
                    formdata.append("description", _self.editTemplate.description);
                    if(_self.logoFileName != _self.editTemplate.logo_url) {
                        formdata.append("logoFile", $("#edit_template_form #logo")[0].files[0]);
                    }
                    $(".loading").css("display", "block");
                    $.ajax({
                        url:'../apporch/template/edit',
                        type: 'post',
                        data: formdata,
                        processData: false,
                        contentType: false,
                        dataType:'json',
                        async: false,
                        success: function (result) {
                            if(result.code=='success') {
                                $("#edit_template").modal('hide');
                                common_module.notify('[应用编排]','修改'+_self.editTemplate.templateName+"成功",'success');
                                _self.getTemplate();
                            } else {
                                common_module.notify('[应用编排]','修改'+_self.editTemplate.templateName+"失败",'danger');
                            }
                            setTimeout(function () {
                                $(".loading").css("display", 'none');
                            },1000);
                        },
                        error: function () {
                            common_module.notify('[应用编排]','修改'+_self.editTemplate.templateName+"失败",'danger');
                            setTimeout(function () {
                                $(".loading").css("display", 'none');
                            },1000);
                        }
                    });
                }
            }
        });
    }
});
