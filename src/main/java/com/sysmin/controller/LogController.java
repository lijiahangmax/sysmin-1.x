package com.sysmin.controller;

import com.sysmin.core.log.Log;
import com.sysmin.core.log.LogFile;
import com.sysmin.util.JsonUtil;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.ArrayList;

/**
 * @author:Li
 * @time: 2019/1/3 17:58
 * @version: 1.0.0
 */
@Controller
@EnableScheduling
public class LogController {

    @Resource
    private SimpMessagingTemplate simpMessagingTemplate;

    @Scheduled(fixedRate = 2000)
    public void sendLog() {
        if (!Log.logList.isEmpty()) {
            Log.logList.forEach(this::accept);
            Log.logList.clear();
        }
    }

    @RequestMapping("/logcache")
    @ResponseBody
    public int cacheLog() {
        if (Log.cacheLog.isEmpty()) {
            return 2;
        }
        try {
            Log.cache();
            return 1;
        } catch (Exception e) {
            return 0;
        }
    }

    @RequestMapping("/loadlogfile")
    @ResponseBody
    public ArrayList<Log> loadLogFile(String path) {
        return LogFile.loadLogFile(path);
    }

    @RequestMapping("/listlogfiles")
    @ResponseBody
    public String[] listLogFile() {
        return LogFile.listLogFile();
    }

    private void accept(Log s) {
        simpMessagingTemplate.convertAndSendToUser("test", "/log", JsonUtil.objToJson(s));
    }
}
