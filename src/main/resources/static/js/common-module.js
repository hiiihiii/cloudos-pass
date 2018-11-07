"use strict";
define([
    'jquery',
    "vue",
    "datatables"
],function ($, Vue, DataTables) {
    debugger;
    if(sessionStorage.href){
        switch (sessionStorage.href){
            case "/adminhomepage":
                $("#overview-menu").addClass("parent-menu-selected");
                break;
            case "/appcenter":
                $("#service-menu").addClass("parent-menu-selected");
                $("#appstore-menu").addClass("child-menu-selected");
                break;
            case "/role":
                $("#setting-menu").addClass("parent-menu-selected");
                $("#system-role-setting").addClass("child-menu-selected");
                break;
            case "/user":
                $("#setting-menu").addClass("parent-menu-selected");
                $("#system-user-setting").addClass("child-menu-selected");
                break;
            case "/apporch":
                $("#service-menu").addClass("parent-menu-selected");
                $("#apporch-menu").addClass("child-menu-selected");
                break;
        }
    }
    //头部菜单的事件
    $("body").on("click",".parent-menu", function () {
        debugger
        // 跳转到概览页
        if(!$(this).find(".child-menu").length){
            $(".parent-menu").removeClass("parent-menu-selected");
            $(".child-menu a").removeClass("child-menu-selected");
            $(this).addClass("parent-menu-selected");
            window.location="../adminhomepage/";
            sessionStorage.currentMenu = "概览";
            sessionStorage.href = "/adminhomepage";
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
        debugger;
        $(".child-menu a").removeClass("child-menu-selected");
        $(".parent-menu").removeClass("parent-menu-selected");
        $(this).addClass("child-menu-selected");
        $(this).parents(".parent-menu").addClass("parent-menu-selected");
        var id = this.getAttribute("id");
        switch(id){
            case "appstore-menu":
                sessionStorage.currentMenu = "应用仓库";
                sessionStorage.href = "/appcenter";
                break;
            case "system-role-setting":
                sessionStorage.currentMenu = "角色";
                sessionStorage.href = "/role";
                break;
            case "system-user-setting":
                sessionStorage.currentMenu = "用户";
                sessionStorage.href = "/user";
                break;
            case "apporch-menu":
                sessionStorage.currentMenu = "应用编排";
                sessionStorage.href = "/apporch";
                break;

        }
    });

    // 创建表格
    function tables(id, options) {
        debugger;
        var tableObj = $(id).DataTable({
            searching: false,
            ordering: true,
            paging: true,
            pageLength: 10,
            pagingType: "full_numbers",
            lengthChange: false,
            language: {
                emptyTable: "暂无数据",
                info: "共_MAX_条记录",
                zeroRecords: "未找到数据",
                paginate: {
                    first: "<<",
                    last: ">>",
                    next: ">",
                    previous: "<"
                }
            }
        });
        return tableObj;
    }
    var commonModule = {
        dataTables: tables
    };
    return commonModule;
});