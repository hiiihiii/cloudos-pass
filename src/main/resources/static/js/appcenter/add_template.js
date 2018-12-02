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
                tWaver:{
                    network: "",
                    box: ""
                }
            },
            mounted: function(){
                var _self = this;
                _self.getImages();
                _self.init();
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
                // 选择不同的标签
                changeAppTag: function(apptag){
                    var _self = this;
                    _self.appTagToShow = apptag;
                },
                // 初始化画布
                init: function () {
                    var _self = this;
                    var box = new twaver.ElementBox(); //容器
                    var network = new twaver.vector.Network(box); //页面上的画布
                    document.getElementById("canvas-box").appendChild(network.getView());
                    network.adjustBounds({x:215,y:0,width:700,height:450});
                    window.onresize = function (e) {
                        network.adjustBounds({x:215,y:0,width:700,height:450});
                    };
                    _self.tWaver.network = network;
                    _self.tWaver.box = box;
                    //设置canvas允许放在拖动的元素
                    $("#canvas-box").on("dragover", "canvas", function(event){
                        event.preventDefault();
                        console.log("dropover")
                    });
                    $("#canvas-box").on("drop", "canvas", function (event) {
                        event.preventDefault();
                        var dragItem = JSON.parse(event.originalEvent.dataTransfer.getData("text"));
                        console.log(dragItem);

                    })
                },
                startDrag: function(event, apptype, appid){
                    console.log("startdrag");
                    var _self = this;
                    var dragItem, temList;
                    switch (apptype) {
                        case "DBMS":
                           temList = _self.DBMSList;
                           break;
                        case "WebServer":
                            temList = _self.WebServerList;
                            break;
                        case "Application":
                            temList = _self.ApplicationList;
                            break;
                        default:
                            temList = _self.OtherList;
                    }
                    for(var i = 0; i < temList.length; i++){
                        if(temList[i].app_id == appid){
                            dragItem = temList[i];
                            break;
                        }
                    }
                    event.dataTransfer.setData("text", JSON.stringify(dragItem));
                    console.log(dragItem);
                }
            }
        });
    }
});