var layer = layui.layer
element = layui.element;

var _sockJS = new SockJS("/sock"),
    _stompClient = Stomp.over(_sockJS),
    // 是否是log页面上
    _logPage = false,
    // 持久化id自增
    _logId = -1,
    // 用户id
    _userId = "test";

$(function () {
    new _init();
})

/**
 * 初始化
 * @private
 */
var _init = function () {
    _indexElement.listenerLog();
    _indexElement.appendJavaProcess();
}
// 主页元素
var _indexElement = new Object();

/**
 * 点击li a切换页面
 */
$(document.body).on('click', '.changepage', function () {
    _indexElement.changeIframe($(this).attr("ref"))
})

/**
 * 切换页面
 * @param src 页面路径
 */
_indexElement.changeIframe = function (src) {
    _logPage = false;
    if (src == "/log") {
        _logPage = true
    }
    $("#pageiframe").attr("src", src);
}

/**
 * 点击日志页面
 */
$("#log").click(function () {
    pageElement.logDotHide();
    _indexElement.changeIframe($(this).attr("ref"))
})

/**
 * 监听日志
 */
_indexElement.listenerLog = function () {
    _stompClient.connect({}, function () {
        _stompClient.subscribe('/user/' + _userId + '/log', function (log) {
            if (!_logPage && layui.data("config", "switchLog") != 0) {
                _logId++;
                cache.addCache("log", _logId, log);
                pageElement.logDotShow();
            }
        });
    });
}

/**
 * 所有jvm进程的pid
 * @type {[]}
 * @private
 */
var _jvmAllProcessArr = [];

/**
 * 拼接jps进程
 */
_indexElement.appendJavaProcess = function () {
    _jvmAllProcessArr = [];
    $.post("/jps", {}, function (data) {
        $.each(eval(data), function (index, _data) {
            _jvmAllProcessArr.push(_data.pid);
            $("#javaprocess").prepend('<dd><a href="javascript:;" class="changepage" ref="/javaprocessinfo?pid=' + _data.pid + '">' + _data.name + '</a></dd>')
            _indexElement.listenerJavaProcess('/user/' + _userId + '/classloader/' + _data.pid, _data.pid, "classloader")
        })
    })
}

/**
 * 点击jvm监控
 */
$("#jvmmom").click(function () {
    //cache.cleanCache("classloader")
    if (cache.getCache("config", "monitorallopt") != 1) {
        $.post("/monitorallopt", {
            pids: _jvmAllProcessArr.join(",")
        }, function (data) {
            cache.addCache("config", "monitorallopt", 1)
        })
    }
    //cache.cleanCache("config")

})

/**
 * 监听所有java进程
 * @param _url 订阅地址
 * @param _pid 进程id
 */
_indexElement.listenerJavaProcess = function (_url, _pid, _type) {
    _stompClient.subscribe(_url, function (_data) {
        var key = _type + _pid;
        let _caches = cache.getCache(_type, key);
        if (_caches == undefined) {
            cache.addCache(_type, _type + _pid, new Array(_data.body))
        } else {
            _caches.push(_data.body)
            // 显示10条
            if (_caches.length > 10) {
                _caches.splice(0, _caches.length - 10);
            }
            cache.addCache(_type, _type + _pid, _caches)
        }
        console.log(cache.getCache(_type))
    });
}

// 页面元素
var pageElement = new Object();

/**
 * 显示日志面包屑
 */
pageElement.logDotShow = function () {
    if ($("#log").children().length == 1) {
        $("#log").append('<span id="logdot" class="layui-badge-dot layui-anim layui-anim-scale layui-bg-blue"></span>')
    } else {
        $("#logdot").show();
    }
}

/**
 * 隐藏日志面包屑
 */
pageElement.logDotHide = function () {
    $("#logdot").hide();
}
