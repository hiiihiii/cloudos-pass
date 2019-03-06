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
                logs: []
            },
            mounted: function () {
                var _self = this;
                _self.initEcharts();
                _self.getLogs();
            },

            methods: {
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
                        success: function (result) {
                            if(result.code =="success") {
                                _self.logs = result.data; 
                            } else {
                                common_module.notify("[日志]","获取日志数据成功", "danger");
                            }
                        },
                        error: function () {
                            common_module.notify("[日志]","获取日志数据成功", "danger");
                        }
                    })
                },
                getImages: function () {
                    
                },
                getTemplates: function () {
                    
                }
            }
        });
    }
});