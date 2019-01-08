package com;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author:Li
 * @time: 2019/1/2 14:44
 * @version: 1.0.0
 */
@SpringBootApplication
@EnableTransactionManagement(proxyTargetClass = true)
public class SysminApplication {

    public static void main(String[] args) {
        SpringApplication.run(SysminApplication.class, args);
    }

}

