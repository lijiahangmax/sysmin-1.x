var layer = layui.layer,
    table = layui.table,
    element = layui.element;

// 进程id
var _pid = window.location.href.split("pid=")[1];

$(function () {
    new _init();
})

/**
 * 初始化
 * @private
 */
var _init = function () {
    $("#processinfo").text("PID: " + _pid)
    _view.range(60000);
}

/**
 * echart可视化
 * @type {Object}
 * @private
 */
var _view = new Object();

/**
 * table可视化
 * @type {Object}
 * @private
 */
var _table = new Object();

/**
 * 轮询参数 用于关闭
 */
var _poll;

/**
 * 轮询渲染echarts
 * @param timer 默认60000 一分钟
 */
_view.range = function (timer) {
    _view.classloader();
    _poll = window.setInterval(function () {
            layer.msg("range")
            _view.classloader();
        }, timer
    )
}

element.on('collapse(tableBox)', function (data) {
    // console.log(data.show);
});

/**
 * 停止渲染
 */
_view.shopRange = function () {
    window.clearInterval(_poll)
}

/**
 * 渲染classloader Echarts
 */
_view.classloader = function () {
    var classloader = echarts.init(document.getElementById('classloadermain'));
    var classloadertime = echarts.init(document.getElementById('classloadertimemain'));
    var classbyte = echarts.init(document.getElementById('classbytemain'));
    var _caches = cache.getCache("classloader", "classloader" + _pid);
    var _xdata = [],
        _sdata_1_1 = [],
        _sdata_1_2 = [],
        _sdata_2_1 = [],
        _sdata_3_1 = [],
        _sdata_3_2 = [];
    $.each(_caches, function (index, data) {
        var _data = eval('(' + data + ')')
        _xdata.push(_data.date)
        _sdata_1_1.push(_data.loaded)
        _sdata_1_2.push(_data.unloaded)
        _sdata_2_1.push(_data.time)
        _sdata_3_1.push(_data.loadedBytes)
        _sdata_3_2.push(_data.unloadedBytes)
    })

    var classloaderoption = {
        title: {
            text: 'ClassLoader'
        },
        tooltip: {
            trigger: 'axis'
        },
        legend: {
            data: ['loaded', 'unloaded']
        },
        grid: {
            left: '3%',
            right: '4%',
            bottom: '3%',
            containLabel: true
        },
        toolbox: {
            feature: {
                saveAsImage: {}
            }
        },
        xAxis: {
            type: 'category',
            boundaryGap: false,
            data: _xdata
        },
        yAxis: {
            type: 'value'
        },
        series: [
            {
                name: 'loaded',
                type: 'line',
                stack: 'loaded总量',
                data: _sdata_1_1
            },
            {
                name: 'unloaded',
                type: 'line',
                stack: 'unloaded总量',
                data: _sdata_1_2
            },
        ]
    };
    var classloadertimeoption = {
        title: {
            text: 'ClassLoaderTime'
        },
        tooltip: {
            trigger: 'axis'
        },
        legend: {
            data: ['loadertime']
        },
        grid: {
            left: '3%',
            right: '4%',
            bottom: '3%',
            containLabel: true
        },
        toolbox: {
            feature: {
                saveAsImage: {}
            }
        },
        xAxis: {
            type: 'category',
            boundaryGap: false,
            data: _xdata
        },
        yAxis: {
            type: 'value'
        },
        series: [
            {
                name: 'loadertime',
                type: 'line',
                stack: '装载和卸载类所花费的时间',
                data: _sdata_2_1
            }
        ]
    };
    var classbyteoption = {
        title: {
            text: 'ClassLoaderBytes'
        },
        tooltip: {
            trigger: 'axis'
        },
        legend: {
            data: ['loadedBytes', 'unloadedBytes']
        },
        grid: {
            left: '3%',
            right: '4%',
            bottom: '3%',
            containLabel: true
        },
        toolbox: {
            feature: {
                saveAsImage: {}
            }
        },
        xAxis: {
            type: 'category',
            boundaryGap: false,
            data: _xdata
        },
        yAxis: {
            type: 'value'
        },
        series: [
            {
                name: 'loadedBytes',
                type: 'line',
                stack: '装载类占用空间大小',
                data: _sdata_3_1
            },
            {
                name: 'unloadedBytes',
                type: 'line',
                stack: '卸载类占用空间大小',
                data: _sdata_3_2
            },
        ]
    };

    classloader.setOption(classloaderoption);
    classloadertime.setOption(classloadertimeoption);
    classbyte.setOption(classbyteoption);
}

/**
 * 渲染classloader Table
 */
_table.classloader = function () {
    table.render({
        elem: '#classTable'
        , cols: [[
            {field: 'loaded', title: 'loaded', sort: true}
            , {field: 'loadedBytes', title: 'loadedBytes', sort: true}
            , {field: 'unloaded', title: 'unloaded', sort: true}
            , {field: 'unloadedBytes', title: 'unloadedBytes', sort: true}
            , {field: 'time', title: 'time', sort: true}
            , {field: 'date', title: 'date', sort: true}
        ]]
        , data: util.arrayToJSON(util.getArrayData(cache.getCache("classloader", "classloader" + _pid), 10))
        , width: $("#tableBox").css("width").substring(0, $("#tableBox").css("width").length - 2) - 50
        , limit: 10
    });
}