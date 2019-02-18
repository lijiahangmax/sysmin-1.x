var layer = layui.layer,
    element = layui.element;

var _sockJS = new SockJS("/sock"),
    _stompClient = Stomp.over(_sockJS),
    // 是否是log页面上
    _logPage = false,
    // 持久化id自增
    _logId = -1,
    // 是否连接
    _connectStatus = false,
    // 用户id
    _userId = "test";

$(function () {
    new _init();
});

/**
 * 初始化
 * @private
 */
var _init = function () {
    _indexElement.getSystemType();
    _indexElement.listenerLog();
    _indexElement.getJavaProcess();

};

// 主页元素
var _indexElement = new Object();

// 操作数据对象
var data = new Object();

/**
 * 点击li a切换页面
 */
$(document.body).on('click', '.changepage', function () {
    _indexElement.changeIframe($(this).attr("ref"))
});

/**
 * 切换页面
 * @param src 页面路径
 */
_indexElement.changeIframe = function (src) {
    _logPage = false;
    if (src === "/log") {
        _logPage = true
    }
    $("#pageiframe").attr("src", src);
};

/**
 * 点击日志页面
 */
$("#log").click(function () {
    pageElement.logDotHide();
    _indexElement.changeIframe($(this).attr("ref"));
});

/**
 * 监听日志
 */
_indexElement.listenerLog = function () {
    if (!_sockJS) {
        _sockJS = new SockJS("/sock");
        _stompClient = Stomp.over(_sockJS);
    }
    _stompClient.connect({}, function () {
        _connectStatus = true;
    });
    // connect需要时间建立连接
    setTimeout(() => {
        if (_connectStatus) {
            _stompClient.subscribe('/user/' + _userId + '/log', function (log) {
                if (!_logPage && layui.data("config", "switchLog") != 0) {
                    _logId++;
                    cache.addCache("log", _logId, log);
                    pageElement.logDotShow();
                }
            });
        }
    }, 1000);
};

/**
 * 所有jvm进程的pid
 * @type {[]}
 * @private
 */
var _jvmAllProcessArr = [];

/**
 * 获得jps进程
 */
_indexElement.getJavaProcess = function () {
    if (!_sockJS) {
        _sockJS = new SockJS("/sock");
        _stompClient = Stomp.over(_sockJS);
    }
    $.post("/jps", {}, function (_data) {
        cache.cleanCache("jps");
        if (_data.clear) {
            data.clearJVMData();
        }
        if (!_connectStatus) {
            _stompClient.connect({}, function () {
                _connectStatus = true;
                _indexElement.appendJavaProcess(_data.data);
            });
        } else {
            _indexElement.appendJavaProcess(_data.data);
        }
    });
};

/**
 * 拼接jps进程
 * @param data jps数据
 */
_indexElement.appendJavaProcess = function (data) {
    _jvmAllProcessArr = [];
    $.each(eval(data), function (index, _data) {
        _jvmAllProcessArr.push(_data.pid);
        cache.addCache("jps", _data.pid, _data.flags);
        $("#javaprocess").prepend('<dd><a href="javascript:;" class="changepage" ref="/javaprocessinfo?pid=' + _data.pid + '">' + _data.name + '</a></dd>');
        _indexElement.listenerJavaProcess('/user/' + _userId + '/gc/' + _data.pid, _data.pid, "gc");
        _indexElement.listenerJavaProcess('/user/' + _userId + '/gcutil/' + _data.pid, _data.pid, "gcutil");
        _indexElement.listenerJavaProcess('/user/' + _userId + '/compiler/' + _data.pid, _data.pid, "compiler");
        _indexElement.listenerJavaProcess('/user/' + _userId + '/compilation/' + _data.pid, _data.pid, "compilation");
        _indexElement.listenerJavaProcess('/user/' + _userId + '/classloader/' + _data.pid, _data.pid, "classloader");
    });
};

/**
 * 点击jvm监控
 */
$("#jvmmom").click(function () {
    if (data.stackPoll === undefined) {
        _indexElement.getStack(60000);
    }
    $.post("/monitorsize", {}, function (data) {
        // if (data === 110) {
        if (data === 0) {
            $.post("/monitorallopt", {
                pids: _jvmAllProcessArr.join(",")
            }, function (data) {
                if (data === 2) {
                    layer.alert("JVM监控失败")
                }
            })
        }
    });
});

/**
 * 清除jvm缓存数据
 */
data.clearJVMData = function () {
    cache.cleanCache("jps");
    cache.cleanCache("classloader");
    cache.cleanCache("gc");
    cache.cleanCache("gcutil");
    cache.cleanCache("compiler");
    cache.cleanCache("compilation");
    cache.cleanCache("stack");
};

/**
 * 监听所有java进程
 * @param _url 订阅地址
 * @param _pid 进程id
 */
_indexElement.listenerJavaProcess = function (_url, _pid, _type) {
    _stompClient.subscribe(_url, function (_data) {
        data.addJVMDataToCache(_data.body, _pid, _type);
    });
};

/**
 * 用于关闭轮询获取stack信息
 */
data.stackPoll;

/**
 * 轮询获取stack数据
 * @param timer
 */
_indexElement.getStack = function (timer) {
    data.getStackToCache();
    data.stackPoll = window.setInterval(function () {
        data.getStackToCache();
    }, timer)
};

/**
 * 关闭所有java进程
 */
_indexElement.destroyJavaProcess = function () {
    $.post("/stopallmonitor", {}, function (data) {
        layer.msg("已关闭 " + data + " 个Java进程")
    });
};

/**
 * 系统类型  1: Linux 0: Windows
 * @type {number}
 */
_indexElement.systemType = 0;

/**
 * 获得系统类型
 */
_indexElement.getSystemType = function () {
    $.post("/getsystemtype", {}, function (data) {
        _indexElement.systemType = data;
        cache.addCache("config", "systemtype", data);
        if (_indexElement.systemType === 0) {
            $(".linuxservice").hide();
            element.render('nav');
        }
    })
};

/**
 * 添加jvm数据到缓存
 * @param _data 数据
 * @param _pid 进程id
 * @param _type 进程类型
 */
data.addJVMDataToCache = function (_data, _pid, _type) {
    let key = _type + _pid;
    let _caches = cache.getCache(_type, key);
    if (_caches === undefined) {
        cache.addCache(_type, _type + _pid, new Array(_data));
    } else {
        _caches.push(_data);
        // 显示10条
        if (_caches.length > 10) {
            _caches.splice(0, _caches.length - 10);
        }
        cache.addCache(_type, _type + _pid, _caches);
    }
    console.log(cache.getCache(_type));
};

/**
 * 添加stack thread 到cache
 */
data.getStackToCache = function () {
    $.post("/monitorallstack", {
        pids: _jvmAllProcessArr.join(",")
    }, function ($data) {
        $.each($data, function (index, _data) {
            data.addJVMDataToCache(_data, index, "stack");
        });
    });
};

// 页面元素
var pageElement = new Object();

/**
 * 显示日志面包屑
 */
pageElement.logDotShow = function () {
    if ($("#log").children().length === 1) {
        $("#log").append('<span id="logdot" class="layui-badge-dot layui-anim layui-anim-scale layui-bg-blue"></span>');
    } else {
        $("#logdot").show();
    }
};

/**
 * 隐藏日志面包屑
 */
pageElement.logDotHide = function () {
    $("#logdot").hide();
};
