var layer = layui.layer,
    table = layer.table,
    element = layui.element;

$(function () {

})

var _init = function () {

}

/**
 * 显示
 * @type {Object}
 * @private
 */
var _view = new Object();

/**
 * 线程表格
 */
_view.threadTable;

/**
 * 线程表格参数
 */
_view.threadTableOption;

/**
 * 堆表格
 */
_view.stackTable;

/**
 * 堆表格参数
 */
_view.stackTableOption;

/**
 * 加载thread快照表格
 */
_view.initThread = function () {
    table.render({
        url: '/api/data/'
    });
}