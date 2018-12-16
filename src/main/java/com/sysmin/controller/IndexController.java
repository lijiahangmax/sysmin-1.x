package com.sysmin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author:Li
 * @time: 2018/12/14 16:35
 * @version: 1.0.0
 */
@Controller
public class IndexController {

    @RequestMapping("/")
    public String index() {
        System.out.println("ss");
        return "index";
    }

    @RequestMapping("/setting")
    public String setting() {
        return "setting";
    }

}
