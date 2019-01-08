package com.sysmin.core.jvm.service.impl;

import com.sysmin.core.jvm.enums.JinfoType;
import com.sysmin.core.jvm.service.api.JinfoApi;
import com.sysmin.util.BashUtil;
import com.sysmin.util.StringUtil;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author:Li
 * @time: 2019/1/1 9:02
 * @version: 1.0.0
 */
@Service
public class JinfoImpl implements JinfoApi {

    @Override
    public Map<String, List> getInfo(int id, JinfoType type) {
        String command = type.getType(id);
        if (type.getType() == 0) {
            return getFlagsInfo(command);
        }
        return null;
    }

    /**
     * 获得flags信息
     *
     * @param command 命令
     * @return
     */
    private Map<String, List> getFlagsInfo(String command) {
        Map<String, List> map = new HashMap<>(2);
        ArrayList<String> list = new ArrayList<>();
        String data = BashUtil.exec(command, true);
        if (data.contains("successfully")) {
            String[] lines = data.split("\n");
            for (int i = 0; i < lines.length; i++) {
                if (i == 4) {
                    map.put("flags", paramToList(lines[4].split("flags:")[1]));
                } else if (i == 5) {
                    map.put("line", paramToList(lines[5].split("line:")[1]));
                }
            }
        }
        return map;
    }

    /**
     * 将参数转化为list
     *
     * @param data
     * @return
     */
    private List<String> paramToList(String data) {
        List<String> list = new ArrayList<>();
        StringTokenizer token = new StringTokenizer(StringUtil.replaceBlank(data, " "), " ");
        while (token.hasMoreTokens()) {
            list.add(token.nextToken());
        }
        return list;
    }

}
