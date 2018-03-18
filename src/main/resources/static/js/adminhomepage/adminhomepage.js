"use strict";
define([
    'jquery',
    "vue",
    "echarts"
],function ($, Vue, echarts) {
    if($("#overview")[0]){
        var adminhomepage = new Vue({
            el: "#overview",
            data: {},
            mounted: function () {
                var _self = this;
                _self.initEcharts();
            },

            methods: {
                initEcharts: function () {
                    debugger;
                    var echartsObj = echarts.init(document.getElementById("app-statistics"));
                    var option = {
                        tooltip: {
                            trigger: 'item',
                            formatter: "{a} <br/>{b} : {c} ({d}%)"
                        },
                        legend: {
                            orient : 'vertical',
                            x : 'right',
                            data: ["运行","停止","异常"]
                        },
                        series:[{
                            name:"",
                            type:"pie",
                            radius : ['50%', '70%'],
                            itemStyle : {
                                normal : {
                                    label : {
                                        show : false
                                    },
                                    labelLine : {
                                        show : false
                                    }
                                },
                                emphasis : {
                                    label : {
                                        show : true,
                                        position : 'center',
                                        textStyle : {
                                            fontSize : '20',
                                            fontWeight : 'bold'
                                        }
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
                }
            }
        });
    }
});