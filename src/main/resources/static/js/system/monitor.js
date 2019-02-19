var layer = layui.layer,
    element = layui.element;

var _sockJS = new SockJS("/sock"),
    _stompClient = Stomp.over(_sockJS),
    _userId = "test";

$(function () {
    new _init();
});

/**
 * 页面元素容器
 * @type {Object}
 * @private
 */
var _monitorElement = new Object();

/**
 * 流量元素容器
 * @type {Array}
 */
_monitorElement.flowDataArr = [];

/**
 * 轮询参数
 * @type {null}
 */
_monitorElement.dataPoll = null;

/**
 * 监听网络流量
 */
_monitorElement.listenFlow = function () {
    _stompClient.subscribe('/user/' + _userId + '/flowtotal', function (data) {
        let dataArr = eval('(' + data.body + ')');
        _view.upFlowView(dataArr.date, dataArr.out);
        _view.downFlowView(dataArr.date, dataArr.in);
    });
};

/**
 * 监听网络流量
 */
_monitorElement.listenMemory = function () {
    _stompClient.subscribe('/user/' + _userId + '/memory', function (data) {
        let dataArr = eval('(' + data.body + ')');
        _data.memoryArr = data.body;
        _view.memoryView(Math.ceil((dataArr.memBeUsed / dataArr.memTotal) * 100), dataArr.memTotal, dataArr.memBeUsed, dataArr.memBeFree);
    });
};

/**
 * 监听io cpu 信息
 */
_monitorElement.listenIoCpu = function () {
    _stompClient.subscribe('/user/' + _userId + '/iocpu', function (data) {
        let dataArr = eval('(' + data.body + ')');
        _view.cpuView(dataArr.cpu.user + dataArr.cpu.system);
        // {"cpu":{"idle":97.98,"iowait":0.0,"nice":0.0,"steal":0.0,"system":1.01,"user":1.01},"io":[{"avgqu":0.0,"avgrq":0.0,"await":0.0,"device":"scd0","r":0.0,"rAwait":0.0,"rMB":0.0,"rrqm":0.0,"svctm":0.0,"util":0.0,"w":0.0,"wAwait":0.0,"wMB":0.0,"wrqm":0.0},{"avgqu":0.0,"avgrq":0.0,"await":0.0,"device":"sda","r":0.0,"rAwait":0.0,"rMB":0.0,"rrqm":0.0,"svctm":0.0,"util":0.0,"w":0.0,"wAwait":0.0,"wMB":0.0,"wrqm":0.0},{"avgqu":0.0,"avgrq":0.0,"await":0.0,"device":"dm-0","r":0.0,"rAwait":0.0,"rMB":0.0,"rrqm":0.0,"svctm":0.0,"util":0.0,"w":0.0,"wAwait":0.0,"wMB":0.0,"wrqm":0.0},{"avgqu":0.0,"avgrq":0.0,"await":0.0,"device":"dm-1","r":0.0,"rAwait":0.0,"rMB":0.0,"rrqm":0.0,"svctm":0.0,"util":0.0,"w":0.0,"wAwait":0.0,"wMB":0.0,"wrqm":0.0}]}
    });
};

/**
 * 初始化方法
 * @private
 */
