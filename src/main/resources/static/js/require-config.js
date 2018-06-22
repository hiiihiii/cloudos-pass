/**
 * Created by Administrator on 2018/3/8 0008.
 */
require.config({
    baseUrl: "../",
    paths: {
        "jquery": "lib/jquery/jquery-3.3.1.min",
        "vue":"lib/vue/vue.min",
        "bootstrap":"lib/bootstrap/js/bootstrap.min",
        "bootstrapSwitch": "lib/bootstrap/js/bootstrap-switch",
        "echarts":"lib/echarts/echarts.min",
        "jquery-validate": "lib/jquery-validation/jquery.validate.min",
        "select2": "/lib/select2/js/select2.min",
        "common-module":"js/common-module",
        "adminhomepage":"js/adminhomepage/adminhomepage",
        "appstore":"js/appcenter/appstore",
        "upload_image": "js/appcenter/upload_image"
    },
    shim:{
        "bootstrap": ["jquery"],
        "jquery-validate": ["jquery"]
    }
});