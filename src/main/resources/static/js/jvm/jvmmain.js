var layer = layui.layer,
    element = layui.element;

$(function () {
    new _init();
});

/**
 * 初始化方法
 * @private
 */
var _init = function () {
    _data.getJavaHome();
    _data.getJavaVersion();
    _data.getJavaVendor();
    layer.msg('一定要用OracleJDK <br/>' +
        '使用OpenJDK工具统计工具无法加载');
};

/**
 * 操作数据对象
 * @type {Object}
 * @private
 */
var _data = new Object();

/**
 * 获得javaVersion信息
 */
_data.getJavaVersion = function () {
    $.post("/getjavaversion", {}, function (data) {
        $("#javaversion").append(data);
    });
};

/**
 * 获得javaHome信息
 */
_data.getJavaHome = function () {
    $.post("/getjavahome", {}, function (data) {
        $("#javahome").append(data);
    });
};

/**
 * 获得javaVendor信息
 */
_data.getJavaVendor = function () {
    $.post("/getjavavendor", {}, function (data) {
        $("#javavendor").append(data);
    });
}