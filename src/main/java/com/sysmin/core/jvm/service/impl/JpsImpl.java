package com.sysmin.core.jvm.service.impl;

import com.sysmin.core.jvm.domain.JpsDO;
import com.sysmin.core.jvm.enums.JpsType;
import com.sysmin.core.jvm.service.api.JpsApi;
import com.sysmin.util.BashUtil;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

/**
 * @author:Li
 * @time: 2018/12/28 16:36
 * @version: 1.0.0
 */
@Service
public class JpsImpl implements JpsApi {

    @Override
    public List<JpsDO> jps(JpsType type) {
        String command = type.getType();
        if (type.getCType() == 0) {
            return getInfo(command);
        } else if (type.getCType() == 1) {
            return getLongInfo(command);
        }
        return null;
    }

    /**
     * 获得长参数信息
     *
     * @param command
     * @return
     */
    private List<JpsDO> getLongInfo(String command) {
        return getJpsInfo(command, "sun.tools.jps.Jps", "sun.tools.jstat.Jstat", "org.jetbrains.jps.cmdline.Launcher");
    }

    /**
     * 获得短参数信息
     *
     * @param command
     * @return
     */
    private List<JpsDO> getInfo(String command) {
        return getJpsInfo(command, "Jps", "Jstat", "Launcher");
    }

    /**
     * 获得jps信息
     *
     * @param command 命令
     * @param lose    忽略的进程 (Jps)
     * @return
     */
    private List<JpsDO> getJpsInfo(String command, String... lose) {
        List<JpsDO> list = new ArrayList<>();
        String data = BashUtil.exec(command);
        StringTokenizer token = new StringTokenizer(data, "\n");
        while (token.hasMoreTokens()) {
            String[] s = token.nextToken().split(" ");
            JpsDO jps = new JpsDO();
            if (!"".equals(s[1])) {
                if (Arrays.binarySearch(lose, s[1]) < 0) {
                    jps.setPid(Integer.valueOf(s[0])).setName(s[1]);
                } else {
                    continue;
                }
            } else {
                jps.setPid(Integer.valueOf(s[0])).setName("null");
            }
            String[] flags = new String[s.length - 2];
            System.arraycopy(s, 2, flags, 0, s.length - 2);
            for (int i = flags.length - 1; i > 0; i--) {
                if (!"-".equals(String.valueOf(flags[i].charAt(0)))) {
                    flags[i - 1] = flags[i - 1] + " " + flags[i];
                    flags[i] = "";
                }
            }
            jps.setFlags(flags);
            list.add(jps);
        }
        return list;
    }

}
