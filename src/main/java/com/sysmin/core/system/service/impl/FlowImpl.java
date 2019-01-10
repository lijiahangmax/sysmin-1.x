package com.sysmin.core.system.service.impl;

import com.sysmin.core.system.domain.FlowDO;
import com.sysmin.core.system.service.api.FlowApi;
import com.sysmin.global.BaseContinueOut;
import com.sysmin.util.BashUtil;
import com.sysmin.util.JsonUtil;
import com.sysmin.util.StringUtil;

import java.util.*;

/**
 * @author:Li
 * @time: 2018/12/27 14:52
 * @version: 1.0.0
 */
public class FlowImpl extends BaseContinueOut implements FlowApi {

    /**
     * 行计数器
     */
    private int lines = 0;

    /**
     * 数据集
     */
    private Map<String, Object> meta = new HashMap<>(2);

    @Override
    public void subscribe() {
        super.subscribe(BashUtil.toLinuxCommand("ifstat -lST"));
    }

    @Override
    public void subscribe(String name) {
        super.subscribe(BashUtil.toLinuxCommand("ifstat -Si " + name));
    }

    @Override
    protected void invoke(String data, int pid, String command) {
        lines++;
        if (lines == 1) {
            List<String> list = new ArrayList<>();
            StringTokenizer token = new StringTokenizer(StringUtil.replaceLine(StringUtil.replaceBlank(data, " ")), " ");
            while (token.hasMoreTokens()) {
                list.add(token.nextToken());
            }
            meta.put("type", list);
        } else if (lines > 2) {
            List<FlowDO> list = new ArrayList<>();
            StringTokenizer token = new StringTokenizer(StringUtil.replaceBlank(data, " "));
            while (token.hasMoreTokens()) {
                list.add(new FlowDO(Double.valueOf(token.nextToken()), Double.valueOf(token.nextToken())));
            }
            meta.put("data", list);
            String result = JsonUtil.objToJson(meta);
            // 数据输出
            System.out.println(result);
        }
    }

    @Override
    protected void error(String data) {
        if (data.contains("command not found")) {
            System.out.println("未安装 ifstat");
        } else {
            System.out.println("error: " + data);
        }
    }

    @Override
    protected void destroy() {
        System.out.println("destroy: 销毁");
    }

}
