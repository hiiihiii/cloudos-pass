"use strict";
define([
    'jquery',
    "vue",
    "datatables",
    "bootstrap-notify"
],function ($, Vue, DataTables, BootstrapNotify) {
    if(sessionStorage.href){
        switch (sessionStorage.href){
            case "/adminhomepage":
                $("#overview-menu").addClass("parent-menu-selected");
                $("#first-menu").text("概览");
                $("#second-menu").text("");
                break;
            case "/appcenter":
                $("#service-menu").addClass("parent-menu-selected");
                $("#appstore-menu").addClass("child-menu-selected");
                $("#first-menu").text("服务");
                $("#second-menu").text("应用仓库");
                break;
            case "/role":
                $("#setting-menu").addClass("parent-menu-selected");
                $("#system-role-setting").addClass("child-menu-selected");
                $("#first-menu").text("系统管理");
                $("#second-menu").text("角色");
                break;
            case "/user":
                $("#setting-menu").addClass("parent-menu-selected");
                $("#system-user-setting").addClass("child-menu-selected");
                $("#first-menu").text("系统管理");
                $("#second-menu").text("用户");
                break;
            case "/apporch":
                $("#service-menu").addClass("parent-menu-selected");
                $("#apporch-menu").addClass("child-menu-selected");
                $("#first-menu").text("服务");
                $("#second-menu").text("应用编排");
                break;
            case "/kubernetes":
                $("#system-menu").addClass("parent-menu-selected");
                $("#kubernetes-node").addClass("child-menu-selected");
                $("#first-menu").text("配置");
                $("#second-menu").text("节点");
                break;
            case "/application":
                $("#service-menu").addClass("parent-menu-selected");
                $("#application-menu").addClass("child-menu-selected");
                $("#first-menu").text("服务");
                $("#second-menu").text("应用实例管理");
                break;
            case "/audit":
                $("#audit-menu").addClass("parent-menu-selected");
                $("#log-menu").addClass("child-menu-selected");
                $("#first-menu").text("审计");
                $("#second-menu").text("日志");
                break;
        }
    }
    //头部菜单的事件
    $("body").on("click",".parent-menu", function () {
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
        $(".child-menu a").removeClass("child-menu-selected");
        $(".parent-menu").removeClass("parent-menu-selected");
        $(this).addClass("child-menu-selected");
        $(this).parents(".parent-menu").addClass("parent-menu-selected");
        var id = this.getAttribute("id");
        switch(id){//id是<a>标签的id
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
            case "kubernetes-node":
                sessionStorage.currentMenu = "节点";
                sessionStorage.href = "/kubernetes";
                break;
            case "application-menu":
                sessionStorage.currentMenu = "应用实例";
                sessionStorage.href = "/application";
                break;
            case "log-menu":
                sessionStorage.currentMenu = "日志";
                sessionStorage.href="/audit";
                break;
        }
    });

    //显示提示信息
    $("body").on("focus", "input.cloud-form-control, textarea", function () {

        var parent = $(this).parent();
        var span = $(parent).children(".input-tip");
        if(span[0]) {
            $(span[0]).show();
        }
    });
    $("body").on("mouseover","input[type='file'], input.input-select2", function () {
        var parent = $(this).parents("div.form-group");
        var span = $(parent).children(".input-tip");
        if(span[0]) {
            $(span[0]).show();
        }
    });
    //隐藏提示信息
    $("body").on("blur", "input.cloud-form-control, textarea", function () {
        var parent = $(this).parent();
        var span = $(parent).children("span");
        if(span[0]) {
            $(span[0]).hide();
        }
    });
    $("body").on("mouseleave","input[type='file'], input.input-select2", function () {
        var parent = $(this).parents("div.form-group");
        var span = $(parent).children("span");
        if(span[0]) {
            $(span[0]).hide();
        }
    });
    // 创建表格
    function tables(id, options) {
        var tableObj = $(id)
            .on('page.dt', function () {
                Vue.nextTick(function () {
                    checkAll(id);
                });
            })
            .DataTable({
                searching: false,
                ordering: true,
                order : [1,'asc'],
                columnDefs: [{
                    orderable: false,//禁用排序
                    targets:[0]   //指定的列
                }],
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

    // 创建通知消息
    function notify( title, msg, type){
        var icon = "glyphicon glyphicon-info-sign";
        if(type === "success"){
            icon = "glyphicon glyphicon-ok-sign";
        } else if(type === "warning"){
            icon = "glyphicon glyphicon-warning-sign";
        } else if(type === "danger"){
            icon = "glyphicon glyphicon-remove-sign";
        }
        $.notify({
            title: title || "",
            message: msg || "No Msg",
            icon: icon
        },{
            element: 'body',
            position: "relative",
            type: "warning",
            allow_dismiss: true,
            newest_on_top: true,
            showProgressbar: false,
            placement: {
                from: "bottom",
                align: "center"
            },
            offset: 20,
            spacing: 10,
            z_index: 2000,
            delay: 5000,
            timer: 2000,
            url_target: '_blank',
            template: '<div data-notify="container" class="col-xs-11 col-sm-3 alert alert-{0} cloud-notify" role="alert">' +
            '<button type="button" aria-hidden="true" class="close" data-notify="dismiss">×</button>' +
            '<span data-notify="icon"></span> ' +
            '<span data-notify="title">{1}</span> ' +
            '<span data-notify="message">{2}</span>' +
            '<div class="progress" data-notify="progressbar">' +
            '<div class="progress-bar progress-bar-{0}" role="progressbar" aria-valuenow="0" aria-valuemin="0" aria-valuemax="100" style="width: 0%;"></div>' +
            '</div>' +
            '<a href="{3}" target="{4}" data-notify="url"></a>' +
            '</div>'
        });
    }

    // 设置id为tableid的表格中的checkbox全选中或全部不选中
    function checkAll(tableid) {
        if($(tableid + " thead input[type='checkbox']").prop("checked")){
            $(tableid + " tbody input[type='checkbox']").each(function (i, element) {
                $(element).prop("checked", true);
            });
        } else {
            $(tableid + " tbody input[type='checkbox']").each(function (i, element) {
                $(element).prop("checked", false);
            });
        }
    }

    function checkOne(event) {
        var $tbody = $(event.target).parents('tbody');
        var thead_input = $tbody.parents("table").find("thead input[type='checkbox']")[0];
        var trs = $tbody.children('tr');
        var total = trs.length;
        var checked = 0;
        trs.each(function (index, element) {
            var input = $(element).find("input[type='checkbox']")[0];
            if($(input).prop("checked")){
                checked += 1;
            }
        });
        if(checked == total){
            $(thead_input).prop("checked", true);
        } else {
            $(thead_input).prop("checked", false);
        }
    }
    var commonModule = {
        dataTables: tables,
        notify: notify,
        checkAll: checkAll,
        checkOne: checkOne
    };
    return commonModule;
});