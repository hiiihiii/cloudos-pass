"use strict";
define([
    'jquery',
    "vue"
],function ($, Vue) {
    //头部菜单的事件
    $("body").on("click",".parent-menu", function () {
        debugger
        if(!$(this).find(".child-menu").length){
            $(".parent-menu").removeClass("parent-menu-selected");
            $(".child-menu a").removeClass("child-menu-selected");
            $(this).addClass("parent-menu-selected");
            window.location="../adminhomepage/";
        }
    });
    $("body").on("mouseover",".parent-menu",function () {
        console.log(this);
        $(this).find(".child-menu").show();
    });
    $("body").on("mouseleave",".parent-menu",function () {
        $(this).find(".child-menu").hide();
    });
    $("body").on("click",".child-menu a",function () {
        $(".child-menu a").removeClass("child-menu-selected");
        $(".parent-menu").removeClass("parent-menu-selected");
        $(this).addClass("child-menu-selected");
        $(this).parents(".parent-menu").addClass("parent-menu-selected");
        sessionStorage.currentMenu = "";
        sessionStorage.href = "";
    });
});