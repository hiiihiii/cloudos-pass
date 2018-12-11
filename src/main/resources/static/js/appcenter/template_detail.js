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
                templateDetail: {},
                twaverObj: {
                    network: '',
                    box: '',
                    selectedApp: null
                }
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
                                console.log(_self.templateDetail);
                                common_module.notify('[应用编排]','获取模板详情成功','success');
                                _self.initTwaver();
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
                    for(var i = 0; i < templateInfo.config.length; i++){
                        templateInfo.config[i].temp_logo_url = "ftp://docker:dockerfile@" + templateInfo.config[i].logo_url;
                    }
                    return templateInfo;
                },

                initTwaver: function () {
                    var _self = this;
                    var box = new twaver.ElementBox(); //容器
                    var network = new twaver.vector.Network(box); //页面上的画布
                    document.getElementById("canvas-box").appendChild(network.getView());
                    network.adjustBounds({x:0, y:100, width:1030, height:535});
                    window.onresize = function (e) {
                        network.adjustBounds({x:0, y:100, width:1030, height:535});
                    };
                    _self.twaverObj.network = network;
                    _self.twaverObj.box = box;
                    //画布画网格
                    network.paintBottom = _self.drawGrid;
                    _self.drawTopology();
                    box.getSelectionModel().addSelectionChangeListener(function () {
                        var selectionModel = _self.twaverObj.box.getSelectionModel();
                        var last = selectionModel.getLastData();
                        if(last != null) {
                            for(var i = 0; _self.templateDetail.config.length; i++){
                                if(last.getName() == _self.templateDetail.config[i].appName){
                                    _self.twaverObj.selectedApp = _self.templateDetail.config[i];
                                    break;
                                }
                            }
                        }
                    });
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

                drawTopology: function () {
                    var _self = this;
                    var startX = 200, startY = 100;
                    _self.registerNormalImage('../images/app-default.png', 'default', 40, 40);
                    //画应用node
                    for(var i = 0; i < _self.templateDetail.config.length; i++){
                        var app = _self.templateDetail.config[i];
                        _self.registerNormalImage(app.temp_logo_url, app.appName, 40, 40);
                        var node = new twaver.Node();
                        node.setName(app.appName);
                        //todo 后期要换成真实的图标
                        node.setImage('default');
                        node.setLocation(startX, startY);
                        startX += 150;
                        _self.twaverObj.box.add(node);
                    }
                    //画关系连线
                    for(key1 in _self.templateDetail.relation) {
                        var from, to;
                        from = _self.getNode(key1);
                        for(var key2 in _self.templateDetail.relation[key1]){
                            to = _self.getNode(key2);
                            var link = new twaver.Link(from, to);
                            link.setName(_self.templateDetail.relation[key1][key2]);
                            link.setStyle('arrow.to', true);
                            link.setStyle('arrow.to.shape', 'arrow.short');
                            link.setStyle('arrow.to.color', '#ffffff');
                            link.setStyle("link.type", "flexional");
                            link.setStyle("link.width", "2");
                            link.setStyle("link.color", "#ffffff");
                            _self.twaverObj.box.add(link);
                        }
                    }
                },

                //根据name获取twaver中的node
                getNode: function (name) {
                    var _self = this;
                    var result;
                    var datalist = _self.twaverObj.box._dataList;
                    for(var i = 0; i < datalist._as.length; i++){
                        if(name == datalist._as[i].getName()){
                            result = datalist._as[i];
                            break;
                        }
                    }
                    return result;
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
            }
        });
    }
});