var _init = function () {
    $.post("/getsysteminfo", {}, function (data) {
        _data.renderSystemName(data.hostName);
        _data.renderIP(data.ip);
        _data.renderLoginUsers(data.userName);
        _data.renderSystemVersion(data.osVersion);
        _data.renderRunningTime(data.upTime);
    });
    if (cache.getCache("config", "systemtype") === 1) {
        // 轮询负载 2000 ms/次
        _monitorElement.dataPoll = window.setInterval(function () {
            $.post("/loadbalance", {}, function (data) {
                _view.loadBalanceView(data[0], data.toString());
            });
        }, 2000);

        // 硬盘
        $.post("/diskinfo", {}, function (dataArr) {
            _data.diskArr = dataArr;
            let len = dataArr.length <= 3 ? dataArr.length : 3;
            for (let i = 0; i < len; i++) {
                $("#summarize").append('<div id="disk' + i + '" class="layui-col-md2" style="height: 300px;"></div>');
                setTimeout(() => {
                    let use = dataArr[i].use.substring(0, dataArr[i].use.length - 1);
                    if (dataArr[i].mounted.length > 7) {
                        _view.diskView('disk' + i, dataArr[i].mounted.substring(0, 7) + '...', use, 100 - use, dataArr[i]);
                    } else {
                        _view.diskView('disk' + i, dataArr[i].mounted, use, 100 - use, dataArr[i]);
                    }
                }, 100)
            }
        });

        $(".layui-card").css("display", "block");
        _view.loadBalanceView(1, '0,0,0');
        _view.cpuView(5);
        _view.memoryView(30, 0, 0, 0);
        // let update = ['1:1', '1:2', '1:3', '1:4', '1:5', '1:6', '1:7', '1:8', '1:9', '1:10', '1:11', '1:12', '1:13'],
        //     upvalue = [128, 79, 121, 56, 32, 18, 170, 150, 180, 160, 120, 128, 200],
        //     downdate = ['1:1', '1:2', '1:3', '1:4', '1:5', '1:6', '1:7', '1:8', '1:9', '1:10', '1:11', '1:12', '1:13'],
        //     downvalue = [30, 47, 56, 80, 62, 120, 110, 105, 75, 65, 79, 56, 120];
        // _view.upFlowView(update, upvalue);
        // _view.downFlowView(downdate, downvalue);

        $.post("/systemallstart", {}, function (data) {
            _stompClient.connect({}, function () {
                _monitorElement.listenMemory();
                _monitorElement.listenFlow();
                _monitorElement.listenIoCpu();
            });
        });
    } else {
        $(".layui-card").remove();
    }
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
 * 渲染硬盘
 * @param id dom的id
 * @param diskName 挂载名称
 * @param used 已使用:number %
 * @param free 为使用:number %
 * @param data 对象信息
 */
_view.diskView = function (id, diskName, used, free, data) {
    _view.disk = echarts.init(document.getElementById(id));
    _view.dickOption = {
        tooltip: {
            show: true,
            formatter: function () {
                return `
                    已使用: ${Math.ceil(data.used / 1024)}MB<br/>
                    未使用: ${Math.ceil(data.available / 1024)}MB<br/>
                    已使用: ${used}%<br/>
                    未使用: ${free}%<br/>
                    挂载点: ${data.mounted}
                `;
            }
        },
        title: {
            text: diskName + '\n已使用 ' + used + '%',
            link: "javascript:_data.diskInfo(" + id + ");",
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
                value: used,
                itemStyle: {
                    normal: {
                        color: {
                            colorStops: [{
                                offset: 0,
                                color: '#FF4777' // 0% 处的颜色
                            }, {
                                offset: 1,
                                color: '#F00056' // 100% 处的颜色
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
                value: free
            }]
        }]
    };
    _view.disk.setOption(_view.dickOption);
};

/**
 * 负载渲染
 * @param use 系统当前负载
 * @param data 系统1,5,15负载
 */
_view.loadBalanceView = function (use, data) {
    _view.loadBalance = echarts.init(document.getElementById('loadBalance'));
    _view.loadBalanceOption = {
        backgroundColor: "#fff",
        tooltip: {
            show: true,
            formatter: function () {
                return '系统负载: ' + use + '%' + '<br/>' +
                    data;
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
 * @param total 内存总量 KB
 * @param used 使用内存 KB
 * @param free 剩余内存 KB
 */
_view.memoryView = function (use, total, used, free) {
    _view.memory = echarts.init(document.getElementById('memory'));
    _view.memoryOption = {
        tooltip: {
            show: true,
            formatter: function () {
                return `最大内存: ${Math.ceil(total / 1024)} MB<br/>
                      使用中:  ${Math.ceil(used / 1024)} MB<br/>
                      空闲中:  ${Math.ceil(free / 1024)} MB<br/>
                    `;
            }
        },
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
        color: ['#3AA1A1'],
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
        color: ['#37A2DA'],
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
    console.log(_data.memoryArr);
    let memArr = eval('(' + _data.memoryArr + ')');
    layer.open({
        skin: 'layui-layer-hei',
        shade: false,
        title: '内存信息',
        content: `
            物理内存总量: ${memArr.memTotal}KB<br/>
            已使用的内存总量: ${memArr.memUsed}KB<br/>
            空闲内存总量: ${memArr.memFree}KB<br/>
            共享内存总量: ${memArr.memShared}KB<br/>
            块设备所占用的缓存: ${memArr.memBuffers}KB<br/>
            普通文件数据所占用的缓存: ${memArr.memCached}KB<br/>
            正在使用的内存总量: ${memArr.memBeUsed}KB<br/>
            真正的可用内存总量: ${memArr.memBeFree}KB<br/>
            交换分区内存总量: ${memArr.swapTotal}KB<br/>
            正在使用的交换分区内存: ${memArr.swapUsed}KB<br/>
            空闲交换分区内存: ${memArr.swapFree}KB<br/>
        `
    });
};

/**
 * 弹出硬盘信息
 */
_data.diskInfo = function (dataDom) {
    let diskData = _data.diskArr[$(dataDom).attr('id').substring(4, 5)];
    let use = diskData.use.substring(0, diskData.use.length - 1);
    layer.open({
        skin: 'layui-layer-hei',
        shade: false,
        title: '硬盘信息',
        content: `
            文件系统:  ${diskData.fileSystem}<br/>
            1K块总量: ${diskData.blocks}<br/>
            已使用: ${Math.ceil(diskData.used / 1024)}MB<br/>
            未使用: ${Math.ceil(diskData.available / 1024)}MB<br/>
            已使用: ${diskData.use}<br/>
            未使用: ${100 - use}%<br/>
            挂载点: ${diskData.mounted}
        `
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
        setTimeout(() => {
            location.reload();
        }, 3000);
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
        setTimeout(() => {
            location.reload();
        }, 3000);
    });
};

/**
 * 渲染上传流量粒度
 */
_data.setUpGranularity = function () {
    layer.prompt({
        value: '1',
        title: '设置粒度  默认1s',
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
        value: '1',
        title: '设置粒度  默认1s',
    }, function (value, index, elem) {
        alert(value);
        layer.close(index);
    });
};