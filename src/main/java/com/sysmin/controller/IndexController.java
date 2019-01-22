package com.sysmin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author:Li
 * @time: 2019/1/2 16:35
 * @version: 1.0.0
 */
@Controller
public class IndexController {

    @RequestMapping("/")
    public String init() {
        return "index";
    }

    @RequestMapping("/index")
    public String index() {
        return "index";
    }

    @RequestMapping("/setting")
    public String setting() {
        return "setting";
    }

    @RequestMapping("/console")
    public String console() {
        return "console";
    }

    @RequestMapping("/log")
    public String log() {
        return "log";
    }

    @RequestMapping("/javasnapshot")
    public String javaSnapShot() {
        return "jvm/javasnapshot";
    }

    @RequestMapping("/javaprocessinfo")
    public String javaProcessInfo() {
        return "jvm/javaprocessinfo";
    }

    @RequestMapping("/jvmmonitor")
    public String jvmMonitor() {
        return "jvm/jvmmonitor";
    }

    @RequestMapping("/systemmonitor")
    public String systemMonitor() {
        return "system/systemmonitor";
    }

}
