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
                    network: {},
                    box: {},
                    isLinkMode: false, //记录是否是连线模式
                    from: null,
                    to: null
                }
            },
            mounted: function(){
                var _self = this;
                _self.getImages();
                _self.init();
            },
            methods: {
                //改变连线模式
                changeLinkMode: function(type) {
                    var _self = this;
                    if(type === 'link'){
                        _self.tWaver.isLinkMode = true;
                    } else {
                        _self.tWaver.isLinkMode = false;
                    }
                    //清空连线起点和终点
                    _self.tWaver.from = null;
                    _self.tWaver.to = null;
                },
                //获取所有镜像
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
                        if(imageArray[i].appName.length > 7){
                            imageArray[i].appNameShow = imageArray[i].appName.slice(0,7);
                        } else {
                            imageArray[i].appNameShow = imageArray[i].appName;
                        }
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
                    network.adjustBounds({x:215, y:0, width:700, height:460});
                    window.onresize = function (e) {
                        network.adjustBounds({x:215, y:0, width:700, height:460});
                    };
                    _self.tWaver.network = network;
                    _self.tWaver.box = box;
                    var rootCanvas = network.getRootCanvas();
                    var topCanvas = network.getTopCanvas();
                    $(rootCanvas).css("background-color", "#ccc");
                    //给topcanvas注册dragover事件
                    $(topCanvas).on("dragover", function(event){
                        //设置canvas允许放在拖动的元素
                        event.preventDefault();
                    });
                    //给topcanvas注册drop事件
                    $(topCanvas).on("drop", function (event) {
                        event.preventDefault();
                        console.log("drop");
                        var dragItem = JSON.parse(event.originalEvent.dataTransfer.getData("text"));
                        var isExist = false;
                        var data = _self.tWaver.box.getDatas()._as;
                        for(var i = 0; i < data.length; i++){
                            if(data[i]._name == dragItem.appName){
                                isExist = true;
                                common_module.notify("[增加模板]", "该镜像已经存在，请重新选择", "warning");
                                break;
                            }
                        }
                        if(!isExist){
                            var node = new twaver.Node();
                            _self.registerNormalImage(dragItem.logo_url, dragItem.appName, 40, 40);
                            node.setName(dragItem.appName);
                            node.setImage(dragItem.appName);
                            node.setLocation(event.offsetX - 40 / 2, event.offsetY - 40 / 2);
                            _self.tWaver.box.add(node);
                        }
                    });
                    var selectionModel = box.getSelectionModel();
                    selectionModel.addSelectionChangeListener(_self.nodeSelectionChangeHandler);
                },
                nodeSelectionChangeHandler: function(e){
                    var _self = this;
                    // 判断是否是连线模式
                    if(_self.tWaver.isLinkMode) {
                        var selectionModel = _self.tWaver.box.getSelectionModel();
                        var last = selectionModel.getLastData();
                        if(!_self.tWaver.from){
                            _self.tWaver.from = last;
                        } else if(!_self.tWaver.to){
                            _self.tWaver.to = last;
                            //创建连线
                            var link = new twaver.Link(_self.tWaver.from, _self.tWaver.to);
                            link.setName('test');
                            // link.setToolTip('<b>Hello!</b>');
                            link.setStyle('arrow.from', false);
                            link.setStyle('arrow.to', true);
                            link.setStyle('arrow.to', true);
                            link.setStyle('arrow.to.fill', true);
                            link.setStyle('arrow.to.shape', 'arrow.short');
                            link.setStyle('arrow.to.color', '#0000ff');
                            _self.tWaver.box.add(link);
                            _self.tWaver.from = _self.tWaver.to;
                            _self.tWaver.to = null;
                        }
                    }
                },
                // 在twaver中注册图片
                registerNormalImage: function(url, name, width, height) {
                    var _self = this;
                    var image = new Image();
                    image.src = url;
                    image.onload = function() {
                        twaver.Util.registerImage(name, image, width, height);
                        image.onload = null;
                        _self.tWaver.network.invalidateElementUIs();
                    };
                },
                startDrag: function( apptype, appid, event ){
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
                    dragItem.logo_url = $(event.target).find("img").attr("src");
                    event.dataTransfer.setData("text", JSON.stringify(dragItem));
                }
            }
        });
    }
});