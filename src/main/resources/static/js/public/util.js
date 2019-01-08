var util = new Object();

/**
 * 动态加载CSS
 * @param href css路径
 */
util.loadCSS = function (href) {
    layui.link(href);
}

/**
 * 获取设备信息
 * @param name 属性 null查询全部
 * @returns {*}
 */
util.getDevice = function (name) {
    if (name == null) {
        return layui.device();
    } else {
        return layui.device(name);
    }
}

/**
 * 动态加载js
 * @param src js路径
 * @param callback 回调方法 function 可以为null
 */
util.loadJS = function (src, callback) {
    if (callback == null) {
        $.getScript(src);
    } else {
        $.getScript(src, callback);
    }
}

/**
 * 将数组数据只保留 几条
 * @param data 数组数据
 * @param size 几条数据
 * @returns {*}
 */
util.getArrayData = function (data, size) {
    if (data.length > size) {
        data.splice(0, data.length - size);
    }
    return data;
}

/**
 * 数组转json数组
 * @param arr jsonString数组
 * @returns {Array}
 */
util.arrayToJSON = function (arr) {
    var tmp = [];
    $.each(arr, function (index, data) {
        tmp.push(eval('(' + data + ')'))
    })
    return tmp;
}