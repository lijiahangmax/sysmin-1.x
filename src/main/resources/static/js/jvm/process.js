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
 * 参数信息
 * @type {Object}
 * @private
 */
var _help = new Object();

/**
 * echarts轮询参数 用于关闭
 */
var _echartsPoll;

/**
 * 轮询渲染echarts
 * @param timer 默认60000 一分钟
 */
_view.range = function (timer) {
    _view.classloader();
    _view.gc();
    _view.gcutil();
    _view.compiler();
    _view.stack();
    _echartsPoll = window.setInterval(function () {
            // 重置保存线程快照
            _help.threadIndex = 0;
            _view.classloader();
            _view.gc();
            _view.gcutil();
            _view.compiler();
            _view.stack();
        }, timer
    )
}

// element.on('collapse(tableBox)', function (data) {
//     // console.log(data.show);
// });

/**
 * 停止渲染
 */
_view.shopRange = function () {
    window.clearInterval(_echartsPoll)
}

/**
 * 渲染classloader Echarts
 */
_view.classloader = function () {
    var classloader = echarts.init(document.getElementById('classloadermain'));
    var classloadertime = echarts.init(document.getElementById('classloadertimemain'));
    var classbyte = echarts.init(document.getElementById('classbytemain'));
    var _caches = cache.getCache("classloader", "classloader" + _pid);
    var _date = [],
        _loaded = [],
        _unloaded = [],
        _time = [],
        _loadedBytes = [],
        _unloadedBytes = [];
    $.each(_caches, function (index, data) {
        var _data = eval('(' + data + ')')
        _date.push(_data.date)
        _loaded.push(_data.loaded)
        _unloaded.push(_data.unloaded)
        _time.push(_data.time)
        _loadedBytes.push(_data.loadedBytes)
        _unloadedBytes.push(_data.unloadedBytes)
    })

    var classloaderoption = {
        title: {
            text: 'Loader',
            left: '4%'
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
            data: _date
        },
        yAxis: {
            type: 'value'
        },
        series: [
            {
                name: 'loaded',
                type: 'line',
                stack: 'loaded总量',
                data: _loaded
            },
            {
                name: 'unloaded',
                type: 'line',
                stack: 'unloaded总量',
                data: _unloaded
            }
        ]
    };
    var classloadertimeoption = {
        title: {
            text: 'LoaderTime',
            left: '4%'
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
            data: _date
        },
        yAxis: {
            type: 'value'
        },
        series: [
            {
                name: 'loadertime',
                type: 'line',
                stack: '装载和卸载类所花费的时间',
                data: _time
            }
        ]
    };
    var classbyteoption = {
        title: {
            text: 'LoaderBytes',
            left: '4%'
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
            data: _date
        },
        yAxis: {
            type: 'value'
        },
        series: [
            {
                name: 'loadedBytes',
                type: 'line',
                stack: '装载类占用空间大小',
                data: _loadedBytes
            },
            {
                name: 'unloadedBytes',
                type: 'line',
                stack: '卸载类占用空间大小',
                data: _unloadedBytes
            }
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

/**
 * 渲染gc Echarts
 */
_view.gc = function () {
    var gcsc = echarts.init(document.getElementById('gcscmain'));
    var gcsu = echarts.init(document.getElementById('gcsumain'));
    var gce = echarts.init(document.getElementById('gcemain'));
    var gco = echarts.init(document.getElementById('gcomain'));
    var gcm = echarts.init(document.getElementById('gcmmain'));
    var gcccs = echarts.init(document.getElementById('gcccsmain'));
    var gcyfgc = echarts.init(document.getElementById('gcyfgcmain'));
    var gcyfgct = echarts.init(document.getElementById('gcyfgctmain'));
    var gcgct = echarts.init(document.getElementById('gcgctmain'));

    var _caches = cache.getCache("gc", "gc" + _pid);
    var _date = [],
        _s0c = [],
        _s1c = [],
        _s0u = [],
        _s1u = [],
        _ec = [],
        _eu = [],
        _oc = [],
        _ou = [],
        _mc = [],
        _mu = [],
        _ccsc = [],
        _ccsu = [],
        _ygc = [],
        _ygct = [],
        _fgc = [],
        _fgct = [],
        _gct = [];
    $.each(_caches, function (index, data) {
        var _data = eval('(' + data + ')');
        _date.push(_data.date);
        _s0c.push(_data.s0c);
        _s1c.push(_data.s1c);
        _s0u.push(_data.s0u);
        _s1u.push(_data.s1u);
        _ec.push(_data.ec);
        _eu.push(_data.eu);
        _oc.push(_data.oc);
        _ou.push(_data.ou);
        _mc.push(_data.mc);
        _mu.push(_data.mu);
        _ccsc.push(_data.ccsc);
        _ccsu.push(_data.ccsu);
        _ygc.push(_data.ygc);
        _ygct.push(_data.ygct);
        _fgc.push(_data.fgc);
        _fgct.push(_data.fgct);
        _gct.push(_data.gct);
    })

    var gcscoption = {
        title: {
            text: 's0c & s1c',
            left: '4%'
        },
        tooltip: {
            trigger: 'axis'
        },
        legend: {
            data: ['s0c', 's1c']
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
            data: _date
        },
        yAxis: {
            type: 'value'
        },
        series: [
            {
                name: 's0c',
                type: 'line',
                stack: 's0c (bytes)',
                data: _s0c
            },
            {
                name: 's1c',
                type: 'line',
                stack: 's1c (bytes)',
                data: _s1c
            }
        ]
    };
    var gcsuoption = {
        title: {
            text: 's0u & s1u',
            left: '4%'
        },
        tooltip: {
            trigger: 'axis'
        },
        legend: {
            data: ['s0u', 's1u']
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
            data: _date
        },
        yAxis: {
            type: 'value'
        },
        series: [
            {
                name: 's0u',
                type: 'line',
                stack: 's0u (bytes)',
                data: _s0u
            },
            {
                name: 's1u',
                type: 'line',
                stack: 's1u (bytes)',
                data: _s1u
            }
        ]
    };
    var gceoption = {
        title: {
            text: 'ec & eu',
            left: '4%'
        },
        tooltip: {
            trigger: 'axis'
        },
        legend: {
            data: ['ec', 'eu']
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
            data: _date
        },
        yAxis: {
            type: 'value'
        },
        series: [
            {
                name: 'ec',
                type: 'line',
                stack: 'ec (bytes)',
                data: _ec
            },
            {
                name: 'eu',
                type: 'line',
                stack: 'eu (bytes)',
                data: _eu
            }
        ]
    };
    var gcooption = {
        title: {
            text: 'oc & ou',
            left: '4%'
        },
        tooltip: {
            trigger: 'axis'
        },
        legend: {
            data: ['oc', 'ou']
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
            data: _date
        },
        yAxis: {
            type: 'value'
        },
        series: [
            {
                name: 'oc',
                type: 'line',
                stack: 'oc (bytes)',
                data: _oc
            },
            {
                name: 'ou',
                type: 'line',
                stack: 'ou (bytes)',
                data: _ou
            }
        ]
    };
    var gcmoption = {
        title: {
            text: 'mc & mu',
            left: '4%'
        },
        tooltip: {
            trigger: 'axis'
        },
        legend: {
            data: ['mc', 'mu']
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
            data: _date
        },
        yAxis: {
            type: 'value'
        },
        series: [
            {
                name: 'mc',
                type: 'line',
                stack: 'mc (bytes)',
                data: _mc
            },
            {
                name: 'mu',
                type: 'line',
                stack: 'mu (bytes)',
                data: _mu
            }
        ]
    };
    var gcccsoption = {
        title: {
            text: 'ccsc & ccsu',
            left: '4%'
        },
        tooltip: {
            trigger: 'axis'
        },
        legend: {
            data: ['ccsc', 'ccsu']
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
            data: _date
        },
        yAxis: {
            type: 'value'
        },
        series: [
            {
                name: 'ccsc',
                type: 'line',
                stack: 'ccsc (bytes)',
                data: _ccsc
            },
            {
                name: 'ccus',
                type: 'line',
                stack: 'ccus (bytes)',
                data: _ccsu
            }
        ]
    };
    var gcyfgcoption = {
        title: {
            text: 'ygc & fgc',
            left: '4%'
        },
        tooltip: {
            trigger: 'axis'
        },
        legend: {
            data: ['ygc', 'fgc']
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
            data: _date
        },
        yAxis: {
            type: 'value'
        },
        series: [
            {
                name: 'ygc',
                type: 'line',
                stack: 'ygc (次数)',
                data: _ygc
            },
            {
                name: 'fgc',
                type: 'line',
                stack: 'fgc (次数)',
                data: _fgc
            }
        ]
    };
    var gcyfgctoption = {
        title: {
            text: 'ygct & fgct',
            left: '4%'
        },
        tooltip: {
            trigger: 'axis'
        },
        legend: {
            data: ['ygct', 'fgct']
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
            data: _date
        },
        yAxis: {
            type: 'value'
        },
        series: [
            {
                name: 'ygct',
                type: 'line',
                stack: 'ygct (s)',
                data: _ygct
            },
            {
                name: 'fgct',
                type: 'line',
                stack: 'fgct (s)',
                data: _fgct
            }
        ]
    };
    var gcgctoption = {
        title: {
            text: 'gct',
            left: '4%'
        },
        tooltip: {
            trigger: 'axis'
        },
        legend: {
            data: ['gct']
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
            data: _date
        },
        yAxis: {
            type: 'value'
        },
        series: [
            {
                name: 'gct',
                type: 'line',
                stack: 'gct (s)',
                data: _gct
            }
        ]
    };

    gcsc.setOption(gcscoption);
    gcsu.setOption(gcsuoption);
    gce.setOption(gceoption);
    gco.setOption(gcooption);
    gcm.setOption(gcmoption);
    gcccs.setOption(gcccsoption);
    gcyfgc.setOption(gcyfgcoption);
    gcyfgct.setOption(gcyfgctoption);
    gcgct.setOption(gcgctoption);

}

/**
 * 渲染gc Table
 */
_table.gc = function () {
    table.render({
        elem: '#gcTable'
        , cols: [[
            {field: 's0c', title: 'S0C', sort: true}
            , {field: 's1c', title: 'S1C', sort: true}
            , {field: 's0u', title: 'S0U', sort: true}
            , {field: 's1u', title: 'S1U', sort: true}
            , {field: 'ec', title: 'EC', sort: true}
            , {field: 'eu', title: 'EU', sort: true}
            , {field: 'oc', title: 'OC', sort: true}
            , {field: 'ou', title: 'OU', sort: true}
            , {field: 'mc', title: 'MC', sort: true}
            , {field: 'mu', title: 'MU', sort: true}
            , {field: 'ccsc', title: 'CCSC', sort: true}
            , {field: 'ccsu', title: 'CCSU', sort: true}
            , {field: 'ygc', title: 'YGC', sort: true}
            , {field: 'ygct', title: 'YGCT', sort: true}
            , {field: 'fgc', title: 'FGC', sort: true}
            , {field: 'fgct', title: 'FGCT', sort: true}
            , {field: 'gct', title: 'GCT', sort: true}
            , {field: 'date', title: 'DATE', sort: true}
        ]]
        , data: util.arrayToJSON(util.getArrayData(cache.getCache("gc", "gc" + _pid), 10))
        , width: $("#tableBox").css("width").substring(0, $("#tableBox").css("width").length - 2) - 50
        , limit: 10
    });
}

/**
 * 渲染gcutil Echarts
 */
_view.gcutil = function () {
    var gcutil = echarts.init(document.getElementById('gcutilmain'));
    var _caches = cache.getCache("gcutil", "gcutil" + _pid);
    var _date = [],
        _s0 = [],
        _s1 = [],
        _e = [],
        _o = [],
        _m = [],
        _ccs = [];
    $.each(_caches, function (index, data) {
        var _data = eval('(' + data + ')');
        _date.push(_data.date);
        _s0.push(_data.s0);
        _s1.push(_data.s1);
        _e.push(_data.e);
        _o.push(_data.o);
        _m.push(_data.m);
        _ccs.push(_data.ccs);
    })

    var gcutiloption = {
        title: {
            text: 'gc统计 (gcutil)',
            left: '4%'
        },
        tooltip: {
            trigger: 'axis'
        },
        legend: {
            data: ['s0', 's1', 'e', 'o', 'm', 'ccs']
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
            data: _date
        },
        yAxis: {
            type: 'value',
            // 不显示多余的线
            // axisLine: {
            //     lineStyle: {
            //         color: '#009688',
            //         width: 3
            //     }
            // },
            // axisTick: {
            //     length: 0
            // }
        },
        series: [
            {
                name: 's0',
                type: 'line',
                stack: 's0 (%)',
                data: _s0
            },
            {
                name: 's1',
                type: 'line',
                stack: 's1 (%)',
                data: _s1
            },
            {
                name: 'e',
                type: 'line',
                stack: 'e (%)',
                data: _e
            },
            {
                name: 'o',
                type: 'line',
                stack: 'o (%)',
                data: _o
            },
            {
                name: 'm',
                type: 'line',
                stack: 'm (%)',
                data: _m
            },
            {
                name: 'ccs',
                type: 'line',
                stack: 'ccs (%)',
                data: _ccs
            }
        ]
    };

    gcutil.setOption(gcutiloption);
}

/**
 * 渲染gcutil Table
 */
_table.gcutil = function () {
    table.render({
        elem: '#gcutilTable'
        , cols: [[
            {field: 's0', title: 'S0', sort: true}
            , {field: 's1', title: 'S1', sort: true}
            , {field: 'e', title: 'E', sort: true}
            , {field: 'o', title: 'O', sort: true}
            , {field: 'm', title: 'M', sort: true}
            , {field: 'ccs', title: 'CCS', sort: true}
            , {field: 'ygc', title: 'YGC', sort: true}
            , {field: 'ygct', title: 'YGCT', sort: true}
            , {field: 'fgc', title: 'FGC', sort: true}
            , {field: 'fgct', title: 'FGCT', sort: true}
            , {field: 'gct', title: 'GCT', sort: true}
            , {field: 'date', title: 'DATE', sort: true}
        ]]
        , data: util.arrayToJSON(util.getArrayData(cache.getCache("gcutil", "gcutil" + _pid), 10))
        , width: $("#tableBox").css("width").substring(0, $("#tableBox").css("width").length - 2) - 50
        , limit: 10
    });
}

/**
 * 渲染compiler Echarts
 */
_view.compiler = function () {
    var compiler = echarts.init(document.getElementById('compilermain'));
    var _caches = cache.getCache("compiler", "compiler" + _pid);
    var _date = [],
        _compiled = [],
        _failed = [],
        _invalid = []

    $.each(_caches, function (index, data) {
        var _data = eval('(' + data + ')');
        _date.push(_data.date);
        _compiled.push(_data.compiled);
        _failed.push(_data.failed);
        _invalid.push(_data.invalid);
    })

    var compileroption = {
        title: {
            text: 'compiler',
            left: '4%'
        },
        tooltip: {
            trigger: 'axis'
        },
        legend: {
            data: ['compiled', 'failed', 'invalid']
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
            data: _date
        },
        yAxis: {
            type: 'value'
        },
        series: [
            {
                name: 'compiled',
                type: 'line',
                stack: 'compiled',
                data: _compiled
            },
            {
                name: 'failed',
                type: 'line',
                stack: 'failed',
                data: _failed
            },
            {
                name: 'invalid',
                type: 'line',
                stack: 'invalid',
                data: _invalid
            }
        ]
    };

    compiler.setOption(compileroption);
}

/**
 * 渲染compiler Table
 */
_table.compiler = function () {
    table.render({
        elem: '#compilerTable'
        , cols: [[
            {field: 'compiled', title: 'Compiled', sort: true}
            , {field: 'failed', title: 'Failed', sort: true}
            , {field: 'invalid', title: 'Invalid', sort: true}
            , {field: 'time', title: 'Time', sort: true}
            , {field: 'failedType', title: 'FailedType'}
            , {field: 'date', title: 'Date', sort: true}
            , {field: 'failedMethod', title: 'FailedMethod'}
        ]]
        , data: util.arrayToJSON(util.getArrayData(cache.getCache("compiler", "compiler" + _pid), 10))
        , width: $("#tableBox").css("width").substring(0, $("#tableBox").css("width").length - 2) - 50
        , limit: 10
    });
}

/**
 * 渲染compiler Table
 */
_table.compilation = function () {
    table.render({
        elem: '#compilationTable'
        , cols: [[
            {field: 'compiled', title: 'Compiled', sort: true}
            , {field: 'size', title: 'Size', sort: true}
            , {field: 'type', title: 'Type', sort: true}
            , {field: 'date', title: 'Date', sort: true}
            , {field: 'method', title: 'Method'}
        ]]
        , data: util.arrayToJSON(util.getArrayData(cache.getCache("compilation", "compilation" + _pid), 10))
        , width: $("#tableBox").css("width").substring(0, $("#tableBox").css("width").length - 2) - 50
        , limit: 10
    });
}

/**
 * 渲染stack Echarts
 */
_view.stack = function () {
    var stack = echarts.init(document.getElementById('stackmain'));
    var _caches = cache.getCache("stack", "stack" + _pid);
    var _date = [],
        _running = [],
        _timeWaiting = [],
        _waiting = [],
        _vmTotal = [],
        _blocked = [],
        _total = [];
    $.each(_caches, function (index, data) {
        var _data = eval(data);
        _date.push(_data.date);
        _running.push(_data.running);
        _timeWaiting.push(_data.timeWaiting);
        _waiting.push(_data.waiting);
        _vmTotal.push(_data.vmTotal);
        _blocked.push(_data.blocked)
        _total.push(_data.total);
    })

    var stackoption = {
        title: {
            text: 'Thread (stack)',
            left: '4%'
        },
        tooltip: {
            trigger: 'axis'
        },
        legend: {
            data: ['running', 'waiting', 'timeWaiting', 'blocked', 'vmTotal', 'total']
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
            data: _date
        },
        yAxis: {
            type: 'value'
        },
        series: [
            {
                name: 'running',
                type: 'line',
                stack: 'running',
                data: _running
            },
            {
                name: 'waiting',
                type: 'line',
                stack: 'waiting',
                data: _waiting
            },
            {
                name: 'timeWaiting',
                type: 'line',
                stack: 'timeWaiting',
                data: _timeWaiting
            },
            {
                name: 'blocked',
                type: 'line',
                stack: 'blocked',
                data: _blocked
            },
            {
                name: 'vmTotal',
                type: 'line',
                stack: 'vmTotal',
                data: _vmTotal
            },
            {
                name: 'total',
                type: 'line',
                stack: 'total',
                data: _total
            }
        ]
    };

    stack.setOption(stackoption);
}

/**
 * 渲染stack Table
 */
_table.stack = function () {
    table.render({
        elem: '#stackTable'
        , cols: [[
            {field: 'running', title: 'Running', sort: true}
            , {field: 'waiting', title: 'Waiting', sort: true}
            , {field: 'timeWaiting', title: 'TimeWaiting', sort: true}
            , {field: 'total', title: 'total', sort: true}
            , {field: 'date', title: 'Date', sort: true}
        ]]
        , data: util.getArrayData(cache.getCache("stack", "stack" + _pid), 10)
        , width: $("#tableBox").css("width").substring(0, $("#tableBox").css("width").length - 2) - 50
        , limit: 10
    });
}

/**
 * ClassLoader参数信息弹出框
 */
_help.classIndex = 0;

/**
 * ClassLoader参数信息
 */
_help.classInfo = function () {
    if (_help.classIndex == 0) {
        layer.open({
            type: 1,
            shade: 0,
            title: "ClassLoader参数",
            content: '<div style="margin-top: 10px;margin-bottom: 10px;margin-left: 10px;margin-right: 10px;line-height: 30px;">' +
            '<p>loaded: 装载class的数量</p>' +
            '<p>unloaded: 卸载class的数量</p>' +
            '<p>loadedBytes: 装载class所占用空间大小</p>' +
            '<p>unloadedBytes: 卸载class所占用空间大小</p>' +
            '<p>time: 装载和卸载类所花费的时间</p>' +
            '</div>',
            success: function (layero, index) {
                _help.classIndex++;
            },
            cancel: function (index, layero) {
                _help.classIndex--;
            }
        });
    }
}

/**
 * GC参数信息弹出框
 */
_help.gcIndex = 0;

/**
 * GC参数信息
 */
_help.gcInfo = function () {
    if (_help.gcIndex == 0) {
        layer.open({
            type: 1,
            shade: 0,
            title: "GC参数",
            content: '<div style="margin-top: 10px;margin-bottom: 10px;margin-left: 10px;margin-right: 10px;line-height: 30px;">' +
            '<p>s0c: 年轻代中第一个幸存区的容量 （字节）</p>' +
            '<p>s1c: 年轻代中第二个幸存区的容量 （字节）</p>' +
            '<p>s0u: 年轻代中第一个幸存区已使用空间 (字节)</p>' +
            '<p>s1u: 年轻代中第二个幸存区已使用空间 (字节)</p>' +
            '<p>ec: 年轻代中伊甸园的容量 (字节)</p>' +
            '<p>eu: 年轻代中Eden伊甸园已使用空间 (字节)</p>' +
            '<p>oc: Old代的容量 (字节)</p>' +
            '<p>ou: Old代已使用空间 (字节)</p>' +
            '<p>mc: 元空间的容量 (字节)</p>' +
            '<p>mu: 元空间已使用空间 (字节)</p>' +
            '<p>ccsc: 压缩类空间大小</p>' +
            '<p>ccsu: 压缩类空间使用大小</p>' +
            '<p>ygc: 年轻代gc次数</p>' +
            '<p>ygct: 年轻代gc消耗时间</p>' +
            '<p>fgc: 老年代gc次数</p>' +
            '<p>fgct: 老年代gc消耗时间</p>' +
            '<p>gct: gc消耗总时间</p>' +
            '</div>',
            success: function (layero, index) {
                _help.gcIndex++;
            },
            cancel: function (index, layero) {
                _help.gcIndex--;
            }
        });
    }
}

/**
 * GC统计参数信息弹出框
 */
_help.gcUtilIndex = 0;

/**
 * GC统计参数信息
 */
_help.gcUtilInfo = function () {
    if (_help.gcUtilIndex == 0) {
        layer.open({
            type: 1,
            shade: 0,
            title: "GCUtil参数",
            content: '<div style="margin-top: 10px;margin-bottom: 10px;margin-left: 10px;margin-right: 10px;line-height: 30px;">' +
            '<p>s0: 年轻代中第一个幸存区使用比例</p>' +
            '<p>s1: 年轻代中第二个幸存区使用比例</p>' +
            '<p>e: 年轻代中伊甸园区使用比例</p>' +
            '<p>o: 老年代使用比例</p>' +
            '<p>m: 元数据区使用比例</p>' +
            '<p>css: 压缩使用比例</p>' +
            '</div>',
            success: function (layero, index) {
                _help.gcUtilIndex++;
            },
            cancel: function (index, layero) {
                _help.gcUtilIndex--;
            }
        });
    }
}

/**
 * 编译(JIT)参数信息弹出框
 */
_help.compilerIndex = 0;

/**
 * 编译(JIT)参数信息
 */
_help.compilerInfo = function () {
    if (_help.compilerIndex == 0) {
        layer.open({
            type: 1,
            shade: 0,
            title: "编译(JIT)参数",
            content: '<div style="margin-top: 10px;margin-bottom: 10px;margin-left: 10px;margin-right: 10px;line-height: 30px;">' +
            '<p>Compiled: 编译任务的数量</p>' +
            '<p>Failed: 编译任务执行失败数量</p>' +
            '<p>Invalid: 编译任务执行失效数量</p>' +
            '<p>Time: 编译任务消耗时间</p>' +
            '<p>Type: 编译类型</p>' +
            '<p>Size: 方法生成的字节码的大小</p>' +
            '<p>Method: 类名和方法名用来标识编译的方法</p>' +
            '<p>FailedType: 最后一个编译失败任务的类型</p>' +
            '<p>FailedMethod: 最后一个编译失败任务所在的类及方法</p>' +
            '</div>',
            success: function (layero, index) {
                _help.compilerIndex++;
            },
            cancel: function (index, layero) {
                _help.compilerIndex--;
            }
        });
    }
}

/**
 * jstack Thread 参数信息弹出框
 */
_help.jstackIndex = 0;

/**
 * jstack Thread 参数信息
 */
_help.jstackInfo = function () {
    if (_help.jstackIndex == 0) {
        layer.open({
            type: 1,
            shade: 0,
            title: "Jstack Thread 参数",
            content: '<div style="margin-top: 10px;margin-bottom: 10px;margin-left: 10px;margin-right: 10px;line-height: 30px;">' +
            '<p>Running: 正在运行的线程数</p>' +
            '<p>Waiting: 等待唤醒的线程数</p>' +
            '<p>Blocked: 被阻塞的线程数</p>' +
            '<p>TimeWaiting: 等待唤醒 但设置了时限的线程数</p>' +
            '<p>VmTotal: vm线程总数</p>' +
            '<p>Total: 线程总数</p>' +
            '</div>',
            success: function (layero, index) {
                _help.jstackIndex++;
            },
            cancel: function (index, layero) {
                _help.jstackIndex--;
            }
        });
    }
}

/**
 * 保存堆快照
 */
_help.createStackSnap = function () {

}

/**
 * 线程快照下标
 * @type {number}
 */
_help.threadIndex = 0;

/**
 * 保存线程快照
 */
_help.createThreadSnap = function () {
    if (_help.threadIndex == 0) {
        $.post("/createthreadsnap", {
            pid: _pid
        }, function (data) {
            _help.threadIndex++;
            layer.msg('快照创建成功' + data, {icon: 1});
        })
    } else {
        layer.msg("已经创建过了,稍等一下吧", function () {
        })
    }
}