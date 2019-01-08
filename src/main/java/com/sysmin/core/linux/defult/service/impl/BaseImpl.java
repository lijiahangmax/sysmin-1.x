package com.sysmin.core.linux.defult.service.impl;

import com.sysmin.core.linux.defult.domain.LastLogDO;
import com.sysmin.core.linux.defult.service.api.BaseApi;
import com.sysmin.util.BashUtil;
import com.sysmin.util.StringUtil;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

/**
 * @author:Li
 * @time: 2018/12/25 13:40
 * @version: 1.0.0
 */
@Service
public class BaseImpl implements BaseApi {

    @Override
    public int chmod(String file, int mod) throws FileNotFoundException {
        String status = (BashUtil.exec("chmod " + mod + " " + file));
        if (!new File(file).exists()) {
            return "".equals(status) ? 1 : 0;
        } else {
            throw new FileNotFoundException(file + " not found");
        }
    }

    @Override
    public ArrayList<Integer> pidOf(String name) {
        String[] data = StringUtil.replaceLine(BashUtil.exec("pidof " + name)).split(" ");
        ArrayList<Integer> list = new ArrayList<>();
        if (data.length >= 1) {
            for (String d : data) {
                list.add(Integer.valueOf(d));
            }
        }
        return list;
    }

    @Override
    public ArrayList<String> ping(String host) {
        return ping(host, 5);
    }

    @Override
    public ArrayList<String> ping(String host, int time) {
        List<String> list = Arrays.asList(BashUtil.exec("ping -c " + time + " " + host).split("\n"));
        if (list.size() == 1 && StringUtil.checkEmpty(list.get(0))) {
            return new ArrayList<>();
        }
        return new ArrayList<>(list);
    }

    @Override
    public ArrayList<String> find(String file) {
        return find(file, "/");
    }

    @Override
    public ArrayList<String> find(String file, String dir) {
        ArrayList<String> list = new ArrayList<>();
        File findDir = new File(dir);
        if (findDir.exists() || findDir.isDirectory()) {
            list.addAll(Arrays.asList(BashUtil.exec("find " + dir + " -name " + file, true).split("\n")));
        }
        return list;
    }

    @Override
    public void reboot() {
        BashUtil.exec("reboot");
    }

    @Override
    public void shutDown(String time, boolean type) {
        BashUtil.exec(BashUtil.toLinuxCommand("shutdown -" + (type ? "h" : "r") + " " + time + " &"));
    }

    @Override
    public void shutDownCancel() {
        BashUtil.exec("shutdown -c");
    }

    @Override
    public void halt() {
        BashUtil.exec("halt");
    }

    @Override
    public String[] who() {
        String data = StringUtil.replaceBlank(BashUtil.exec("who -H").split("\n")[1], " ");
        StringTokenizer token = new StringTokenizer(data, " ");
        return new String[]{token.nextToken(), token.nextToken(), token.nextToken() + " " + token.nextToken(), token.nextToken()};
    }

    @Override
    public ArrayList<String> users() {
        String[] data = StringUtil.replaceLine(BashUtil.exec("users")).split(" ");
        ArrayList<String> list = new ArrayList<>();
        if (data.length >= 1) {
            list.addAll(Arrays.asList(data));
        }
        return list;
    }

    @Override
    public LastLogDO getLastLog(String user) {
        String[] line = StringUtil.replaceBlank(BashUtil.exec("lastlog -u " + user), " ").split("\n");
        if (line.length == 1) {
            return new LastLogDO("", "", "", "");
        }
        return supLastLog(line[1]);
    }

    @Override
    public ArrayList<LastLogDO> getAllLastLog() {
        String[] line = StringUtil.replaceBlank(BashUtil.exec("lastlog"), " ").split("\n");
        ArrayList<LastLogDO> list = new ArrayList<>();
        for (int i = 1; i < line.length; i++) {
            list.add(supLastLog(line[i]));
        }
        return list;
    }

    /**
     * 将数据转化为LastLogDO
     *
     * @param s
     * @return
     */
    private LastLogDO supLastLog(String s) {
        LastLogDO logDO = new LastLogDO("", "", "", "");
        StringTokenizer token = new StringTokenizer(s);
        if (token.countTokens() == 2) {
            logDO.setUsername(token.nextToken()).setLastTime(token.nextToken());
        } else {
            logDO.setUsername(token.nextToken());
            String tmp = token.nextToken();
            if (tmp.split(".").length == 0) {
                logDO.setPort(tmp);
                tmp = token.nextToken();
                if (new StringTokenizer(tmp, ".").countTokens() == 4) {
                    StringBuilder sb = new StringBuilder();
                    while (token.hasMoreTokens()) {
                        sb.append(token.nextToken() + " ");
                    }
                    logDO.setFrom(tmp).setLastTime(sb.toString());
                } else {
                    StringBuilder sb = new StringBuilder();
                    while (token.hasMoreTokens()) {
                        sb.append(token.nextToken() + " ");
                    }
                    logDO.setLastTime(tmp + " " + sb.toString());
                }
            } else {
                StringBuilder sb = new StringBuilder();
                while (token.hasMoreTokens()) {
                    sb.append(token.nextToken() + " ");
                }
                logDO.setFrom(token.nextToken()).setLastTime(sb.toString());
            }
        }
        return logDO;
    }

}
