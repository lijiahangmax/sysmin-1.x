package com.sysmin.controller.jvm;

import com.sysmin.core.jvm.JavaProperties;
import com.sysmin.core.jvm.domain.JstackDO;
import com.sysmin.core.jvm.enums.JmapType;
import com.sysmin.core.jvm.enums.JstackType;
import com.sysmin.core.jvm.enums.JstatType;
import com.sysmin.core.jvm.service.impl.JinfoImpl;
import com.sysmin.core.jvm.service.impl.JmapImpl;
import com.sysmin.core.jvm.service.impl.JstackImpl;
import com.sysmin.core.jvm.service.impl.JstatImpl;
import com.sysmin.core.log.Log;
import com.sysmin.core.log.LogType;
import com.sysmin.global.LayuiTableVO;
import com.sysmin.util.FileUtil;
import com.sysmin.util.StringUtil;
import org.apache.commons.io.FileUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

/**
 * @author:Li
 * @time: 2019/1/6 20:03
 * @version: 1.0.0
 */
@Controller
public class JvmHandlerController {

    @Resource
    private JstatImpl jstatImpl;

    @Resource
    private JstackImpl jstackImpl;

    @Resource
    private JinfoImpl jinfoImpl;

    @Resource
    private JmapImpl jmapImpl;

    @RequestMapping("/stopallmonitor")
    @ResponseBody
    public int stopAllMonitor() {
        Log.getLog("test", "stopallmonitor", "all");
        int size = JstatImpl.jvmProcess.size();
        JstatImpl.jvmProcess.forEach((p) -> {
            p.destroy();
        });
        JstatImpl.jvmProcess.clear();
        return size;
    }

    @RequestMapping("/monitorallopt")
    @ResponseBody
    public int monitorAllOpt(String pids) {
        try {
            StringTokenizer token = new StringTokenizer(pids, ",");
            Log.getLog("test", "startallmonitor", "all");

            while (token.hasMoreTokens()) {
                int pid = Integer.valueOf(token.nextToken());
                new Thread(() -> {
                    jstatImpl.jstat(pid, JstatType.CLASS);
                }).start();
                new Thread(() -> {
                    jstatImpl.jstat(pid, JstatType.GC);
                }).start();
                new Thread(() -> {
                    jstatImpl.jstat(pid, JstatType.GCUTIL);
                }).start();
                new Thread(() -> {
                    jstatImpl.jstat(pid, JstatType.COMPILER);
                }).start();
                new Thread(() -> {
                    jstatImpl.jstat(pid, JstatType.PRINTCOMPILATION);
                }).start();
            }
            return 1;
        } catch (Exception e) {
            return 2;
        }
    }

    @RequestMapping("/monitorallstack")
    @ResponseBody
    public Map<Integer, JstackDO> monitorAllStack(String pids) {
        Map<Integer, JstackDO> map = new HashMap<>();
        StringTokenizer token = new StringTokenizer(pids, ",");
        while (token.hasMoreTokens()) {
            int pid = Integer.valueOf(token.nextToken());
            JstackDO jstack = jstackImpl.jstack(pid, JstackType.DEFAULT);
            map.put(pid, jstack);
        }
        return map;
    }

    @RequestMapping("/monitorsize")
    @ResponseBody
    public int getMonitorSize() {
        return JstatImpl.jvmProcess.size();
    }

    @RequestMapping("/createthreadsnap")
    @ResponseBody
    public String createThreadSnap(int pid) {
        Log.getLog("test", "createThreadSnap " + pid, "all");
        return jstackImpl.threadSnap(pid, JstackType.DEFAULT);
    }

    @RequestMapping("/createstacksnap")
    @ResponseBody
    public String createStackSnap(int pid, int type) {
        Log.getLog("test", "createStackSnap " + pid, type + "");
        JmapType types = JmapType.DUMP;
        if (type == 1) {
            types = JmapType.DUMP;
        } else if (type == 2) {
            types = JmapType.HISTO;
        } else if (type == 3) {
            types = JmapType.HEAP;
        } else if (type == 4) {
            types = JmapType.FINALIZERINFO;
        }
        return jmapImpl.saveHeap(pid, types);
    }

