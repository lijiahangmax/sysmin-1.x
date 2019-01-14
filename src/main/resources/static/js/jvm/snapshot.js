var layer = layui.layer,
    table = layui.table,
    element = layui.element;

$(function () {
    new _init()
});

/**
 * 初始化方法
 * @private
 */
var _init = function () {
    if (cache.getCache("config", "showtable") !== 2) {
        _view.initThread();
        _view.showThread();
        $("#changeTable").text("显示堆快照")
    } else {
        _view.initStack();
        _view.showStack();
        $("#changeTable").text("显示线程快照")
    }
};

/**
 * 显示
 * @type {Object}
 * @private
 */
var _view = new Object();

/**
 * 1 thread表格
 * 2 stack表格
 * @type {number}
 */
_view.thisShow = 1;

/**
 * 线程表格
 */
_view.threadTable;

/**
 * 堆表格
 */
_view.stackTable;

/**
 * 点击下载s
 */
$(document.body).on('click', '.download', function () {
    var path = $(this).attr("path").replace(/[\\/]/g, ",");
    window.open("/downloadsnap?path=" + path)
});

/**
 * 点击删除
 */
$(document.body).on('click', '.delete', function () {
    let path = $(this).attr("path").replace(/[\\/]/g, ",");
    layer.confirm('确定要删除吗？', {
        btn: ['确定', '取消']
    }, function (index, layero) {
        $.post("/deletesnap", {
            path: path
        }, function (data) {
            if (data === 1) {
                layer.msg('删除成功', {icon: 1});
            } else if (data === 0) {
                layer.msg('删除失败', {icon: 2});
            }
        });
        layer.close(index);
        if (_view.threadTable !== undefined) {
            _view.threadTable.reload();
        }
        if (_view.stackTable !== undefined) {
            _view.stackTable.reload();
        }
    });
});

/**
 * 加载thread快照表格
 */
_view.initThread = function () {
    _view.threadTable = table.render({
        elem: '#threadTable',
        url: '/getthreadsnap',
        page: true,
        cols: [[
            {field: 'pid', title: 'PID', sort: true, width: 100}
            , {field: 'size', title: '大小', sort: true, width: 120}
            , {field: 'date', title: '日期', sort: true, width: 200}
            , {field: 'path', title: '路径'}
            , {
                field: 'path', title: '操作', width: 180, templet: function (d) {
                    return '<button class="layui-btn download" path="' + d.path + '">下载</button><button class="layui-btn layui-btn-normal delete" path="' + d.path + '">删除</button>'
                }
            }
        ]],
        size: 'lg'
    });
};

/**
 * 加载stack快照表格
 */
_view.initStack = function () {
    _view.stackTable = table.render({
        elem: '#stackTable',
        url: '/getstacksnap',
        page: true,
        cols: [[
            {field: 'pid', title: 'PID', sort: true, width: 100}
            , {field: 'size', title: '大小', sort: true, width: 120}
            , {field: 'date', title: '日期', sort: true, width: 200}
            , {field: 'path', title: '路径'}
            , {
                field: 'path', title: '操作', width: 180, templet: function (d) {
                    return '<button class="layui-btn download" path="' + d.path + '">下载</button><button class="layui-btn layui-btn-normal delete" path="' + d.path + '">删除</button>'
                }
            }
        ]],
        size: 'lg'
    });
};

/**
 * 切换表格
 */
_view.changeTable = function () {
    if (_view.thisShow === 1) {
        if (_view.stackTable === undefined) {
            _view.initStack()
        }
        _view.showStack();
        $("#changeTable").text("显示线程快照")
    } else if (_view.thisShow === 2) {
        if (_view.threadTable === undefined) {
            _view.initThread();
        }
        _view.showThread();
        $("#changeTable").text("显示堆快照");
    }
};

/**
 * 显示线程表格
 */
_view.showThread = function () {
    $("#threadbox").show();
    $("#stackbox").hide();
    _view.thisShow = 1;
    cache.addCache("config", "showtable", 1);
};

/**
 * 显示堆表格
 */
_view.showStack = function () {
    $("#stackbox").show();
    $("#threadbox").hide();
    _view.thisShow = 2;
    cache.addCache("config", "showtable", 2);
};