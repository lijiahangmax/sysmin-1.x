var layer = layui.layer,
    element = layui.element;

$(function () {
    new _init();
});

/**
 * 初始化方法
 * @private
 */
var _init = function () {
    _view.loadBalanceView(15);
    _view.cpuView(30);
    _view.memoryView(30);
    let update = ['1:1', '1:2', '1:3', '1:4', '1:5', '1:6', '1:7', '1:8', '1:9', '1:10', '1:11', '1:12', '1:13'],
        upvalue = [128, 79, 121, 56, 32, 18, 170, 150, 180, 160, 120, 128, 200],
        downdate = ['1:1', '1:2', '1:3', '1:4', '1:5', '1:6', '1:7', '1:8', '1:9', '1:10', '1:11', '1:12', '1:13'],
        downvalue = [30, 47, 56, 80, 62, 120, 110, 105, 75, 65, 79, 56, 120];
    _view.upFlowView(update, upvalue);
    _view.downFlowView(downdate, downvalue);
    $.post("/getsysteminfo", {}, function (data) {
        _data.renderSystemName(data.hostName);
        _data.renderIP(data.ip);
        _data.renderLoginUsers(data.userName);
        _data.renderSystemVersion(data.osVersion);
        _data.renderRunningTime(data.upTime);
    });
};

/**
 * 视图渲染对象
 * @private
 */
var _view = [],
    /**
     * 数据渲染对象
     * @private
     */
    _data = [];

/**
 * 负载渲染
 * @param use 系统负载
 */
_view.loadBalanceView = function (use) {
    _view.loadBalance = echarts.init(document.getElementById('loadBalance'));
    _view.loadBalanceOption = {
        backgroundColor: "#fff",
        tooltip: {
            show: true,
            formatter: function () {
                return '系统负载: ' + use + "%";
            }
        },
        series: [{
            type: 'gauge',
            radius: '60%',
            min: 0,
            max: 100,
            splitNumber: 10, //刻度数量
            startAngle: 220,
            endAngle: -40,
            axisLine: {
                show: false,
                lineStyle: {
                    width: 1,
                    color: [
                        [1, 'rgba(0,0,0,0)']
                    ]
                }
            },
            axisLabel: {
                show: true,
                color: '#3B53A2',
                distance: -30,
                fontSize: 11
            },
            axisTick: {
                show: true,
                lineStyle: {
                    color: '#3B53A2',
                    width: 1
                },
                length: -2
            },
            splitLine: {
                show: true,
                length: -4,
                lineStyle: {
                    color: '#3B53A2',
                    width: 1
                }
            },
            detail: {
                show: false
            },
            pointer: {
                show: false
            }
        },
            {
                type: 'gauge',
                radius: '55%',
                min: 0,
                max: 100,
                center: ['50%', '50%'],
                splitNumber: 0,
                startAngle: 220,
                endAngle: -40,
                axisLine: {
                    show: true,
                    lineStyle: {
                        width: 13,
                        color: [[
                            use / 100, new echarts.graphic.LinearGradient(
                                0, 0, 1, 0, [{
                                    offset: 0,
                                    color: '#02acb0'
                                },
                                    {
                                        offset: 1,
                                        color: '#01AAED'
                                    }
                                ]
                            )],
                            [
                                1, '#DAE1F4'
                            ]
                        ]
                    }
                },
                splitLine: {
                    show: false,
                },
                axisLabel: {
                    show: false
                },
                axisTick: {
                    show: false
                },
                pointer: {
                    show: true,
                    width: "8%",
                    length: '20%',
                },
                itemStyle: {
                    normal: {
                        color: '#01AAED'
                    }
                },
                detail: {
                    show: false,
                    color: '#ddd',
                    textStyle: {
                        fontSize: 12
                    }
                },
                data: [{
                    name: "系统负载",
                    value: use
                }]
            }
        ]
    };
    _view.loadBalance.setOption(_view.loadBalanceOption);
};

