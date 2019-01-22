package com.sysmin.core.system;

import com.sysmin.global.GlobalConfig;
import com.sysmin.util.BashUtil;
import com.sysmin.util.StringUtil;

import java.net.InetAddress;
import java.util.StringTokenizer;
import java.util.regex.Pattern;

/**
 * @author:Li
 * @time: 2018/12/28 14:27
 * @version: 1.0.0
 */
public class SystemInfo {

    static {
        GlobalConfig.setValue("OSSystem", System.getProperty("os.name"));
        GlobalConfig.setValue("OSVersion", getOSVersion());
        if (((String) GlobalConfig.getValue("OSSystem")).contains("Linux")) {
            GlobalConfig.setValue("cpuCoreNumber", Integer.valueOf(StringUtil.replaceBlank((BashUtil.exec(BashUtil.toLinuxCommand("cat /proc/cpuinfo| grep \"cpu cores\"| uniq"), true)), " ").split(":")[1].trim()));
            GlobalConfig.setValue("pcpuNumber", Integer.valueOf(StringUtil.replaceBlank((BashUtil.exec(BashUtil.toLinuxCommand("cat /proc/cpuinfo| grep \"physical id\"| sort| uniq| wc -l"), true)))));
            GlobalConfig.setValue("lcpuNumber", Integer.valueOf(StringUtil.replaceBlank((BashUtil.exec(BashUtil.toLinuxCommand("cat /proc/cpuinfo| grep \"processor\"| wc -l"), true)))));
            GlobalConfig.setValue("cpuInfo", StringUtil.replaceLine(StringUtil.replaceBlank((BashUtil.exec(BashUtil.toLinuxCommand("cat /proc/cpuinfo | grep name"), true)), " ").split(":")[1].substring(1)));
        } else if (((String) GlobalConfig.getValue("OSSystem")).contains("Windows")) {
            GlobalConfig.setValue("cpuCoreNumber", Integer.valueOf(StringUtil.replaceBlank(Pattern.compile("[^0-9]").matcher((BashUtil.exec("wmic cpu get NumberOfCores", true))).replaceAll(""))));
            StringTokenizer tokenizer1 = new StringTokenizer((String) BashUtil.exec("wmic cpu get Name", true), "\n");
            GlobalConfig.setValue("pcpuNumber", tokenizer1.countTokens() - 1);
            tokenizer1.nextToken();
            GlobalConfig.setValue("lcpuNumber", Integer.valueOf(StringUtil.replaceBlank(Pattern.compile("[^0-9]").matcher((BashUtil.exec("wmic cpu get NumberOfLogicalProcessors", true))).replaceAll(""))));
            GlobalConfig.setValue("cpuInfo", tokenizer1.nextToken());
        }
    }

    /**
     * 物理cpu个数
     */
    public static final int P_CPU_NUMBER = (int) GlobalConfig.getValue("pcpuNumber");

    /**
     * 逻辑cpu个数
     */
    public static final int L_CPU_NUMBER = (int) GlobalConfig.getValue("lcpuNumber");

    /**
     * cpu核心个数
     */
    public static final int CPU_CORE_NUMBER = (int) GlobalConfig.getValue("cpuCoreNumber");

    /**
     * cpu型号
     */
    public static final String CPU_INFO = String.valueOf(GlobalConfig.getValue("cpuInfo"));

    /**
     * 获得系统版本信息
     *
     * @return 系统版本
     */
    private static int getOSVersion() {
        String osSystem = (String) GlobalConfig.getValue("OSSystem");
        try {
            if (osSystem.contains("Windows")) {
                // ip
                GlobalConfig.setValue("ip", InetAddress.getLocalHost().getHostAddress().toString());
                return Integer.valueOf(osSystem.split(" ")[1]);
            } else if (osSystem.contains("Linux")) {
                // 系统版本
                String os = StringUtil.replaceLine(BashUtil.exec("cat /proc/version"));
                // ip
                GlobalConfig.setValue("ip", StringUtil.replaceLine(BashUtil.exec("hostname -I")));
                return Integer.valueOf(os.substring(os.indexOf("el") + 2, os.indexOf("el") + 3));
            } else {
                return 0;
            }
        } catch (Exception e) {
            return 0;
        }
    }

}
