var layer = layui.layer,
    // 定义一个sock连接（并没有进行连接）, url和服务端定义的stomp终端是一致的
    _sockJS,
    // 创建一个客户端对象
    _stompClient,
    // 是否订阅queue 0未订阅 1订阅
    _subQueue = 0,
    // 是否连接
    _connection = 0,
    // 订阅queue对象，用于关闭订阅
    _subscribeQueue,
    // 用户id
    _userid = "test";

/**
 * 打开双通道
 */
$(function () {
    init()
    connectionServer()
})

/**
 * 初始化
 */
function init() {
    _sockJS = new SockJS("/sock");
    _stompClient = Stomp.over(_sockJS);
}

/**
 *  连接服务端
 */
function connectionServer() {
    if (_connection == 0 && _sockJS == null && _stompClient == null) {
        alert("init")
        init()
    }
    if (_connection == 0) {
        _stompClient.connect({}, function () {
            layer.msg("WebSocket已连接！");
            // 订阅服务端发送的消息，用于接收消息
            _connection = 1;
            subscription();
        }, function () {
            layer.alert("WebSocket连接失败！")
        });
    } else {
        layer.msg("WebSocket已连接！");
    }
}

/**
 *  订阅
 */
function subscription() {
    if (_connection == 1) {
        if (_subQueue != 1) {
            _subscribeQueue = _stompClient.subscribe('/user/' + _userid + '/queueto', function (data) {
                printLog("收到 一对一 推送消息 " + data.body);
            });
            _subQueue = 1;
        }
        printLog("已订阅 一对一消息");
    } else if (_connection == 0) {
        layer.alert("未连接到websocket服务器")
    }
}

/**
 * 断开连接
 */
function disconnect() {
    if (_stompClient != null && _connection == 1) {
        _stompClient.disconnect();
        _subQueue = 0;
        _connection = 0;
        _sockJS = null;
        _stompClient = null;
    }
    printLog("WebSocket连接已断开！")
}

/**
 * 取消订阅
 */
function unSubscription() {
    if (_subscribeQueue != null && _connection == 1) {
        if (_subQueue == 1) {
            _subscribeQueue.unsubscribe();
            _subQueue = 0;
        }
    } else if (_connection == 0) {
        layer.alert("未连接到websocket服务器")
    }
    printLog("取消订阅消息");
}

/**
 * 向服务器发送消息（服务器将发送给发送者）
 * 如果取消订阅依旧可以发送消息 但是不可以 接受消息
 * 如果取消订阅不能发送消息 需配置 && _subQueue == 1
 */
function queueSend() {
    if (_connection == 0) {
        layer.alert("未连接到websocket服务器")
        return false;
    }
    var message = $("#input").val();
    if (message == "" || $.trim(message).length == 0) {
        layer.msg("信息不能为空")
        return false;
    }
    if (_subscribeQueue != null && _connection == 1) {
        // 参数依次为 发送地址，header，消息
        _stompClient.send("/app/queuesend", {}, message);
        printLog("发送 一对一消息 " + message);
    }
}

/**
 * 打印拼接日志
 */
function printLog(log) {
    console.log(log);
}