package com.sysmin.controller.jvm;

import com.sysmin.core.jvm.domain.JpsDO;
import com.sysmin.core.jvm.enums.JpsType;
import com.sysmin.core.jvm.service.impl.JpsImpl;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author:Li
 * @time: 2019/1/4 16:19
 * @version: 1.0.0
 */
@Controller
public class JpsController {

    @Resource
    private JpsImpl jpsImpl;

    @RequestMapping("/jps")
    @ResponseBody
    public List<JpsDO> getJavaProcess() {
        return jpsImpl.jps(JpsType.DEFAULT);
    }
}
