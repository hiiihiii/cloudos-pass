"use strict";
define([
    'jquery',
    "vue",
    "echarts",
    "common-module"
],function ($, Vue, echarts, common_module) {
    if($("#overview")[0]){
        var adminhomepage = new Vue({
            el: "#overview",
            data: {
                logs: [],
                allAppIns:[], // 应用实例
                allApps:[],   // 所有应用，包括镜像和模板
                runningApps: [],
                stopApps:[],
                unknownApps:[],
                images:[],
                templates:[],
                hotImage: true,
                hotTemplate: true,
                hotestApp: '',
                up: [],
                down: [],
                userNum:0,
                nodeNum:0,
                imageNumAll: 0,
                templateNumAll: 0,
                imageNumPrivate: 0,
                imageNumPublic: 0,
                templateNumPrivate:0,
                templateNumPublic:0
            },
            mounted: function () {
                var _self = this;
                $(".loading").css("display", "block");
                _self.getUsers();
                _self.getK8sNodes();
                _self.allAppIns = _self.getApplications();
                _self.classifyAppIns();
                _self.initEcharts();
                _self.getLogs();
                _self.getImageData('public');
                _self.getImageData('private');
                _self.getTemplateData('public');
                _self.getTemplateData('private');
                _self.setAllApps();
                _self.setPercent();
                setTimeout(function () {
                    $(".loading").css("display", "none");
                }, 1000);
            },

            methods: {
                toggleHotImage: function (event) {
                    var _self = this;
                    _self.hotestApp = '';
                    var div = $(event.target).parent('div.hot-title')|| $(event.target);
                    var img = $(div).find("img")[0];
                    var span = $(div).find('span')[0];
                    var temp = [];
                    if(_self.hotImage) {
                        _self.hotImage = false;
                        $(img).attr("src", "../images/docker-unselected.png");
                        $(span).css('color', "#bfbfbf");
                        for(var i = 0; i < _self.allApps.length;i++) {
                            if(_self.allApps[i].appType != 'docker'){
                                temp.push(_self.allApps[i]);
                            }
                        }
                        _self.allApps = temp;
                    } else {
                        _self.hotImage = true;
                        $(img).attr("src", "../images/docker-selected.png");
                        $(span).css('color', "#ec670b");
                        for(var i = 0; i < _self.images.length; i++) {
                            _self.allApps.push(_self.images[i]);
                        }
                    }
                    _self.allApps = _self.sort(_self.allApps);
                    if(_self.allApps.length > 6){
                        _self.allApps = _self.allApps.slice(0,6);
                    }
                    // 设置热门应用
                    if(_self.allApps.length > 0) {
                        _self.hotestApp = _self.allApps[0];
                    }
                    if(_self.allApps.length > 3) {
                        _self.up = _self.allApps.slice(0,3);
                        _self.down = _self.allApps.slice(3);
                    } else {
                        _self.up = _self.allApps;
                        _self.down = [];
                    }
                },
                toggleHotTemplate: function (event) {
                    var _self = this;
                    _self.hotestApp = '';
                    var div = $(event.target).parent('div.hot-title')|| $(event.target);
                    var img = $(div).find('img')[0];
                    var span = $(div).find('span')[0];
                    var temp = [];
                    if(_self.hotTemplate) {
                        _self.hotTemplate = false;
                        $(img).attr("src", "../images/template-unselected.png");
                        $(span).css("color", '#bfbfbf');
                        for(var i = 0; i < _self.allApps.length;i++) {
                            if(_self.allApps[i].appType == 'docker'){
                                temp.push(_self.allApps[i]);
                            }
                        }
                        _self.allApps = temp;
                    } else {
                        _self.hotTemplate = true;
                        $(img).attr("src", "../images/template-selected.png");
                        $(span).css('color', '#ec670b');
                        for(var i = 0; i < _self.templates.length; i++) {
                            _self.allApps.push(_self.templates[i]);
                        }
                    }
                    _self.allApps = _self.sort(_self.allApps);
                    if(_self.allApps.length > 6){
                        _self.allApps = _self.allApps.slice(0,6);
                    }
                    //设置热门应用
                    if(_self.allApps.length > 0) {
                        _self.hotestApp = _self.allApps[0];
                    }
                    if(_self.allApps.length > 3) {
                        _self.up = _self.allApps.slice(0,3);
                        _self.down = _self.allApps.slice(3);
                    } else {
                        _self.up = _self.allApps;
                        _self.down = [];
                    }
                },
                convertData: function(dataArray, type){
                    if(type === 'image'){
                        for(var i = 0; i < dataArray.length; i++){
                            dataArray[i].logo_url = "ftp://docker:dockerfile@" + dataArray[i].logo_url;
                            dataArray[i].appType = "docker";
                        }
                    } else {
                        for(var i = 0; i < dataArray.length; i++) {
                            dataArray[i].temp_logo_url = "ftp://docker:dockerfile@" + dataArray[i].logo_url;
                            dataArray[i].appType = 'template';
                        }
                    }
                    return dataArray;
                },
                //设置默认图标
                setSrc: function (event) {
                    var $this = $(event.target);
                    $this.attr("src", "../images/app-default.png");
                },
                //设置公私仓库的百分比
                setPercent: function () {
                    var _self = this;
                    var publicImagePer = "0";
                    var publicTemplatePer = "0";
                    if(_self.imageNumAll != 0) {
                        publicImagePer = (_self.imageNumPublic / _self.imageNumAll * 100) + "%";
                    }
                    if(_self.templateNumAll !=0) {
                        publicTemplatePer = (_self.templateNumPublic / _self.templateNumAll * 100) + "%";
                    }
                    var imageSpan = $("#image-statistics").find('.type-classify span');
                    var templateSpan = $("#template-statistics").find('.type-classify span');
                    $(imageSpan).css({
                        'width': publicImagePer,
                        'background':'#8ec035'
                    });
                    $(templateSpan).css({
                        'width': publicTemplatePer,
                        'background':'#17abe3'
                    });
                },
                setAllApps: function () {
                    var _self = this;
                    _self.allApps = _self.images;
                    _self.hotestApp = '';
                    for(var i = 0; i< _self.templates.length; i++) {
                        _self.allApps.push(_self.templates[i]);
                    }
                    console.log("所有应用");
                    console.log(_self.allApps);
                    _self.allApps = _self.sort(_self.allApps);
                    if(_self.allApps.length > 6){
                        _self.allApps = _self.allApps.slice(0,6);
                    }
                    //设置最热门的应用
                    if(_self.allApps.length > 0) {
                        _self.hotestApp = _self.allApps[0];
                    }
                    if(_self.allApps.length > 3) {
                        _self.up = _self.allApps.slice(0,3);
                        _self.down = _self.allApps.slice(3);
                    } else {
                        _self.up = _self.allApps;
                        _self.down = [];
                    }
                },
                initEcharts: function () {
                    var echartsObj = echarts.init(document.getElementById("app-statistics"));
                    var option = {
                        tooltip: { // 鼠标悬浮时的提示信息
                            trigger: 'item', // 触发类型
                            formatter: "{b} : {c} ({d}%)"
                        },
                        legend: {  // 图例
                            orient : 'vertical', // 图例方向
                            x : 'right',         // 水平放置位置
                            data: ["运行","停止","异常"]
                        },
                        graphic: {
                            type: 'text',
                            top: 'center',
                            left: 'center',
                            style: {
                                text: '7',
                                fill: '#000',           // 填充色。
                                fontSize: 20,           // 字体大小
                                fontWeight: 'bold'
                            }
                        },
                        series:[{  //驱动图表生成的数据内容数组
                            name: "", //系列的名称
                            type: "pie", //图表类型
                            radius : ['35%', '60%'], //半径
                            label:{
                                show: false
                            },
                            emphasis : { //强调样式（悬浮时样式）
                                label : {
                                    show : true,
                                    position : 'center',
                                    formatter: "{c}",
                                    textStyle : {
                                        fontSize : '20',
                                        fontWeight : 'bold'
                                    }
                                }
                            },
                            data:[{
                                value: 2, name: "运行"
                            }, {
                                value: 2, name: "停止"
                            }, {
                                value: 3, name: "异常"
                            }]
                        }]
                    };
                    echartsObj.setOption(option);
                },
                getLogs: function () {
                    var _self = this;
                    $.ajax({
                        type:"get",
                        url:"../userlog/info",
                        dataType: "json",
                        async:false,
                        success: function (result) {
                            if(result.code =="success") {
                                var logsCount = result.data.length;
                                if(logsCount > 10) {
                                    _self.logs = result.data.slice(logsCount-10, logsCount);
                                } else {
                                    _self.logs = result.data;
                                }
                                console.log(_self.logs);
                            } else {
                                common_module.notify("[日志]","获取日志数据失败", "danger");
                            }
                        },
                        error: function () {
                            common_module.notify("[日志]","获取日志数据失败", "danger");
                        }
                    })
                },
                getImageData: function (repoType) {
                    var _self = this;
                    $.ajax({
                        url: "../appcenter/imageinfo",
                        type: "get",
                        data: {
                            repoType: repoType
                        },
                        dataType: "json",
                        async: false,
                        success: function (data) {
                            if(data.code=='success') {
                                var count = data.data.length;
                                if(repoType=='public') {
                                    _self.imageNumPublic = count;
                                } else {
                                    _self.imageNumPrivate = count;
                                }
                                _self.imageNumAll += count;
                                for(var i = 0 ; i < count; i++) {
                                    _self.images.push(data.data[i]);
                                }
                                _self.images = _self.convertData(_self.images, 'image');
                            } else {
                                common_module.notify("[应用中心]","获取镜像数据失败", "danger");
                            }
                        },
                        error: function () {
                            common_module.notify("[应用中心]","获取镜像数据失败", "danger");
                        }
                    })
                },
                getTemplateData: function (repoType) {
                    var _self = this;
                    $.ajax({
                        url: "../appcenter/templateinfo",
                        type: 'get',
                        dataType: 'json',
                        data: {
                            repoType: repoType
                        },
                        async: false,
                        success: function (data) {
                            if(data.code === "success"){
                                var count = data.data.length;
                                if(repoType == 'public') {
                                    _self.templateNumPublic = count;
                                } else {
                                    _self.templateNumPrivate = count;
                                }
                                for(var i = 0; i < count; i++) {
                                    _self.templates.push(data.data[i]);
                                }
                                _self.templateNumAll += count;
                                _self.templates = _self.convertData(_self.templates, 'template');
                                console.log(_self.templates);
                            } else {
                                common_module.notify('[应用中心]', '获取模板信息失败', 'fail');
                            }
                        },
                        error: function () {
                            common_module.notify('[应用中心]', '获取模板信息失败', 'fail');
                        }
                    });
                },
                getApplications: function () {
                    var _self = this;
                    var applications = [];
                    $.ajax({
                        url: "../application/info",
                        type: "get",
                        dataType: "json",
                        success: function (result) {
                            if(result.code == "success") {
                                applications = result.data;
                                console.log(result.data);
                            } else {
                                common_module.notify("[应用实例]","获取应用数据失败", "danger");
                            }
                        },
                        error: function () {
                            common_module.notify("[应用实例]","获取应用数据失败", "danger");
                        }
                    });
                    return applications;
                },
                getUsers: function() {
                    var _self = this;
                    $.ajax({
                        url: "../user/info",
                        type: "get",
                        dataType: "json",
                        success: function (result) {
                            if(result.code == "success") {
                                _self.userNum = result.data.length;
                                console.log("用户个数"+ _self.userNum);
                            } else {
                                common_module.notify("[用户]","获取用户数据失败", "danger");
                            }
                        },
                        error: function () {
                            common_module.notify("[用户]","获取用户数据失败", "danger");
                        }
                    });
                },
                getK8sNodes: function () {
                    var _self = this;
                    $.ajax({
                        url: '../kubernetes/nodesinfo',
                        type: 'get',
                        dataType:"json",
                        success:function (result) {
                            if(result.code=="success") {
                                _self.nodeNum = result.data.length;
                                console.log('节点个数'+_self.nodeNum);
                            } else {
                                common_module.notify("[Kubernetes]","获取节点数据失败", "danger");
                            }
                        },
                        error:function () {
                            common_module.notify("[Kubernetes]","获取节点数据失败", "danger");
                        }
                    })
                },
                //冒泡排序
                sort: function (appArray) {
                    var i = appArray.length, j;
                    var tempExchangVal;
                    while (i > 0) {
                        for (j = 0; j < i - 1; j++) {
                            var jAfter_deployCount = parseInt(appArray[j+1].deploycount);
                            var j_deployCount = parseInt(appArray[j].deploycount);
                            if (j_deployCount > jAfter_deployCount) {
                                tempExchangVal = appArray[j];
                                appArray[j] = appArray[j + 1];
                                appArray[j + 1] = tempExchangVal;
                            }
                        }
                        i--;
                    }
                    return appArray;
                },
                classifyAppIns: function () {
                    var _self = this;
                    _self.runningApps = _self.stopApps = _self.unknownApps = [];
                    for(var i = 0 ; i < _self.allAppIns.length; i++) {
                        var temp = _self.allAppIns[i];
                        switch (temp.status) {
                            case "运行中":
                                _self.runningApps.push(temp);
                                break;
                            case "停止":
                                _self.stopApps.push(temp);
                                break;
                            case "异常":
                                _self.unknownApps.push(temp);
                                break;
                        }
                    }
                },
                classifyApp: function (array, Type) {

                }
            }
        });
    }
});