/**
 * Created by tanli on 2018/12/5 0005.
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
    if($("#template")[0]){
        var template = new Vue({
            el: "#template",
            data: {
                imageInfoList: [],
                appTagToShow: "DBMS",
                DBMSList: [],
                WebServerList: [],
                ApplicationList: [],
                OtherList: [],
                DBMSCount: 0,
                WebServerCount: 0,
                ApplicationCount: 0,
                OtherCount: 0,
                twaverObj:{
                    network: {},
                    box: {},
                    isLinkMode: false, //记录是否是连线模式
                    from: null,
                    to: null,
                    selectedApp: null
                },
                selected: {
                    version: '',
                    limits: {},
                    requests: {},
                    env: [],
                    ports: []
                }
            },
            mounted: function () {
                var _self = this;
                _self.getImages();
                _self.init();
            },
            methods: {
                //改变连线模式
                changeLinkMode: function(type) {
                    var _self = this;
                    if(type === 'link'){
                        _self.twaverObj.isLinkMode = true;
                        // _self.twaverObj.network.setCreateLinkInteractions();
                    } else {
                        _self.twaverObj.isLinkMode = false;
                        // _self.twaverObj.network.setDefaultInteractions();
                    }
                    //清空连线起点和终点
                    _self.twaverObj.from = null;
                    _self.twaverObj.to = null;

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
                            imageArray[i].appNameShow = imageArray[i].appName.slice(0,7) + "...";
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
                },

                // 初始化画布
                init: function () {
                    var _self = this;
                    var box = new twaver.ElementBox(); //容器
                    var network = new twaver.vector.Network(box); //页面上的画布
                    document.getElementById("canvas-box").appendChild(network.getView());
                    network.adjustBounds({x:228, y:100, width:823, height:535});
                    window.onresize = function (e) {
                        network.adjustBounds({x:228, y:100, width:823, height:535});
                    };
                    _self.twaverObj.network = network;
                    _self.twaverObj.box = box;
                    var rootCanvas = network.getRootCanvas();
                    var topCanvas = network.getTopCanvas();
                    //给topcanvas注册dragover、drop事件
                    $(topCanvas).on("dragover", function(event){
                        //设置canvas允许放在拖动的元素
                        event.preventDefault();
                    });
                    $(topCanvas).on("drop", function (event) {
                        event.preventDefault();
                        console.log("drop");
                        var dragItem = JSON.parse(event.originalEvent.dataTransfer.getData("text"));
                        var isExist = false;
                        var data = _self.twaverObj.box.getDatas()._as;
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
                            _self.twaverObj.box.add(node);
                        }
                    });
                    //画布画网格
                    network.paintBottom = _self.drawGrid;
                    /**
                     * todo 可以考虑使用twaver自带的连线模式，但存在的问题是使用自带的连线模式时怎么设置link的样式
                     */
                    box.getSelectionModel().addSelectionChangeListener(_self.nodeSelectionChangeHandler);
                    _self.popupMenuInit();
                },

                //右键删除
                popupMenuInit: function(){
                    var _self = this;
                    var popupmenu = new twaver.controls.PopupMenu(_self.twaverObj.network);
                    popupmenu.setMenuItems([
                        {'label': "删除"}
                    ]);
                    popupmenu.onMenuItemRendered = function (div, menuItem) {
                        div.parentElement.style.width = "100px";
                    };
                    //设置菜单是否显示
                    popupmenu.isVisible = function (menuItem) {
                        var selectionModel = _self.twaverObj.box.getSelectionModel();
                        var last = selectionModel.getLastData();
                        return last!= null ? true : false;
                    };
                    popupmenu.onAction = function (menuItem) {
                        var selectionModel = _self.twaverObj.box.getSelectionModel();
                        var last = selectionModel.getLastData();
                        console.log(last);
                        if(menuItem.label == '删除'){
                            var id = last.getId();
                            if(last instanceof twaver.Link){
                                _self.twaverObj.box.removeById(id);
                            } else {
                                //删除node上的link
                                var links = last.getLinks();
                                if(links){
                                    for(var i = 0; i < links._as.length; i++ ){
                                        _self.twaverObj.box.removeById(links._as[i].getId());
                                    }
                                }
                                //删除node
                                _self.twaverObj.box.removeById(id);
                                _self.twaverObj.box.selectedApp = null;
                            }
                            console.log("删除");
                        }
                    };
                },

                //画网格
                drawGrid: function (ctx, dirtyRect){
                    var _self = this;
                    var rootCanvas = _self.twaverObj.network.getRootCanvas();
                    ctx.fillStyle = '#ccc';
                    ctx.fillRect(0,0,rootCanvas.width,rootCanvas.height);
                    ctx.lineWidth = 0.2;
                    ctx.strokeStyle = '#fff';
                    for (var i = 10; i < ctx.canvas.width; i += 10) {
                        ctx.beginPath();
                        ctx.moveTo(i, 0);
                        ctx.lineTo(i, ctx.canvas.height);
                        ctx.closePath();
                        ctx.stroke();
                    }
                    for (var j = 10; j < ctx.canvas.height; j += 10) {
                        ctx.beginPath();
                        ctx.moveTo(0, j);
                        ctx.lineTo(ctx.canvas.width, j);
                        ctx.closePath();
                        ctx.stroke();
                    }
                },

                //网元选中事件处理器
                nodeSelectionChangeHandler: function(e){
                    var _self = this;
                    var selectionModel = _self.twaverObj.box.getSelectionModel();
                    var last = selectionModel.getLastData();
                    // 判断是否是连线模式
                    if(_self.twaverObj.isLinkMode && last!=null) {
                        if(!_self.twaverObj.from){
                            _self.twaverObj.from = last;
                        } else if(!_self.twaverObj.to){
                            _self.twaverObj.to = last;
                            /**
                             * 创建连线，
                             * 连线样式设置参考http://doc.servasoft.com/twaver-document-center/recommended/twaver-html5-guide/%E7%BD%91%E5%85%83%E6%A0%B7%E5%BC%8F%E8%A1%A8/#link
                             */
                            var link = new twaver.Link(_self.twaverObj.from, _self.twaverObj.to);
                            link.setName('test');
                            // link.setToolTip('<b>Hello!</b>');
                            link.setStyle('arrow.to', true);
                            link.setStyle('arrow.to.shape', 'arrow.short');
                            link.setStyle('arrow.to.color', '#ffffff');
                            link.setStyle("link.type", "flexional");
                            link.setStyle("link.width", "2");
                            link.setStyle("link.color", "#ffffff");
                            _self.twaverObj.box.add(link);
                            //完成一次连线就清空起始节点和终点
                            _self.twaverObj.from = null;
                            _self.twaverObj.to = null;
                        }
                    }
                    if(last != null){
                        var imageName = last.getName();
                        console.log(imageName);
                        _self.showSelectedAppInfo(imageName);
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
                        _self.twaverObj.network.invalidateElementUIs();
                    };
                },

                //显示画布中选中的应用的信息
                showSelectedAppInfo: function (imageName) {
                    var _self = this;
                    for(var i = 0; i < _self.imageInfoList.length; i++){
                        if(_self.imageInfoList[i].appName == imageName){
                            _self.twaverObj.selectedApp = _self.imageInfoList[i];
                            break;
                        }
                    }
                    _self.getInfoByVersion(_self.twaverObj.selectedApp.version[0],_self.twaverObj.selectedApp)
                },

                getInfoByVersion: function (version, item) {
                    var _self = this;
                    var metadata = item.metadata[version];
                    _self.selected.version = version;
                    _self.selected.limits = {
                        cpu: metadata.limits.cpu,
                        memory: metadata.limits.memory.slice(0, metadata.limits.memory.length - 2),
                        memoryUnit: metadata.limits.memory.slice(metadata.limits.memory.length - 2, metadata.limits.memory.length)
                    };
                    _self.selected.requests = {
                        cpu: metadata.requests.cpu,
                        memory: metadata.requests.memory.slice(0, metadata.requests.memory.length - 2),
                        memoryUnit: metadata.requests.memory.slice(metadata.requests.memory.length - 2, metadata.requests.memory.length)
                    };
                    _self.selected.env = metadata.env;
                    _self.selected.ports = metadata.ports;
                },

                changeVersion: function(event){
                    var _self = this;
                    var version = $(event.target).val();
                    _self.getInfoByVersion(version, _self.twaverObj.selectedApp);
                }
            }
        });
    }
});
