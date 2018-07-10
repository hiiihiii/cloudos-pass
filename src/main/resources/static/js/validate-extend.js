/**
 * Created by tanli on 2018/7/10 0010.
 */
define([
    'jquery'
], function ($) {
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
});
