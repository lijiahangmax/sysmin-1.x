var layer = layui.layer,
    element = layui.element,
    form = layui.form,
    table = layui.table;

// 定义一个sock连接（并没有进行连接）, url和服务端定义的stomp终端是一致的
var _sockJS,
    // 创建一个客户端对象
    _stompClient,
    // 是否订阅queue 0未订阅 1订阅
    _subQueue = 0,
    // 是否连接
    _connection = 0,
    // 订阅queue对象，用于关闭订阅
    _subscribeQueue,
    // 用户id
    _userId = "test",
    // 日志数量
    _count = 0,
    // 文件分隔符
    _separator = "\\",
    // 是否正在显示文件
    _filePage = false;

// 监听状态  1/undefined监听  0未监听
var _listenerStatus = cache.getCache("config", "switchLog");

$(function () {
    new _init();
});

/**
 * 初始化
 * @private
 */
var _init = function () {
    // 如果已监听 修改开关状态
    if (_listenerStatus != 0) {
        $("#subscription").prop("checked", true);
        form.render("checkbox");
    }
    _logElenent.listLogFile();
    _logElenent.appendSessionLog();
    _socket.connectionServer();
};

/**
 * websocket 配置
 * @type {Object}
 * @private
 */
var _socket = new Object();

/**
 * websocket初始化
 */
_socket.init = function () {
    _sockJS = new SockJS("/sock");
    _stompClient = Stomp.over(_sockJS);
};

/**
 * 打开双通道 连接服务端
 */
_socket.connectionServer = function () {
    if (_connection === 0 && _sockJS === undefined && _stompClient === undefined) {
        _socket.init()
    }
    if (_connection === 0) {
        _stompClient.connect({}, function () {
            _connection = 1;
            if (_listenerStatus !== 0) {
                _socket.subscription();
            }
        }, function () {
            layer.alert("监听日志失败！")
        });
    }
};

/**
 * 监听开关
 */
form.on('switch(subscript)', function (data) {
    if (this.checked) {
        _socket.subscription();
        cache.addCache("config", "switchLog", "1");
    } else {
        _socket.unSubscription();
        cache.addCache("config", "switchLog", "0");
    }
});

/**
 * 监听持久化
 */
$("#cache_file").click(function () {
    $.post("/logcache", {}, function (data) {
        if (data === 0) {
            layer.msg('缓存失败', {icon: 2});
        } else if (data === 1) {
            layer.msg('缓存成功', {icon: 1});
            _logElenent.listLogFile();
        } else if (data === 2) {
            layer.msg('无日志', {icon: 4});
        }
    });
});

/**
 * 监听清除缓存
 */
$("#clean_cache").click(function () {
    cache.cleanCache("logcache");
    layer.msg('清除完成', {icon: 1});
});

/**
 *  订阅
 */
_socket.subscription = function () {
    if (_connection === 1) {
        if (_subQueue !== 1) {
            _subscribeQueue = _stompClient.subscribe('/user/' + _userId + '/log', function (log) {
                if (_filePage) {
                    cache.addCache("log", eval("(" + log.body + ")").id, log);
                    _pageElement.dotShow();
                } else {
                    _logElenent.printLog(log.body);
                }
            });
            _subQueue = 1;
        }
    } else if (_connection === 0) {
        layer.alert("监听日志失败！")
    }
};

/**
 * 取消订阅
 */
_socket.unSubscription = function () {
    if (_subscribeQueue !== undefined && _connection === 1) {
        if (_subQueue === 1) {
            _subscribeQueue.unsubscribe();
            _subQueue = 0;
        }
    }
};

/**
 * 点击文件列表文件
 */
