package com.sysmin.core.system.service.impl;

import com.sysmin.core.system.domain.DiskDO;
import com.sysmin.core.system.service.api.DiskApi;
import com.sysmin.util.BashUtil;
import com.sysmin.util.StringUtil;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.StringTokenizer;

/**
 * @author:Li
 * @time: 2018/12/27 8:56
 * @version: 1.0.0
 */
@Service
public class DiskImpl implements DiskApi {

    @Override
    public ArrayList<DiskDO> getDiskInfo() {
        String data = StringUtil.replaceBlank(BashUtil.exec("df -l"), " ");
        ArrayList<DiskDO> list = new ArrayList<>();
        String[] lines = data.split("\n");
        DiskDO tmp = new DiskDO();
        for (int i = 1; i < lines.length; i++) {
            StringTokenizer token = new StringTokenizer(lines[i], " ");
            if (token.countTokens() == 1) {
                tmp.setFileSystem(token.nextToken());
            } else if (token.countTokens() == 5) {
                tmp.setBlocks(Long.valueOf(token.nextToken()))
                        .setUsed(Long.valueOf(token.nextToken()))
                        .setAvailable(Long.valueOf(token.nextToken()))
                        .setUse(token.nextToken())
                        .setMounted(token.nextToken());
                DiskDO disk = new DiskDO(tmp.getFileSystem(), tmp.getBlocks(), tmp.getUsed(), tmp.getAvailable(), tmp.getUse(), tmp.getMounted());
                tmp.clear();
                list.add(disk);
            } else if (token.countTokens() >= 6) {
                tmp.setFileSystem(token.nextToken())
                        .setBlocks(Long.valueOf(token.nextToken()))
                        .setUsed(Long.valueOf(token.nextToken()))
                        .setAvailable(Long.valueOf(token.nextToken()))
                        .setUse(token.nextToken());
                int count = token.countTokens();
                if (count == 1) {
                    tmp.setMounted(token.nextToken());
                } else {
                    StringBuilder sb = new StringBuilder();
                    for (int j = 0; j < count; j++) {
                        sb.append(token.nextToken() + "\n");
                    }
                    tmp.setMounted(sb.toString());
                }
                DiskDO disk = new DiskDO(tmp.getFileSystem(), tmp.getBlocks(), tmp.getUsed(), tmp.getAvailable(), tmp.getUse(), tmp.getMounted());
                tmp.clear();
                list.add(disk);
            }
        }
        return list;
    }

}
