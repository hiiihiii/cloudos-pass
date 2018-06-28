"use strict";
define([
    'jquery',
    "vue",
    "echarts",
    "common-module",
    "bootstrap",
    "bootstrapSwitch",
    "select2"
],function ($, Vue, echarts, common_module, bootstrap, bootstrapSwitch, select2) {
    if($("#appstore")[0]){
        var appstore = new Vue({
            el: "#appstore",
            data: {
                appType: "public",
                showPublic: true,
                showPrivate: false
            },
            mounted: function () {
                var _self = this;
                _self.showPublic = true;
                _self.showPrivate = false;
            },
            methods: {
                //显示上传镜像框框
                showUploadDialog: function () {
                    //select2初始化
                    $("#upload_image_form select[name='appTag']").select2({
                        dropdownParent: $('#upload_image'),
                        minimumResultsForSearch: -1//去除搜索框
                    });
                    $("#upload_image_form select[name='appTag']").val("");
                    //开关按钮初始化
                    $("#upload_image_form input[type='checkbox']").bootstrapSwitch({
                        onText: '',
                        offText: '',
                        onSwitchChange: function (event, state) {
                            if(state){

                            } else {

                            }
                        }
                    });
                    $("#upload_image").modal({backdrop: 'static', keyboard: false});
                }
            }
        });

        $('#upload_image').on('shown.bs.modal', function () {

        });
    }
});