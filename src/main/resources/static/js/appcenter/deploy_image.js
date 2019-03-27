"use strict";
define([
    'jquery',
    'vue',
    'bootstrap',
    'jquery-validate',
    'validate-extend'
], function ($, Vue, bootstrap, bootstrapSwitch, jquery_validate, validate_extend) {
    if($("#deploy_image_dialog")[0]){
        var deploy_image = new Vue({
            el: '#deploy_image',
            data: {
                infoTag: "baseInfo",
                nextStep: true,
                previousStep: false,
                submitTag: false,
                deployImageObj: '',
                selectedVersion: {
                    version: '',
                    limits: {},
                    requests: {},
                    volume: '',
                    cmd: '',
                    cmdParams: [],
                    env: [],
                    ports: []
                }

            },
            mounted: function () {

            },
            methods: {
                //下一步
                toNext: function () {
                    var _self = this;
                    if(_self.infoTag == 'baseInfo'){
                        _self.infoTag = 'resourceInfo';
                        _self.previousStep = true;
                        _self.nextStep = true;
                    } else if(_self.infoTag == 'resourceInfo'){
                        _self.infoTag = 'configInfo';
                        _self.previousStep = true;
                        _self.nextStep = false;
                    }
                    if(_self.infoTag == 'configInfo'){
                        _self.submitTag = true;
                    } else {
                        _self.submitTag = false;
                    }
                },

                //上一步
                toPrevious: function () {
                    var _self = this;
                    if(_self.infoTag == 'resourceInfo'){
                        _self.infoTag = 'baseInfo';
                        _self.previousStep = false;
                        _self.nextStep = true;
                    } else if(_self.infoTag == 'configInfo'){
                        _self.infoTag = 'resourceInfo';
                        _self.previousStep = true;
                        _self.nextStep = true;
                    }
                    if(_self.infoTag == 'configInfo'){
                        _self.submitTag = true;
                    } else {
                        _self.submitTag = false;
                    }
                },

                //增加命令参数
                addCmdParam: function (cmdParam) {
                    cmdParam = cmdParam || '';
                    var cmdParamStr =
                        '<div class="cmdParam-item">' +
                        '<input class="form-control" type="text" name="cmdParam" value="'+ cmdParam +'" />' +
                        '<span class="delete-cmd-btn"><i class="fa fa-trash-o"></i></span>'+
                        '</div>';
                    var cmdParam_box = $("#deploy_image #cmdParam-box");
                    cmdParam_box.append(cmdParamStr);
                    var cmdParam_items = cmdParam_box.find(".delete-cmd-btn");
                    $(cmdParam_items[cmdParam_items.length-1]).on("click", function () {
                        $(this).parent().remove();
                    });
                },

                //增加环境变量
                addEnv: function (envObj) {
                    envObj = envObj || {name:'', value: ''};
                    var envStr =
                        '<tr>' +
                        '<td><input class="form-control" type="text" value="'+ envObj.name +'" name="envKey" /></td>' +
                        '<td><input class="form-control" type="text" value="' + envObj.value + '" name="envValue" /></td>' +
                        '<td><span class="modal-table-operation"><i class="fa fa-trash-o"></i></span></td>' +
                        '</tr>';
                    var envTbody = $("#deploy_image #env_table tbody");
                    envTbody.append(envStr);
                    var trs = envTbody.find("tr");
                    $(trs[trs.length-1]).on("click", ".modal-table-operation", function () {
                        /*detach方法可以删除绑定的事件，而remove能*/
                        $(this).parents("tr").remove();
                    });
                },

                //增加端口映射
                addPort: function (portObj, canDelete) {
                    portObj = portObj || {portName:'', protocol:'TCP',containerPort:'',port:'',nodePort:''};
                    var portStr = '<tr>' +
                        '<td><input class="form-control" type="text" value="' + portObj.portName + '" name="portName"/></td>' +
                        '<td>' +
                        '<select class="form-control" name="protocol" value="' + portObj.protocol + '">' +
                        '<option value="TCP">TCP</option>' +
                        '<option value="UDP">UDP</option>' +
                        '</select>' +
                        '</td>' +
                        '<td><input class="form-control" type="text" maxlength="5" value="'+portObj.containerPort+'" name="containerPort"/></td>' +
                        '<td><input class="form-control" type="text" maxlength="5" value="'+portObj.port+'" name="port"/></td>' +
                        '<td><input class="form-control" type="text" maxlength="5" value="'+portObj.nodePort+'" name="nodePort"/></td>';
                    if(canDelete){
                        portStr +=
                            '<td><span class="modal-table-operation"><i class="fa fa-trash-o"></i></span></td>' +
                            '</tr>';
                    } else {
                        portStr += '</tr>';
                    }
                    var portTbody = $("#deploy_image #port_table tbody");
                    portTbody.append(portStr);
                    var trs = portTbody.find("tr");
                    $(trs[trs.length-1]).on("click", ".modal-table-operation", function () {
                        /*detach方法不能删除绑定的事件，而remove能*/
                        $(this).parents("tr").remove();
                    });
                },

                //切换版本
                changeRepo: function (event) {
                    var _self = this;
                    var version = $(event.target).val();
                    _self.getImageInfoByVersion(version);
                },

                //根据版本获取镜像信息
                getImageInfoByVersion: function(version){
                    var _self = this;
                    var metadata = _self.deployImageObj.metadata[version];
                    _self.selectedVersion.version = version;
                    _self.selectedVersion.limits = {
                        cpu: metadata.limits.cpu,
                        memory: metadata.limits.memory.slice(0, metadata.limits.memory.length - 2),
                        memoryUnit: metadata.limits.memory.slice(metadata.limits.memory.length - 2, metadata.limits.memory.length)
                    };
                    _self.selectedVersion.requests = {
                        cpu: metadata.requests.cpu,
                        memory: metadata.requests.memory.slice(0, metadata.requests.memory.length - 2),
                        memoryUnit: metadata.requests.memory.slice(metadata.requests.memory.length - 2, metadata.requests.memory.length)
                    };
                    _self.selectedVersion.volume = metadata.volume;
                    _self.selectedVersion.cmd = metadata.cmd;
                    _self.selectedVersion.cmdParams = metadata.cmdParams;
                    _self.selectedVersion.env = metadata.env;
                    _self.selectedVersion.ports = metadata.ports;
                    $("#deploy_image .cmdParam-item").remove();
                    $("#deploy_image #env_table tbody tr").remove();
                    $("#deploy_image #port_table tbody tr").remove();
                    for(var i = 0; i < metadata.cmdParams.length; i++){
                        _self.addCmdParam(metadata.cmdParams[i]);
                    }
                    for(var i = 0; i < metadata.env.length; i++){
                        _self.addEnv(metadata.env[i]);
                    }
                    for(var i = 0; i < metadata.ports.length; i++){
                        if(i === 0){
                            _self.addPort(metadata.ports[i],false);
                        } else {
                            _self.addPort(metadata.ports[i],true);
                        }
                    }
                },

                submitDeployImage: function () {
                    debugger
                    var _self = this;
                    var test = $("#deploy_image_form").valid();
                    if(!test){
                        return;
                    }
                    var formdata = new FormData();
                    var image = JSON.parse(sessionStorage.getItem("deployImage"));
                    //公有字段
                    formdata.append("deploy_name", $("#deploy_image_form input[name='deployName']").val());
                    formdata.append("deploy_type", "docker");
                    formdata.append("description", $("#deploy_image_form textarea[name='description']").val());
                    formdata.append("app_id", image.app_id);
                    formdata = _self.generateMetadata(formdata, image);

                    $.ajax({
                        type: "post",
                        url: "../appcenter/image/deploy",
                        data: formdata,
                        processData: false,
                        contentType: false,
                        dataType: "json",
                        success: function (data) {

                        },
                        error: function () {

                        }
                    });
                },

                generateMetadata: function(formdata, image){
                    var container = {};

                    //resources
                    var resources = {
                        limits: {},
                        requests: {},
                    };
                    resources.limits.cpu = $("#deploy_image_form input[name='maxcpu']").val();
                    resources.limits.memory = $("#deploy_image_form input[name='maxMemory']").val() + $("#deploy_image_form select[name='maxMemoryUnit']").val();
                    resources.requests.cpu = $("#deploy_image_form input[name='mincpu']").val();
                    resources.requests.memory = $("#deploy_image_form input[name='minMemory']").val() + $("#deploy_image_form select[name='minMemoryUnit']").val();

                    //command
                    var command = $("#deploy_image_form input[name='cmd']").val().split(",");

                    //args
                    var args = [];
                    $("#deploy_image_form").find("input[name='cmdParam']").each(function (index, element) {
                        args.push($(element).val());
                    });

                    //env
                    var envs = [];
                    $("#deploy_image_form #env_table tbody").find("tr").each(function (index, element) {
                        var temp = {};
                        temp.name = $($(element).find("input[name='envKey']")[0]).val();
                        temp.value = $($(element).find("input[name='envValue']")[0]).val();
                        envs.push(temp);
                    });

                    //ports
                    var ports = [];
                    $("#deploy_image_form #port_table tbody").find("tr").each(function (index, element) {
                        var port = {};
                        port.name = $($(element).find("input[name='portName']")[0]).val();
                        port.nodePort = $($(element).find("input[name='nodePort']")[0]).val();
                        port.port = $($(element).find("input[name='port']")[0]).val();
                        port.protocol = $($(element).find("select[name='protocol']")[0]).val();
                        port.targetPort = $($(element).find("input[name='containerPort']")[0]).val();
                        ports.push(port);
                    });

                    var version = $("#deploy_image_form select[name='version']").val();

                    container.imageName = image.appName;
                    container.image_source_url = image.source_url[version];
                    container.serviceName = $("#deploy_image_form input[name='serviceName']").val();
                    container.replicas = $("#deploy_image_form input[name='instanceCount']").val();
                    container.workingDir = $("#deploy_image_form input[name='volumeDir']").val()
                    // container.resources = resources;
                    container.limits = resources.limits;
                    container.requests = resources.requests;
                    container.command = command;
                    container.args = args;
                    container.env = envs;
                    container.ports = ports;
                    formdata.append("container", JSON.stringify(container));
                    return formdata;
                }
            }
        });


        var validator = $("#deploy_image_form").validate({
            submitHandler: function(form){
                if($(form).valid()){
                    deploy_image.submitDeployImage();
                }
            },
            ignore: "",
            onkeyup: false,
            rules: {
                deployName: {
                    required: true,
                    notEmpty: true,
                    deployUnique: true
                },
                version:{
                    required: true,
                    notEmpty: true
                },
                mincpu:{
                    required: true,
                    notEmpty: true
                },
                minMemory:{
                    required: true,
                    notEmpty: true
                },
                maxcpu:{
                    required: true,
                    notEmpty: true
                },
                maxMemory:{
                    required: true,
                    notEmpty: true
                },
                serviceName:{
                    required: true,
                    notEmpty: true
                },
                instanceCount:{
                    required: true,
                    notEmpty: true
                },
                portName:{
                    required: true,
                    notEmpty: true
                },
                containerPort:{
                    required: true,
                    notEmpty: true
                },
                port:{
                    required: true,
                    notEmpty: true
                },
                protocol:{
                    required: true,
                    notEmpty: true
                },
                nodePort:{
                    required: true,
                    notEmpty: true
                }
            },
            messages: { },
            errorPlacement: function (error, element) {
                error.appendTo(element.parent());
            }
        });

        $('#deploy_image').on('show.bs.modal', function () {
            console.log("deploy-image-show");
            var imageInfo = JSON.parse(sessionStorage.getItem("deployImage"));
            deploy_image.deployImageObj = imageInfo;
            var version = imageInfo.version[0];
            deploy_image.getImageInfoByVersion(version);
        });
    }
});