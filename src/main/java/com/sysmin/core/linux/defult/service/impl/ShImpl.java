package com.sysmin.core.linux.defult.service.impl;

import com.sysmin.core.linux.defult.service.api.ShApi;
import com.sysmin.global.GlobalConfig;
import com.sysmin.util.BashUtil;
import com.sysmin.util.StringUtil;
import org.springframework.stereotype.Service;

import java.io.*;

/**
 * @author:Li
 * @time: 2018/12/24 16:00
 * @version: 1.0.0
 */
@Service
public class ShImpl implements ShApi {

    @Override
    public String run(String file) throws FileNotFoundException {
        return run(file, false);
    }

    @Override
    public String run(String file, boolean wait) throws FileNotFoundException {
        return run(file, wait, "");
    }

    @Override
    public String run(String file, boolean wait, String param) throws FileNotFoundException {
        if (new File(file).exists()) {
            return (BashUtil.exec("sh " + file + (StringUtil.checkEmpty(param) ? "" : " " + param), wait));
        } else {
            throw new FileNotFoundException(file + " not found");
        }
    }

    /**
     * 创建sh脚本
     *
     * @param file 脚本文件名称 如(test.sh) 不包含路径
     * @return true创建成功 false创建失败(如: 文件已经存在)
     */
    private boolean createSh(String file) {
        File dir = new File((String) GlobalConfig.getValue("shPath"));
        if (!dir.exists() || dir.isFile()) {
            boolean mkdir = dir.mkdir();
            if (!mkdir) {
                return false;
            }
        }
        File sh = new File(dir.getPath() + File.separator + file);
        if (sh.exists()) {
            return false;
        } else {
            try {
                return sh.createNewFile();
            } catch (IOException e) {
                return false;
            }
        }
    }

    @Override
    public boolean writeSh(String file, String... data) {
        return writeSh(file, true, data);
    }

    @Override
    public boolean writeSh(String file, boolean append, String... data) {
        if (file.contains(".")) {
            if (file.endsWith(".")) {
                file = file + "sh";
            } else if (!file.endsWith(".sh")) {
                file = file + ".sh";
            }
        } else {
            file = file + ".sh";
        }
        File sh = new File(GlobalConfig.getValue("shPath") + File.separator + file);
        if (sh.exists() && sh.isFile()) {
            try {
                BufferedWriter wb = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(sh, append)));
                for (String s : data) {
                    wb.write(s);
                    wb.newLine();
                }
                wb.flush();
                wb.close();
                return true;
            } catch (IOException e) {
                return false;
            }
        } else {
            return createSh(file) && writeSh(file, data);
        }
    }

}