/**
 * cpu使用渲染
 * @param use cpu使用率
 */
_view.cpuView = function (use) {
    _view.cpu = echarts.init(document.getElementById('cpu'));
    _view.cpuOption = {
        backgroundColor: "#fff",
        tooltip: {
            show: true,
            formatter: function () {
                return 'CPU使用率: ' + use + "%";
            }
        },
        series: [{
            name: '刻度',
            type: 'gauge',
            radius: '60%',
            min: 0,
            max: 100,
            splitNumber: 10, //刻度数量
            startAngle: 220,
            endAngle: -40,
            axisLine: {
                show: false,
                lineStyle: {
                    width: 1,
                    color: [
                        [1, 'rgba(0,0,0,0)']
                    ]
                }
            },
            axisLabel: {
                show: true,
                color: '#3B53A2',
                distance: -30,
                fontSize: 11
            },
            axisTick: {
                show: true,
                lineStyle: {
                    color: '#3B53A2',
                    width: 1
                },
                length: -2
            },
            splitLine: {
                show: true,
                length: -4,
                lineStyle: {
                    color: '#3B53A2',
                    width: 1
                }
            },
            detail: {
                show: false
            },
            pointer: {
                show: false
            }
        },
            {
                type: 'gauge',
                radius: '55%',
                min: 0,
                max: 100,
                center: ['50%', '50%'],
                splitNumber: 0,
                startAngle: 220,
                endAngle: -40,
                axisLine: {
                    show: true,
                    lineStyle: {
                        width: 13,
                        color: [
                            [
                                use / 100, new echarts.graphic.LinearGradient(
                                0, 0, 1, 0, [{
                                    offset: 0,
                                    color: '#02acb0'
                                },
                                    {
                                        offset: 1,
                                        color: '#0890a7'
                                    }
                                ]
                            )
                            ],
                            [
                                1, '#DAE1F4'
                            ]
                        ]
                    }
                },
                splitLine: {
                    show: false,
                },
                axisLabel: {
                    show: false
                },
                axisTick: {
                    show: false
                },
                pointer: {
                    show: true,
                    width: "8%",
                    length: '20%',
                },
                itemStyle: {
                    normal: {
                        color: '#0890a7'
                    }
                },
                detail: {
                    show: false,
                    color: '#ddd',
                    textStyle: {
                        fontSize: 12
                    }
                },
                data: [{
                    name: "CPU",
                    value: use
                }]
            }
        ]
    };
    _view.cpu.setOption(_view.cpuOption);
};

/**
 * 内存使用渲染
 * @param use 内存使用率
 */
_view.memoryView = function (use) {
    _view.memory = echarts.init(document.getElementById('memory'));
    _view.memoryOption = {
        title: {
            text: '内存使用率\n' + use + '%',
            link: "javascript:_data.memoryInfo()",
            target: "self",
            x: 'center',
            y: 'center',
            textStyle: {
                fontWeight: 'normal',
                color: '#0580F2',
                fontSize: '18'
            }
        },
        color: ['rgba(176, 212, 251, 1)'],
        series: [{
            name: 'Line 1',
            type: 'pie',
            clockWise: true,
            radius: ['50%', '66%'],
            itemStyle: {
                normal: {
                    label: {
                        show: false
                    },
                    labelLine: {
                        show: false
                    }
                }
            },
            hoverAnimation: false,
            data: [{
                value: use,
                itemStyle: {
                    normal: {
                        color: {
                            colorStops: [{
                                offset: 0,
                                color: '#00CEFC' // 0% 处的颜色
                            }, {
                                offset: 1,
                                color: '#367BEC' // 100% 处的颜色
                            }]
                        },
                        label: {
                            show: false
                        },
                        labelLine: {
                            show: false
                        }
                    }
                }
            }, {
                value: 100 - use
            }]
        }]
    };
    _view.memory.setOption(_view.memoryOption);
};

/**
 * 渲染上传流量
 * @param date 时间数组
 * @param value 数据数组
 */
