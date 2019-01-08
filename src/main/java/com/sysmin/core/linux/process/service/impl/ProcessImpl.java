package com.sysmin.core.linux.process.service.impl;

import com.sysmin.core.linux.process.domain.ProcessDO;
import com.sysmin.core.linux.process.enums.KillType;
import com.sysmin.core.linux.process.service.api.ProcessApi;
import com.sysmin.util.BashUtil;
import com.sysmin.util.StringUtil;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * @author:Li
 * @time: 2018/12/21 14:30
 * @version: 1.0.0
 */
@Service
public class ProcessImpl implements ProcessApi {

    @Override
    public List<ProcessDO> getProcess() {
        return listProcess("ps -aux");
    }

    @Override
    public List<ProcessDO> getProcess(String process) {
        return listProcess(new String[]{"/bin/sh", "-c", "ps -aux | grep " + process});
    }

    /**
     * 执行ps 命令将进程封装到实体类
     *
     * @param command
     * @return
     */
    private List<ProcessDO> listProcess(Object command) {
        List<ProcessDO> list = new ArrayList<>();
        String lines = BashUtil.exec(command);
        String[] line = lines.split("\n");
        for (int i = 0; i < line.length; i++) {
            String data = StringUtil.replaceBlank(line[i], " ");
            ProcessDO pro = new ProcessDO();
            StringTokenizer token = new StringTokenizer(data, " ");
            String uid = token.nextToken();
            if ("USER".equals(uid)) {
                continue;
            }
            pro.setUser(uid)
                    .setPid(Integer.valueOf(token.nextToken()))
                    .setCpu(Double.valueOf(token.nextToken()))
                    .setMem(Double.valueOf(token.nextToken()))
                    .setVsc(Integer.valueOf(token.nextToken()))
                    .setRss(Integer.valueOf(token.nextToken()))
                    .setTime(token.nextToken())
                    .setTty(token.nextToken())
                    .setStat(token.nextToken())
                    .setStart(token.nextToken());
            int tokens = token.countTokens();
            if (tokens > 1) {
                StringBuilder sb = new StringBuilder();
                for (int j = 0; j < tokens; j++) {
                    sb.append(token.nextToken() + " ");
                }
                pro.setCommand(sb.toString());
            } else {
                pro.setCommand(token.nextToken());
            }
            list.add(pro);
        }
        return list;
    }

    @Override
    public void killProcess(int pid) {
        kill(pid, 9);
    }

    @Override
    public void killProcess(int pid, KillType sign) {
        kill(pid, sign.getType());
    }

    @Override
    public void stopProcess(int pid) {
        kill(pid, 19);
    }

    @Override
    public void startProcess(int pid) {
        kill(pid, 18);
    }

    /**
     * 执行kill命令
     *
     * @param pid  pid
     * @param sign kill类型
     */
    private void kill(int pid, int sign) {
        String status = BashUtil.exec("kill -" + sign + " " + pid);
    }

}
