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
    'twaver',
    "bootstrapSwitch",
    "select2"
], function ($, Vue, bootstrap, jquery_validate, validate_extend, common_module, Twaver, bootstrapSwitch, select2) {
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
                    selectedImage: null,  //记录画布中被选中的节点，格式与imageInfoList[i]一致
                    fromNode: null,
                    toNode: null
                },
                selectedImages:[],    //画布中的镜像信息，用于最后统计信息
                versions: [],         //用于最后统计信息，格式为{appName:'', version:''}
                selected: {           //用于在页面中绑定数据
                    appName: '',
                    version: '',
                    appTag: '',
                    description: '',
                    volume: '',
                    cmd: '',
                    cmdParams: [],
                    limits: {},
                    requests: {},
                    env: [],
                    ports: []
                },
                logoFileName: '',
                ports:[]
            },
            mounted: function () {
                var _self = this;
                _self.getImages();
                _self.init();
            },
            methods: {

                //打开填写模板基本信息的窗口
                showTemplateDialog: function () {
                    //是否发布开关按钮初始化
                    $("#add_template_form input[type='checkbox']").bootstrapSwitch({
                        onText: '',
                        offText: '',
                        onSwitchChange: function (event, state) {
                            if(state){

                            } else {

                            }
                        }
                    });
                    $("#add_template").modal({backdrop: 'static', keyboard: false});
                },

                //进入连线模式并设置创建连线处理程序
                setCreateLinkMode: function () {
                    var _self = this;
                    _self.twaverObj.isLinkMode = true;
                    //进入连线模式并设置创建连线处理程序
                    _self.twaverObj.network.setCreateLinkInteractions(function (from, to) {
                        //相同起始点和终点之间不允许有连线
                        if(from === to) {
                            common_module.notify("[增加模板]", "相同起始点和终点之间不允许有连线", "warning");
                            return;
                        }
                        //判断from和to之间是否已存在连线，需要保证from和to之间只有一条连线
                        var links = twaver.Util.getSharedLinks(from,to);
                        if(!links || links.size()==0) {
                            _self.twaverObj.fromNode = from;
                            _self.twaverObj.toNode = to;
                            //判断如果加入连线后 图中有没有环
                            var hasloop = _self.hasLoop(from, to);
                            if(hasloop) {
                                common_module.notify("[增加模板]", "添加连线后可能存在环", "warning");
                            } else {
                                _self.showPortListDialog(to);
                            }
                        } else {
                            common_module.notify("[增加模板]", "镜像之间已存在连线", "warning");
                        }
                    });
                },

                //关闭连线模式，进入默认模式
                closeCreateLinkMode: function () {
                    var _self = this;
                    _self.twaverObj.isLinkMode = false;
                    _self.twaverObj.network.setDefaultInteractions(false);
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
                        imageArray[i].temp_logo_url = "ftp://docker:dockerfile@" + imageArray[i].logo_url;
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
                    dragItem.temp_logo_url = $(event.target).find("img").attr("src");
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
                    //给topcanvas注册dragover、drop事件
                    var rootCanvas = network.getRootCanvas();
                    var topCanvas = network.getTopCanvas();
                    $(topCanvas).on("dragover", function(event){
                        //设置canvas允许放在拖动的元素
                        event.preventDefault();
                    });
                    $(topCanvas).on("drop", function (event) {
                        event.preventDefault();
                        var dragItem = JSON.parse(event.originalEvent.dataTransfer.getData("text"));
                        var data = _self.twaverObj.box.getDatas()._as;
                        for(var i = 0; i < data.length; i++){
                            if(data[i]._name == dragItem.appName){
                                //镜像存在，提示用户，结束
                                common_module.notify("[增加模板]", "该镜像已经存在，请重新选择", "warning");
                                return;
                            }
                        }
                        //镜像不存在，添加镜像
                        var node = new twaver.Node();
                        _self.registerNormalImage(dragItem.temp_logo_url, dragItem.appName, 40, 40);
                        node.setName(dragItem.appName);
                        node.setImage(dragItem.appName);
                        node.setLocation(event.offsetX - 40 / 2, event.offsetY - 40 / 2);
                        _self.twaverObj.box.add(node);
                        for(var i = 0; i < _self.imageInfoList.length; i++){
                            if(dragItem.appName === _self.imageInfoList[i].appName){
                                _self.selectedImages.push(_self.imageInfoList[i]);
                                    _self.versions.push({appName: dragItem.appName, version: dragItem.version[0]});
                                break;
                            }
                        }
                        //当前展示拖动到画布中镜像信息
                        _self.showSelectedImageInfo(dragItem.appName);
                    });
                    //画布画网格
                    network.paintBottom = _self.drawGrid;
                    box.getSelectionModel().addSelectionChangeListener(_self.nodeSelectionChangeHandler);
                    _self.popupMenuInit();
                },

                //右键删除
                popupMenuInit: function(){
                    var _self = this;
                    var popupmenu = new twaver.controls.PopupMenu(_self.twaverObj.network);
                    popupmenu.setMenuItems([
                        {'label': "删除"},
                        {'label': "编辑"}
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
                                _self.twaverObj.box.selectedImage = null;
                            }
                        }
                        //todo 连线编辑功能
                        if(menuItem.label == '编辑'){

                        }
                    };
                },

                //画网格
                drawGrid: function (ctx, dirtyRect){
                    var _self = this;
                    var rootCanvas = _self.twaverObj.network.getRootCanvas();
                    ctx.fillStyle = '#fff';
                    ctx.fillRect(0,0,rootCanvas.width,rootCanvas.height);
                    ctx.lineWidth = 0.2;
                    ctx.strokeStyle = '#aaa';
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
                    // 保存当前节点的信息
                    if(_self.twaverObj.selectedImage != null){
                        for(var i = 0; i < _self.selectedImages.length; i++){
                            if(_self.twaverObj.selectedImage.appName === _self.selectedImages[i].appName){
                                _self.setInfoByVersion(i, _self.selected.version);
                                _self.setVersionByName(_self.selectedImages[i].appName);
                            }
                        }
                    }
                    // 展示选中节点的信息
                    if(last != null){
                        var imageName = last.getName();
                        _self.showSelectedImageInfo(imageName);
                    }
                },

                //展示端口模态框
                showPortListDialog: function (toNode) {
                    var _self = this;
                    var imageName = toNode.getName();
                    var version = "";
                    var ports = [];
                    for(var i = 0; i < _self.versions.length; i++) {
                        if(_self.versions[i].appName == imageName) {
                            version = _self.versions[i].version;
                                break;
                        }
                    }
                    for(var j = 0; j < _self.imageInfoList.length; j++) {
                        if(_self.imageInfoList[j].appName == imageName) {
                            ports = _self.imageInfoList[j].metadata[version]["ports"];
                            break;
                        }
                    }
                    _self.ports = ports;
                    //初始化select2
                    $("#portList_dialog select[name='portList']").select2();
                    $("#portList_dialog").modal({backdrop: 'static', keyboard: false});
                },

                //确认端口，关闭端口选择框
                confirmPorts: function () {
                    var _self = this;
                    debugger
                    var portNamesArray = $("#portList_dialog select[name='portList']").val();
                    if($("#portSelectForm").valid()) {
                        _self.createLink(_self.twaverObj.fromNode, _self.twaverObj.toNode, portNamesArray.join(","));
                        $("#portList_dialog").modal('hide');
                    }
                },
                //创建连线
                createLink: function (from, to, linName) {
                    var _self = this;
                    var link = new twaver.Link(from, to);
                    link.setName(linName);
                    link.setStyle('arrow.to', true);
                    link.setStyle('arrow.to.shape', 'arrow.short');
                    link.setStyle('arrow.to.color', '#ec670b');
                    link.setStyle("link.type", "flexional");
                    link.setStyle("link.width", "2");
                    link.setStyle("link.color", "#ec670b");
                    _self.twaverObj.box.add(link);
                },

                //显示画布中选中的应用的信息
                showSelectedImageInfo: function (imageName) {
                    var _self = this;
                    for(var i = 0; i < _self.selectedImages.length; i++){
                        if(_self.selectedImages[i].appName == imageName){
                            _self.twaverObj.selectedImage = _self.selectedImages[i];
                            break;
                        }
                    }
                    //加载之前最后展示过的版本，如果从未展示过就展示第一个版本的
                    var version = _self.twaverObj.selectedImage.version[0];
                    for(var i = 0; i < _self.versions.length; i++ ){
                        if(_self.versions[i].appName == imageName){
                            version = _self.versions[i].version;
                            break;
                        }
                    }
                    _self.getInfoByVersion(version, _self.twaverObj.selectedImage)
                },

                //动态改变镜像信息
                /**
                 *
                 * @param event
                 * @param imageName
                 * @param k1：metadata具体版本中的key
                 * @param k2：数组的index
                 * @param k3：数组每项的key
                 */
                saveImageInfo:function (event, imageName, k1, k2, k3) {
                    var _self = this;
                    var value = $(event.target).val();
                    var version = _self.twaverObj.selectedImage.version;
                    for(var i = 0; i < _self.selectedImages.length; i++) {
                        if(_self.selectedImages[i].appName == imageName) {
                            console.log(_self.selectedImages[i]);
                            //5个参数
                            if(k3) {
                                _self.selectedImages[i]['metadata'][version][k1][k2][k3] = value;
                            }
                            //4个参数
                            if(k3 == undefined && k2) {
                                if(k2 == 'memory') {
                                    var input = $(event.target).parent("div.form-group").find("input");
                                    var select = $(event.target).parent("div.form-group").find("select");
                                    value = $(input[0]).val() + $(select[0]).val();
                                }
                                _self.selectedImages[i]['metadata'][version][k1][k2] = value;
                            }
                            //3个参数
                            if(k3 == undefined && k2 == undefined && k1) {
                                _self.selectedImages[i]['metadata'][version][k1] = value;
                            }
                            break;
                        }
                    }
                },

                //记录最后展示的版本
                setVersionByName: function (appName) {
                    var _self = this;
                    for(var i = 0; i < _self.versions.length; i++){
                        if(_self.versions[i].appName == appName){
                            _self.versions[i].version = _self.selected.version;
                            return;
                        }
                    }
                    _self.versions.push({appName: appName, version: _self.selected.version});
                },

                //根据版本设置选中的镜像信息
                setInfoByVersion: function (index, version) {
                    var _self = this;
                    _self.selectedImages[index].metadata[version].requests = {
                        cpu: _self.selected.requests.cpu,
                        memory: _self.selected.requests.memory + _self.selected.requests.memoryUnit
                    };
                    _self.selectedImages[index].metadata[version].limits = {
                        cpu: _self.selected.limits.cpu,
                        memory: _self.selected.limits.memory + _self.selected.limits.memoryUnit
                    };
                    _self.selectedImages[index].metadata[version].volume = _self.selected.volume;
                    _self.selectedImages[index].metadata[version].cmd = _self.selected.cmd;
                    _self.selectedImages[index].metadata[version].cmdParams = _self.selected.cmdParams;
                    _self.selectedImages[index].metadata[version].env = _self.selected.env;
                    _self.selectedImages[index].metadata[version].ports = _self.selected.ports;
                },

                getInfoByVersion: function (version, item) {
                    var _self = this;
                    var metadata = item.metadata[version];
                    _self.selected.appName = item.appName;
                    _self.selected.version = version;
                    _self.selected.appTag = item.appTag;
                    _self.selected.description = item.description;
                    _self.selected.volume = metadata.volume;
                    _self.selected.cmd = metadata.cmd;
                    _self.selected.cmdParams = metadata.cmdParams;
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
                    _self.getInfoByVersion(version, _self.twaverObj.selectedImage);
                },

                //设置文件名
                fileChange: function(event){
                    var _self = this;
                    var logoFile = document.getElementById("logo");
                    if(logoFile.files[0]){
                        _self.logoFileName = logoFile.files[0].name;
                    }
                },

                submitAdd: function(){
                    var _self = this;
                    var formData = new FormData();
                    formData.append("templateName", $("#add_template_form input[name='templateName']").val());
                    formData.append("logoFile", $("#add_template_form #logo")[0].files[0]);
                    formData.append("description", $("#add_template_form textarea[name='description']").val());
                    var isPublish = $('#add_template_form #isPublish input').bootstrapSwitch('state');
                    if(isPublish){
                        formData.append("isPublish", "1");
                    } else {
                        formData.append("isPublish", "0");
                    }
                    var relation = _self.createRelation();
                    formData.append("relation",JSON.stringify(relation));
                    var config = _self.createConfig();
                    formData.append("config", JSON.stringify(config));
                    $(".loading").css("display", 'block');
                    $.ajax({
                        url: "../apporch/template/add",
                        type: "post",
                        data: formData,
                        processData: false,
                        contentType: false,
                        success: function(data){
                            if(data.code=='success') {
                                console.log(data);
                                common_module.notify('[应用编排]','新增应用模板成功','success');
                                window.location.href="../apporch";
                            } else {
                                common_module.notify('[应用编排]','新增应用模板成功','danger');
                                setTimeout( function(){
                                    $('.loading').css('display','none');
                                }, 1000);
                            }
                        },
                        error: function(){
                            common_module.notify('[应用编排]','新增应用模板成功','danger');
                            setTimeout( function(){
                                $('.loading').css('display','none');
                            }, 1000);
                        }
                    });
                },
                
                createRelation: function () {
                    var _self = this;
                    var relation = {};
                    var data = _self.twaverObj.box.getDatas()._as;
                    for(var i = 0; i < data.length; i++){
                        if(data[i] instanceof twaver.Link){
                            var fromName = data[i]._fromNode.getName();
                            var toName = data[i]._toNode.getName();
                            if(relation.hasOwnProperty(fromName)){
                                relation[fromName][toName] = data[i].getName();
                            } else {
                                relation[fromName] = {};
                                relation[fromName][toName] = data[i].getName();
                            }
                        }
                    }
                    return relation;
                },
                createConfig: function () {
                    var _self = this;
                    var config = [];
                    for(var i=0; i< _self.versions.length; i++){
                        var name = _self.versions[i].appName;
                        var version = _self.versions[i].version;
                        var imageInfo = {};
                        for(var j=0; j < _self.selectedImages.length; j++){
                            if(_self.selectedImages[j].appName === name){
                                imageInfo = _self.selectedImages[i];
                                break;
                            }
                        }
                        var temp = {
                            appName: name,
                            version: version,
                            logo_url: imageInfo.logo_url,
                            source_url: imageInfo.source_url[version],
                            metadata: imageInfo.metadata[version]
                        };
                        config.push(temp);
                    }
                    return config;
                },

                //判断图中是否有环路
                hasLoop: function (fromNode, toNode) {
                    var result = false;
                    var _self = this;
                    var data = _self.twaverObj.box.getDatas()._as;//data包括了link和node
                    var nodes = _self.calculateNode(data);
                    //辅助对象，使镜像名称与0,1,2,...对应起来
                    var helpObj = {};
                    for(var i = 0; i < nodes.length; i++) {
                        var imageName = nodes[i].getImage();
                        helpObj[imageName] = i;
                    }
                    var graph = [];
                    //初始化图的邻接矩阵为全零
                    for(var i = 0; i< nodes.length; i++) {
                        var temp = [];
                        for(var j = 0; j < nodes.length; j++) {
                            temp.push(0);
                        }
                        graph.push(temp);
                    }
                    //设置已有的边
                    for(var i = 0; i < nodes.length; i++) {
                        debugger
                        var links = nodes[i].getLinks();
                        if(links) {
                            var temp = links._as;
                            for(var j = 0; j < temp.length; j++) {
                                var fromName = temp[j]._fromNode.getName();
                                var toName = temp[j]._toNode.getName();
                                graph = _self.setEdge(graph, helpObj, fromName, toName);
                            }
                        }
                    }
                    //设置预计加入的边
                    _self.setEdge(graph, helpObj, fromNode.getName(), toNode.getName());
                    console.log(graph);
                    return _self.topSort(graph);
                    // return result;
                },

                //计算图中Node的个数
                calculateNode: function (data) {
                    var nodes = [];
                    for(var i = 0 ; i < data.length; i++) {
                        if(!(data[i] instanceof twaver.Link)){
                            nodes.push(data[i]);
                        }
                    }
                    return nodes;
                },

                setEdge: function (graph, helpObj, fromName, toName) {
                    var fromIndex =0, toIndex = 0;
                    for(var key in helpObj) {
                        if(key == fromName) {
                            fromIndex = helpObj[key];
                        }
                        if(key == toName) {
                            toIndex = helpObj[key];
                        }
                    }
                    graph[fromIndex][toIndex] = 1;
                    return graph;
                },

                //使用拓扑排序判断有向图是否有环
                topSort: function (graph) {
                    //入度为0的结点的个数，也就是入队个数
                    var number = 0;
                    //用队列保存拓扑序列
                    var queue = [];
                    //暂时存放拓扑序列
                    var temp = [];
                    //记录每个结点的入度，初始化为0
                    var count = [];
                    for(var i = 0; i < graph.length; i++) {
                        count.push(0);
                    }
                    //计算每个节点的入度, O(N2)
                    for(var i = 0; i < graph.length; i++){
                        for(var j = 0; j < graph.length; j++){
                            if(graph[i][j] == 1){
                                count[j] = count[j] + 1;
                            }
                        }
                    }
                    //遍历图中所有结点，找入度为0的结点删除（放进队列）
                    for(var i = 0; i < graph.length; i++){
                        if(count[i] == 0){
                            queue.push(i);
                        }
                    }
                    //删除这些被删除结点的出边（即对应结点入度减一）
                    while(queue.length!=0){
                        var i = queue[0];
                        temp.push(queue.shift());
                        number += 1;
                        for(var j = 0; j < graph.length; j++){
                            if(graph[i][j] == 1) {
                                count[j] -= 1;
                                //出现了新的入度为0的结点，删除
                                if(count[j] == 0) {
                                    queue.push(j);
                                }
                            }
                        }
                    }

                    if(number != graph.length){
                        return true;//最后存在入度为1的结点，这个有向图是有回路的
                    }else{
                        return false;
                    }
                },

                //在twaver中注册图片
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
            }
        });


        var validator1 = $("#add_template_form").validate({
            rules: {
                templateName: {
                    required: true,
                    notEmpty: true
                },
                messages:{
                }
            }
        });

        var portsValidator = $("#portSelectForm").validate({
            rules: {
                portList: {
                    required: true,
                    portNotEmpty: true
                },
                messages:{

                }
            },
            errorPlacement: function (error, element) {
                error.appendTo(element.parent());
            }
        });

        $("#portList_dialog").on('hidden.bs.modal', function() {
            //清空端口
            template.ports = [];
        });
    }
});
