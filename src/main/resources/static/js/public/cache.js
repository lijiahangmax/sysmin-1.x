var cache = new Object();

/**
 * 添加到缓存
 * @param table 表
 * @param key 键
 * @param val 值
 */
cache.addCache = function (table, key, val) {
    layui.data(table, {
        key: key,
        value: val
    });
}

/**
 * 清空全部缓存
 * @param table 表
 */
cache.cleanCache = function (table) {
    layui.data(table, null)
}

/**
 * 删除缓存
 * @param table 表
 * @param key 键
 */
cache.removeCache = function (table, key) {
    layui.data(table, {
        key: key
        , remove: true
    });
}

/**
 * 得到缓存
 * @param table 表
 * @param key 键 可以为null
 * @returns {*} 所有缓存数据
 */
cache.getCache = function (table, key) {
    if (key == null) {
        return layui.data(table);
    } else {
        return layui.data(table, key);
    }
}

/**
 * 添加到临时缓存
 * @param table 表
 * @param key 键
 * @param val 值
 */
cache.addSessionCache = function (table, key, val) {
    layui.sessionData(table, {
        key: key,
        value: val
    });
}

/**
 * 清空全部临时缓存
 * @param table 表
 */
cache.clearSessionCache = function (table) {
    layui.sessionData(table, null)
}

/**
 * 删除临时缓存
 * @param table 表
 * @param key 键
 */
cache.removeSessionCache = function (table, key) {
    layui.sessionData(table, {
        key: key
        , remove: true
    });
}

/**
 * 得到临时缓存
 * @param table 表
 * @param key 键 可以为null
 * @returns {*} 所有缓存数据
 */
cache.getSessionCache = function (table, key) {
    if (key == null) {
        return layui.sessionData(table);
    } else {
        return layui.sessionData(table, key);
    }
}