$(document.body).on('click', '.logfile', function () {
    _filePage = true;
    _pageElement.xFileShow();
    $.post("/loadlogfile", {
        path: $(this).attr("path")
    }, function (data) {
        let _data = eval(data),
            _html = "";
        $.each(_data, function (index, $data) {
            if ($data.logType === "INFO") {
                $data.logType = '<span style="color:green">' + $data.logType + '</span>';
            } else if ($data.logType === "WARN") {
                $data.logType = '<span style="color:yellow">' + $data.logType + '</span>';
            } else if ($data.logType === "ERROR") {
                $data.logType = '<span style="color:red">' + $data.logType + '</span>';
            }
            _html += ("<div class='layui-row logs' id='log" + $data.id + "' style=''>" +
                "<div class='layui-col-md1'>" + $data.logType + "</div>" +
                "<div class='layui-col-md1'>" + $data.user + "</div>" +
                "<div class='layui-col-md2'>" + $data.createTime + "</div>" +
                "<div class='layui-col-md3'>" + $data.remake + "</div>" +
                "<div class='layui-col-md5'>" + $data.log + "</div>" +
                "</div><br/>");
        });
        $("#logcontainer").html(_html);
    });
});

/**
 * 日志元素
 * @type {Object}
 * @private
 */
var _logElenent = new Object();

/**
 * 罗列日志文件
 */
_logElenent.listLogFile = function () {
    $.post("/listlogfiles", {}, function (data) {
        var _files = eval(data);
        $("#logfiles").html("");
        for (var i = 0; i < _files.length; i++) {
            var file = _files[i].split(_separator);
            $("#logfiles").append("<div style='padding: 5px 5px 10px 10px; cursor: pointer;'>" +
                "<p path='" + _files[i] + "' class='logfile'>" +
                file[file.length - 1] +
                "</p>" +
                "</div>"
            );
        }
    });
};

/**
 * 关闭文件
 */
_logElenent.xfile = function () {
    _pageElement.dotHide();
    _pageElement.xFileHide();
    $("#logcontainer").html("");
    _filePage = false;
    _logElenent.appendSessionLog();
};

/**
 * 打印拼接日志
 */
_logElenent.printLog = function (log) {
    var _log = eval("(" + log + ")");
    if (_log.logType === "INFO") {
        _log.logType = '<span style="color:green">' + _log.logType + '</span>';
    } else if (_log.logType === "WARN") {
        _log.logType = '<span style="color:yellow">' + _log.logType + '</span>';
    } else if (_log.logType === "ERROR") {
        _log.logType = '<span style="color:red">' + _log.logType + '</span>';
    }
    $("#logcontainer").prepend("<div class='layui-row logs' id='log" + _log.id + "' style=''>" +
        "<div class='layui-col-md1'>" + _log.logType + "</div>" +
        "<div class='layui-col-md1'>" + _log.user + "</div>" +
        "<div class='layui-col-md2'>" + _log.createTime + "</div>" +
        "<div class='layui-col-md3'>" + _log.remake + "</div>" +
        "<div class='layui-col-md5'>" + _log.log + "</div>" +
        "</div><br/>");
    cache.addCache("logcache", _log.id, log)
};

/**
 * 拼接缓存中的日志
 */
_logElenent.appendSessionLog = function () {
    // log页面接收的数据
    var cacheData = cache.getCache("logcache");
    var _cacheData = eval(cacheData);
    $.each(_cacheData, function (key, log) {
        _logElenent.printLog(log);
    });
    // index页面接收的数据
    var data = cache.getCache("log");
    var _data = eval(data);
    $.each(_data, function (key, log) {
        _logElenent.printLog(log.body);
        cache.removeCache("log", key)
    })
};

/**
 * 页面元素
 * @type {Object}
 * @private
 */
var _pageElement = new Object();

/**
 * 显示面包屑
 */
_pageElement.dotShow = function () {
    parent.pageElement.logDotShow();
};

/**
 * 隐藏面包屑
 */
_pageElement.dotHide = function () {
    parent.pageElement.logDotHide();
};

/**
 * 隐藏关闭文件按钮
 */
_pageElement.xFileHide = function () {
    $("#xfile").hide();
};

/**
 * 显示关闭文件按钮
 */
_pageElement.xFileShow = function () {
    if ($("#toolsbox").children().length === 2) {
        $("#toolsbox").append('<button id="xfile" onclick="_logElenent.xfile()" class="layui-btn layui-btn-primary layui-anim layui-anim-scale">' +
            '<i class="layui-icon layui-icon-close"></i>关闭文件' +
            '</button>')
    } else {
        $("#xfile").show();
    }
};

