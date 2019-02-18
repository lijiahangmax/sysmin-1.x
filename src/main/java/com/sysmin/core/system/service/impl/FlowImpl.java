package com.sysmin.core.system.service.impl;

import com.sysmin.core.system.domain.FlowDO;
import com.sysmin.core.system.service.api.FlowApi;
import com.sysmin.global.BaseContinueOut;
import com.sysmin.util.BashUtil;
import com.sysmin.util.JsonUtil;
import com.sysmin.util.StringUtil;
import org.springframework.context.annotation.Scope;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * @author:Li
 * @time: 2018/12/27 14:52
 * @version: 1.0.0
 */
@Service
@Scope("prototype")
public class FlowImpl extends BaseContinueOut implements FlowApi {

    @Resource
    private SimpMessagingTemplate simpMessagingTemplate;

    /**
     * 行计数器
     */
    private int lines = 0;

    /**
     * 数据集
     */
    private Map<String, Object> meta = new HashMap<>(2);

    /**
     * 结果数据集
     */
    private Map<String, Map<String, Object>> res = new HashMap<>(13);


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
            list.forEach(s -> {
                HashMap<String, Object> map = new HashMap<>();
                map.put("date", new ArrayList<String>());
                map.put("in", new ArrayList<Double>());
                map.put("out", new ArrayList<Double>());
                res.put(s, map);
            });
        } else if (lines > 2) {
            List<FlowDO> list = new ArrayList<>();
            StringTokenizer token = new StringTokenizer(StringUtil.replaceBlank(data, " "));
            while (token.hasMoreTokens()) {
                list.add(new FlowDO(Double.valueOf(token.nextToken()), Double.valueOf(token.nextToken())));
            }
            meta.put("data", list);
            List<String> typeList = ((List<String>) meta.get("type"));
            for (int i = 0; i < typeList.size(); i++) {
                FlowDO flow = list.get(i);
                Map<String, Object> resMap = res.get(typeList.get(i));
                ArrayList<String> dateList = (ArrayList<String>) resMap.get("date");
                ArrayList<Double> inList = (ArrayList<Double>) resMap.get("in");
                ArrayList<Double> outList = (ArrayList<Double>) resMap.get("out");
                dateList.add(flow.getDate());
                inList.add(flow.getIn());
                outList.add(flow.getOut());
                resMap.put("date", cutList(dateList, 30));
                resMap.put("in", cutList(inList, 30));
                resMap.put("out", cutList(outList, 30));
                res.put(typeList.get(i), resMap);
            }
            simpMessagingTemplate.convertAndSendToUser("test", "/flowtotal", res.get("Total"));
            simpMessagingTemplate.convertAndSendToUser("test", "/flowall", res);
        }
    }

    /**
     * 保留集合的后几位
     *
     * @param list 集合
     * @param len  保留几位
     * @return 新集合
     */
    public List cutList(List list, int len) {
        if (len < list.size()) {
            int size = list.size();
            for (int i = 0; i < size - len; i++) {
                list.remove(0);
            }
        }
        return list;
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
        lines = 0;
        if (FlowImpl.systemProcess.get("ifstat") != null) {
            systemProcess.get("ifstst").destroy();
        }
        System.out.println("flow: 销毁");
    }

}
