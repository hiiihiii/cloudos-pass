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
                hotTemplate: true
            },
            mounted: function () {
                var _self = this;
                _self.allAppIns = _self.getApplications();
                _self.classifyApp();
                _self.initEcharts();
                _self.getLogs();
                _self.getTemplates();
                _self.getImages();
                _self.setallApps();
            },

            methods: {
                toggleHotImage: function (event) {
                    var _self = this;
                    var $this = $(event.target);
                    var temp = [];
                    if(_self.hotImage) {
                        _self.hotImage = false;
                        $this.attr("src", "../images/docker-unselected.png");
                        for(var i = 0; i < _self.allApps.length;i++) {
                            if(_self.allApps[i].appType != 'docker'){
                                temp.push(_self.allApps[i]);
                            }
                        }
                        _self.allApps = temp;
                    } else {
                        _self.hotImage = true;
                        $this.attr("src", "../images/docker-selected.png");
                        for(var i = 0; i < _self.images.length; i++) {
                            _self.allApps.push(_self.images[i]);
                        }
                    }
                    _self.allApps = _self.sort(_self.allApps);
                    if(_self.allApps.length > 6){
                        _self.allApps = _self.allApps.slice(0,6);
                    }
                },
                toggleHotTemplate: function (event) {
                    var _self = this;
                    var $this = $(event.target);
                    var temp = [];
                    if(_self.hotTemplate) {
                        _self.hotTemplate = false;
                        $this.attr("src", "../images/template-unselected.png");
                        for(var i = 0; i < _self.allApps.length;i++) {
                            if(_self.allApps[i].appType == 'docker'){
                                temp.push(_self.allApps[i]);
                            }
                        }
                        _self.allApps = temp;
                    } else {
                        _self.hotTemplate = true;
                        $this.attr("src", "../images/template-selected.png");
                        for(var i = 0; i < _self.templates.length; i++) {
                            _self.allApps.push(_self.templates[i]);
                        }
                    }
                    _self.allApps = _self.sort(_self.allApps);
                    if(_self.allApps.length > 6){
                        _self.allApps = _self.allApps.slice(0,6);
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

                setSrc: function (event) {
                    var $this = $(event.target);
                    $this.attr("src", "../images/app-default.png");
                },
                setallApps: function () {
                    var _self = this;
                    _self.allApps = _self.images;
                    for(var i = 0; i< _self.templates; i++) {
                        _self.allApps.push(_self.templates[i]);
                    }
                    console.log(_self.allApps);
                    _self.allApps = _self.sort(_self.allApps);
                    if(_self.allApps.length > 6){
                        _self.allApps = _self.allApps.slice(0,6);
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
                                value: 2, name: "异常"
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
                getImages: function () {
                    var _self = this;
                    $.ajax({
                       url:"../adminhomepage/images",
                        type:"get",
                        dataType:"json",
                        async: false,
                        success:function (result) {
                            if(result.code == 'success') {
                                _self.images = _self.sort(result.data);
                                _self.images = _self.convertData(_self.images, 'image')
                                console.log(result.data);
                            } else {

                            }
                        },
                        error: function () {
                            
                        }
                    });
                },
                getTemplates: function () {
                    var _self = this;
                    $.ajax({
                        url:"../adminhomepage/templates",
                        type:"get",
                        dataType:"json",
                        async: false,
                        success:function (result) {
                            if(result.code == 'success') {
                                _self.templates = _self.sort(result.data);
                                _self.templates = _self.convertData(_self.templates, 'template');
                                console.log(result.data);
                            } else {

                            }
                        },
                        error: function () {

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

                classifyApp: function () {
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
                }
            }
        });
    }
});