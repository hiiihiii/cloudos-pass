"use strict";
define([
    'jquery',
    "vue",
    "echarts",
    "common-module",
    "bootstrap",
    "bootstrapSwitch",
    "select2",
    "jpages"
],function ($, Vue, echarts, common_module, bootstrap, bootstrapSwitch, select2, jPages) {
    if($("#appstore")[0]){
        var appstore = new Vue({
            el: "#appstore",
            data: {
                appType: "public",
                showPublic: true,
                showPrivate: false,
                imageInfos: ""
            },
            mounted: function () {
                var _self = this;
                _self.showPublic = true;
                _self.showPrivate = false;
                //设置分类全部选中
                $(".app-items .cloud-checkbox").attr("checked", "true");
                //为分类选择绑定事件
                _self.classifyBind();
                //todo 这里不应该是image，而是all
                _self.getAppData("public", "image");
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
                },
                classifyBind: function(){
                    $("#appstore .nav").on("change", "input[type='chec  kbox']", function(){
                        debugger
                        var status = this.getAttribute("checked");//选中时status为"checked"
                        var isPublic = false;
                        var isAll = false;
                        if($(this).parent().parent().attr("id") === "public_repo") {
                            isPublic = true;
                        }
                        if($(this).hasClass("app-type-all")){
                            isAll = true;
                        }
                    });
                },
                getAppData: function (repoType, appType) {
                    var _self = this;
                    $.ajax({
                        url: "../appcenter/appinfo",
                        type: "get",
                        data: {
                            repoType: repoType,
                            appType: appType
                        },
                        dataType: "json",
                        // processData: false,
                        // contentType: false,
                        success: function (data) {
                            _self.imageInfos = _self.convertData(data.data);
                            console.log(_self.imageInfos);
                            common_module.notify("[应用中心]","获取镜像数据成功", "success");
                        },
                        error: function () {
                            common_module.notify("[应用中心]","获取镜像数据失败", "danger");
                        }
                    })
                },
                convertData: function(imageArray){
                    var _self = this;
                    for(var i = 0; i < imageArray.length; i++){
                        debugger
                        imageArray[i].createType = JSON.parse(imageArray[i].createType);
                        imageArray[i].metadata = JSON.parse(imageArray[i].metadata);
                        imageArray[i].source_url = JSON.parse(imageArray[i].source_url);
                        imageArray[i].v_description = JSON.parse(imageArray[i].v_description);
                        imageArray[i].version = JSON.parse(imageArray[i].version);
                    }
                    return imageArray;
                },
                changeRepo: function (repoType) {
                    var _self = this;
                    _self.appType = repoType;
                    if(repoType === 'public'){
                        _self.showPrivate = false;
                        _self.showPublic = true;
                    } else{
                        _self.showPrivate = true;
                        _self.showPublic = false;
                    }
                }
            }
        });

    }
});