/**
 * Created by Administrator on 2018/3/8 0008.
 */
require.config({
    baseUrl: "../",
    paths: {
        "jquery": "lib/jquery/jquery-3.3.1.min",
        "vue": "lib/vue/vue.min",
        "bootstrap": "lib/bootstrap/js/bootstrap.min",
        "bootstrapSwitch": "lib/bootstrap/js/bootstrap-switch",
        "bootstrap-notify": "lib/bootstrap-notify/bootstrap-notify.min",
        "echarts": "lib/echarts/echarts.min",
        "jquery-validate": "lib/jquery-validation/jquery.validate.min",
        "validate-extend": 'js/validate-extend',
        "select2": "lib/select2/js/select2.min",
        "datatables":"lib/datatables/js/jquery.dataTables.min",
        "twaver": "lib/twaver/twaver",
        "jpages": "lib/jpages/js/jPages",
        "common-module": "js/common-module",
        "adminhomepage": "js/adminhomepage/adminhomepage",
        "appstore": "js/appcenter/appstore",
        "upload_image": "js/appcenter/upload_image",
        "deploy_image": "js/appcenter/deploy_image",
        "deploy_template": "js/appcenter/deploy_template",
        "apporch": "js/appcenter/apporch",
        // "template": "js/appcenter/template",
        "template_new": "js/appcenter/template_new",
        "template_detail": "js/appcenter/template_detail",
        "application": "js/appcenter/application",
        "login": "js/login",
        "role": "js/system/role",
        "user": "js/system/user",
        "userlog": "js/system/userlog",
        'node':'js/kubernetes/node'
    },
    shim:{
        "bootstrap": ["jquery"],
        "bootstrap-notify": ["jquery"],
        "jquery-validate": ['jquery'],
        "datatables": ['jquery'],
        "jpages": ['jquery']
    }
});