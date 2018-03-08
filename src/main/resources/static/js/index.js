"use strict";
define([
    'jquery',
    "vue"
],function ($,Vue) {
    function index() {
        debugger;
        if($(".index")[0]){
            var indexVue = new Vue({
                el:".index",
                data:{
                    msg:"hello"
                },
                mounted: function () {
                    console.log('test');
                },
                method:{

                }
            });
        }
    }
    return{
        index: index
    };
});