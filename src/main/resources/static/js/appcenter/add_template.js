"use struct"
define([
    'jquery',
    'vue',
    'bootstrap',
    'jquery-validate',
    'validate-extend',
    'common-module'
], function ($, Vue, bootstrap, bootstrapSwitch, jquery_validate, validate_extend, common_module) {
    if($("#add_template_dialog")[0]){
        var add_template = new Vue({
            el: "#add_template",
            data: {
                imageInfoList: [],
                DBMSList:"",
                appTagToShow: "DBMS",
                DBMSList: [],
                WebServerList: [],
                ApplicationList: [],
                OtherList: [],
                DBMSCount: 0,
                WebServerCount: 0,
                ApplicationCount: 0,
                OtherCount: 0,
            },
            mounted: function(){
                var _self = this;
                _self.getImages();
            },
            methods: {
                getImages: function(){
                    var _self = this;
                    $.ajax({
                        url: "../apporch/appinfo",
                        type: "get",
                        dataType: "json",
                        async: false,
                        success: function(data){
                            if(data.code === "success"){
                                _self.imageInfoList = _self.convertData(data.data);
                                console.log(_self.imageInfoList);
                                common_module.notify("[应用编排]", "获取镜像数据成功", "success");
                            } else {
                                common_module.notify("[应用编排]", "获取镜像数据失败", "danger");
                            }
                        },
                        error: function () {
                            common_module.notify("[应用编排]", "获取镜像数据失败", "danger");
                        }
                    });
                },
                convertData: function (imageArray) {
                    var _self = this;
                    _self.DBMSList = [];
                    _self.WebServerList = [];
                    _self.ApplicationList = [];
                    _self.OtherList =  [];
                    for(var i = 0; i < imageArray.length; i++){
                        imageArray[i].createType = JSON.parse(imageArray[i].createType);
                        imageArray[i].metadata = JSON.parse(imageArray[i].metadata);
                        imageArray[i].source_url = JSON.parse(imageArray[i].source_url);
                        imageArray[i].v_description = JSON.parse(imageArray[i].v_description);
                        imageArray[i].version = JSON.parse(imageArray[i].version);
                        imageArray[i].logo_url = "ftp://docker:dockerfile@" + imageArray[i].logo_url;
                        switch (imageArray[i].appTag){
                            case "DBMS":
                                _self.DBMSList.push(imageArray[i]);
                                break;
                            case "WebServer":
                                _self.WebServerList.push(imageArray[i]);
                                break;
                            case "Application":
                                _self.ApplicationList.push(imageArray[i]);
                                break;
                            default:
                                _self.OtherList.push(imageArray[i]);
                        }
                    }
                    _self.DBMSCount = _self.DBMSList.length;
                    _self.WebServerCount = _self.WebServerList.length;
                    _self.ApplicationCount = _self.ApplicationList.length;
                    _self.OtherCount = _self.OtherList.length;
                    return imageArray;
                },
                // 设置默认图标
                setDefault: function (event) {
                    $(event.target).attr("src", "../images/app-default.png");
                },
                changeAppTag: function(apptag){
                    var _self = this;
                    _self.appTagToShow = apptag;
                }
            }
        });
    }
});