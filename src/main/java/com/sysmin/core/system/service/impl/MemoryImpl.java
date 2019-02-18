package com.sysmin.core.system.service.impl;

import com.sysmin.core.system.domain.MemoryDO;
import com.sysmin.core.system.service.api.MemoryApi;
import com.sysmin.global.BaseContinueOut;
import com.sysmin.util.JsonUtil;
import com.sysmin.util.StringUtil;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.StringTokenizer;

/**
 * @author:Li
 * @time: 2018/12/27 20:06
 * @version: 1.0.0
 */
@Service
public class MemoryImpl extends BaseContinueOut implements MemoryApi {

    @Resource
    private SimpMessagingTemplate simpMessagingTemplate;

    /**
     * 行计数器
     */
    private int lines = 0;

    /**
     * 数据对象
     */
    private MemoryDO memory = new MemoryDO();

    @Override
    public void getMemory() {
        super.subscribe("free -s 1");
    }

    @Override
    protected void invoke(String data, int pid, String command) {
        lines++;
        if (lines == 2) {
            StringTokenizer token1 = new StringTokenizer(StringUtil.replaceBlank(data, " "), " ");
            token1.nextToken();
            memory.setMemTotal(Long.valueOf(token1.nextToken()))
                    .setMemUsed(Long.valueOf(token1.nextToken()))
                    .setMemFree(Long.valueOf(token1.nextToken()))
                    .setMemShared(Long.valueOf(token1.nextToken()))
                    .setMemBuffers(Long.valueOf(token1.nextToken()))
                    .setMemCached(Long.valueOf(token1.nextToken()));
        } else if (lines == 3) {
            StringTokenizer token2 = new StringTokenizer(StringUtil.replaceBlank(data, " "), " ");
            token2.nextToken();
            token2.nextToken();
            memory.setMemBeUsed(Long.valueOf(token2.nextToken()))
                    .setMemBeFree(Long.valueOf(token2.nextToken()));
        } else if (lines == 4) {
            StringTokenizer token3 = new StringTokenizer(StringUtil.replaceBlank(data, " "), " ");
            token3.nextToken();
            memory.setSwapTotal(Long.valueOf(token3.nextToken()))
                    .setSwapUsed(Long.valueOf(token3.nextToken()))
                    .setSwapFree(Long.valueOf(token3.nextToken()));
            // 数据输出
            simpMessagingTemplate.convertAndSendToUser("test", "/memory", memory);
            memory.clear();
            lines = 0;
        }
    }

    @Override
    protected void error(String data) {
        System.out.println("error :" + data);
    }

    @Override
    protected void destroy() {
        lines = 0;
        if (MemoryImpl.systemProcess.get("free") != null) {
            systemProcess.get("free").destroy();
        }
        System.out.println("memory: 销毁");
    }
}