    @RequestMapping("/getthreadsnap")
    @ResponseBody
    public LayuiTableVO getThreadSnap(int page, int limit) {
        return jstackImpl.getThreadSnap(page, limit);
    }

    @RequestMapping("/getstacksnap")
    @ResponseBody
    public LayuiTableVO getStackSnap(int page, int limit) {
        return jmapImpl.getStackSnap(page, limit);
    }

    /**
     * 文件下载 可以下载大文件
     *
     * @param path     文件路径
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/downloadsnap")
    public String downloadSnap(String path, HttpServletRequest request, HttpServletResponse response) {
        try {
            File file = new File(FileUtil.replacePath(path.replaceAll("\\,", "\\/")));
            response.setContentType("text/html;charset=utf-8");
            try {
                request.setCharacterEncoding("UTF-8");
            } catch (UnsupportedEncodingException e) {
                Log.getLog("test", e.getMessage(), "downloaderror", LogType.ERROR);
            }
            BufferedInputStream in = null;
            BufferedOutputStream out = null;
            String downLoadPath = FileUtil.replacePath(path.replaceAll("\\,", "\\/"));
            try {
                response.setContentType("application/x-msdownload;");
                response.setHeader("Content-disposition", "attachment; filename=" + new String(path.substring(path.lastIndexOf(",") + 1, path.length()).getBytes("utf-8"), "ISO8859-1"));
                response.setHeader("Content-Length", String.valueOf(file.length()));
                in = new BufferedInputStream(new FileInputStream(downLoadPath));
                out = new BufferedOutputStream(response.getOutputStream());
                byte[] buff = new byte[2048];
                int bytesRead;
                while (-1 != (bytesRead = in.read(buff, 0, buff.length))) {
                    out.write(buff, 0, bytesRead);
                }
            } catch (Exception e) {
                Log.getLog("test", e.getMessage(), "downloadError", LogType.ERROR);
            } finally {
                if (in != null) {
                    try {
                        in.close();
                    } catch (IOException e) {
                        Log.getLog("test", e.getMessage(), "BufferedInputStream Close Error", LogType.ERROR);
                    }
                }
                if (out != null) {
                    try {
                        out.close();
                    } catch (IOException e) {
                        Log.getLog("test", e.getMessage(), "BufferedOutputStream Close Error", LogType.ERROR);
                    }
                }
            }
        } catch (OutOfMemoryError e) {
            Log.getLog("test", e.getMessage(), "downloadError OutOfMemoryError Java heap space", LogType.ERROR);
        }
        return null;
    }

    /**
     * 下载文件第一种方法 下载大文件可能会内存溢出
     *
     * @param path 文件路径
     * @return
     * @throws IOException
     */
    @RequestMapping("/downloadsnap1")
    public ResponseEntity<byte[]> downloadSnap1(String path) {
        try {
            File file = new File(FileUtil.replacePath(path.replaceAll("\\,", "\\/")));
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            String show = path.substring(path.lastIndexOf(",") + 1, path.length());
            headers.setContentDispositionFormData("attachment", show);
            byte[] bytes;
            try {
                bytes = FileUtils.readFileToByteArray(file);
            } catch (IOException e) {
                Log.getLog("test", e.getMessage(), "downloadError", LogType.ERROR);
                return null;
            }
            return new ResponseEntity<byte[]>(bytes, headers, HttpStatus.CREATED);
        } catch (OutOfMemoryError e) {
            Log.getLog("test", e.getMessage(), "downloadError OutOfMemoryError Java heap space", LogType.ERROR);
            return null;
        }
    }

    @RequestMapping("/deletesnap")
    @ResponseBody
    public int deleteSnap(String path) {
        return new File(FileUtil.replacePath(path.replaceAll("\\,", "\\/"))).delete() ? 1 : 0;
    }

    @RequestMapping("/getjavahome")
    @ResponseBody
    public String getJavaHome() {
        return StringUtil.htmlEncode(JavaProperties.JAVA_HOME);
    }

    @RequestMapping("/getjavaversion")
    @ResponseBody
    public String getJavaVersion() {
        return StringUtil.htmlEncode(JavaProperties.JAVA_VERSION);
    }

    @RequestMapping("/getjavavendor")
    @ResponseBody
    public String getJavaVendor() {
        return StringUtil.htmlEncode(JavaProperties.JAVA_VENDOR);
    }

}