_view.upFlowView = function (date, value) {
    _view.upFlow = echarts.init(document.getElementById('upFlow'));
    _view.upOption = {
        color: ['#3aa1a1'],
        tooltip: {
            trigger: 'axis',
        },
        toolbox: {
            feature: {
                saveAsImage: {}
            }
        },
        grid: {
            left: '2%',
            right: '3%',
            bottom: '2%',
            top: '10%',
            containLabel: true
        },
        xAxis: [{
            type: 'category',
            boundaryGap: false,
            data: date
        }],
        yAxis: [{
            type: 'value'
        }],
        series: [{
            name: 'Kbps',
            type: 'line',
            areaStyle: {normal: {}},
            data: value
        }]
    };
    _view.upFlow.setOption(_view.upOption);
};

/**
 * 渲染下载流量
 * @param date 时间数组
 * @param value 数据数组
 */
_view.downFlowView = function (date, value) {
    _view.downFlow = echarts.init(document.getElementById('downFlow'));
    _view.downOption = {
        color: ['#37a2da'],
        tooltip: {
            trigger: 'axis',
        },
        toolbox: {
            feature: {
                saveAsImage: {}
            }
        },
        grid: {
            left: '2%',
            right: '3%',
            bottom: '2%',
            top: '10%',
            containLabel: true
        },
        xAxis: [{
            type: 'category',
            boundaryGap: false,
            data: date
        }],
        yAxis: [{
            type: 'value'
        }],
        series: [{
            name: 'Kbps',
            type: 'line',
            areaStyle: {normal: {}},
            data: value
        }]
    };
    _view.downFlow.setOption(_view.downOption);
};

/**
 * 弹出内存信息
 */
_data.memoryInfo = function () {
    layer.open({
        skin: 'layui-layer-hei',
        shade: false,
        title: '内存信息',
        content: 123
    });
};

/**
 * 渲染实例名称
 * @param name 实例名称
 */
_data.renderSystemName = function (name) {
    $("#systemName").text(name);
};

/**
 * 渲染ip
 * @param ip ip信息
 */
_data.renderIP = function (ip) {
    $("#ip").text(ip);
};

/**
 * 渲染当前用户
 * @param name 当前用户
 */
_data.renderLoginUsers = function (name) {
    $("#loginUsers").text(name);
};

/**
 * 渲染系统版本
 * @param version
 */
_data.renderSystemVersion = function (version) {
    $("#systemVersion").text(version);
};

/**
 * 渲染运行时间
 * @param time 运行时间
 */
_data.renderRunningTime = function (time) {
    $("#runningTime").text(time);
};

/**
 * 关机
 * @param time 多长时间执行
 */
_data.shutdown = function (time) {
    layer.confirm('确定要关机吗？', {
        btn: ['确定', '取消']
    }, function (index, layero) {
        layer.close(index);
        layer.msg('正在关机', {
            icon: 16,
            // time: 60 * 1000,
            shade: 0.3
        });
        $.post("/halt");
        location.reload();
    });
};

/**
 * 重启
 * @param time 多长时间执行
 */
_data.reboot = function (time) {
    layer.confirm('确定要重启吗？', {
        btn: ['确定', '取消']
    }, function (index, layero) {
        layer.close(index);
        layer.msg('正在重启', {
            icon: 16,
            // time: 60 * 1000,
            shade: 0.3
        });
        $.post("/reboot");
        location.reload();
    });
};

/**
 * 渲染上传流量粒度
 */
_data.setUpGranularity = function () {
    layer.prompt({
        value: '10',
        title: '设置粒度  默认10s',
    }, function (value, index, elem) {
        alert(value);
        layer.close(index);
    });
};

/**
 * 渲染下载流量粒度
 */
_data.setDownGranularity = function () {
    layer.prompt({
        value: '10',
        title: '设置粒度  默认10s',
    }, function (value, index, elem) {
        alert(value);
        layer.close(index);
    });
};