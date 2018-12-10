/**
 * Created by tanli on 2018/12/10 0010.
 */
"use struct"
define([
    'jquery',
    'vue',
    'bootstrap',
    'jquery-validate',
    'validate-extend',
    'common-module',
    'twaver'
], function ($, Vue, bootstrap, jquery_validate, validate_extend, common_module, Twaver) {
    if($("#template_detail")[0]){
        var templateDetail = new Vue({
            el: '#template_detail',
            data: {
                templateDetail: {}
            },
            mounted: function () {
                var _self = this;
                _self.getDetail();
            },
            methods: {
                getDetail: function () {
                    var _self = this;
                    var templateId = sessionStorage.getItem("templateId");
                    $.ajax({
                        url: '../apporch/templateinfo/detail',
                        type: 'get',
                        data: {
                            templateId: templateId
                        },
                        dataType: 'json',
                        success: function (data) {
                            if(data.code === 'success') {
                                _self.templateDetail = _self.convertData(data.data);
                                common_module.notify('[应用编排]','获取模板详情成功','success');
                            } else {
                                common_module.notify('[应用编排]','获取模板详情失败','danger');
                            }
                        },
                        error: function () {
                            common_module.notify('[应用编排]','获取模板详情失败','danger');
                        }
                    })
                },

                convertData: function (templateInfo) {
                    templateInfo.relation = JSON.parse(templateInfo.relation);
                    templateInfo.config = JSON.parse(templateInfo.config);
                    templateInfo.temp_logo_url = "ftp://docker:dockerfile@" + templateInfo.logo_url;
                    return templateInfo;
                }
            }
        });
    }
});