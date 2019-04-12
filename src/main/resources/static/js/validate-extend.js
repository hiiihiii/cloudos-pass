/**
 * Created by tanli on 2018/7/10 0010.
 */
define([
    'jquery',
    'jquery-validate'
], function ($, jquery_validate) {
    //必填验证
    $.validator.addMethod("required", function (value, element) {
        if(value == ''){
            return false;
        } else {
            return true;
        }
    }, '该项为必填项');

    //非空验证
    $.validator.addMethod("notEmpty", function (value, element) {
        if(value.trim() == ''){
            return false
        } else {
            return true;
        }
    }, '该项不能为空');

    //上传镜像，同名同版本验证
    $.validator.addMethod('imageUnique', function (value, element) {
        debugger
        var result = false;
        var version = value;
        var name = $("#upload_image_form input[name='appName']").val();
        var type = $('#upload_image_form #isPublic input').bootstrapSwitch('state');
        var repoType = 'public';
        if(!type) {
            repoType = 'private';
        }
        $.ajax({
            url: '../appcenter/checkexist',
            type: 'get',
            async: false,
            data: {
                imageName: name,
                version: version,
                repoType: repoType
            },
            dataType: 'json',
            success: function (data) {
                result = data.data;
            },
            error: function () {
                result =  false;
            }
        });
        return result;
    }, '该仓库已存在同名同版本镜像，请重新填写');

    $.validator.addMethod("deployUnique", function (value, element) {
        // return true;
        var result = false;
        $.ajax({
            url: '../appcenter/checkDeployName',
            type: 'get',
            async: false,
            data: {
                deployName: value
            },
            dataType: 'json',
            success: function (data) {
                result = data.data;
            },
            error: function () {
                result = true;
            }
        });
        return result;
    },"已存在相同的部署名称，请重新填写");

    $.validator.addMethod("userNameUnique",function (value, element) {
        var result = false;
        $.ajax({
            url: '../user/checkUserName',
            type: 'get',
            data: {
                username: value
            },
            dataType: 'json',
            async: false,
            success: function (data) {
                result = data.data;
            },
            error: function () {
                result = false;
            }
        });
        return result;
    }, "已存在相同的用户名，请重新填写");

    $.validator.addMethod('sameToPsd', function (value, element) {
        var psdAgain = value;
        var psd = $("#add_user_form input[name='password']").val();
        if(psd === psdAgain){
            return true;
        } else {
            return false;
        }
    }, '确认密码不一致');

    $.validator.addMethod('phone', function (value, element) {
        //前者是手机，后者是固定电话
        if(!(/^1(3|4|5|7|8)\d{9}$/.test(value)) || !/^(\(\d{3,4}\)|\d{3,4}-|\s)?\d{7,14}$/.test(value)){
            return false;
        } else {
            return true;
        }
    }, '联系方式有误');
});
