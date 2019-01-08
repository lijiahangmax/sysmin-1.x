package com.sysmin.core.linux.net.service.impl;

import com.sysmin.core.linux.net.domain.NetInfoDO;
import com.sysmin.core.linux.net.service.api.NetInfoApi;
import com.sysmin.util.BashUtil;
import com.sysmin.util.StringUtil;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * @author:Li
 * @time: 2018/12/21 19:54
 * @version: 1.0.0
 */
@Service
public class NetInfoImpl implements NetInfoApi {


    @Override
    public List<NetInfoDO> getAllNetInfo() {
        return listNetInfo("netstat -auntpl");
    }

    @Override
    public List<NetInfoDO> getAllNetInfo(String process) {
        return listNetInfo(BashUtil.toLinuxCommand("netstat -auntpl | grep " + process));
    }

    @Override
    public List<NetInfoDO> getTcpNetInfo() {
        return listNetInfo("netstat -atp");
    }

    @Override
    public List<NetInfoDO> getUdpNetInfo() {
        return listNetInfo("netstat -aup");
    }

    private List<NetInfoDO> listNetInfo(Object commands) {
        List<NetInfoDO> list = new ArrayList<>();
        String lines = BashUtil.exec(commands);
        String[] line = lines.split("\n");
        for (int i = 0; i < line.length; i++) {
            String data = StringUtil.replaceBlank(line[i], " ");
            NetInfoDO netInfo = new NetInfoDO();
            StringTokenizer token = new StringTokenizer(data, " ");
            String proto = token.nextToken();
            if ("Active".equals(proto) || "Proto".equals(proto)) {
                continue;
            }
            netInfo.setProto(proto)
                    .setRecv(token.nextToken())
                    .setSend(token.nextToken())
                    .setLocalAddress(token.nextToken())
                    .setForeignAddress(token.nextToken());
            String state = token.nextToken();
            String[] tmp = state.split("/");
            if (tmp.length == 1) {
                netInfo.setState(state);
                String[] idAndName = token.nextToken().split("/");
                netInfo.setPid(Integer.valueOf(idAndName[0]))
                        .setName(idAndName[1]);
            } else {
                netInfo.setState(" ")
                        .setPid(Integer.valueOf(tmp[0]))
                        .setName(tmp[1]);
            }
            int count = token.countTokens();
            if (count >= 1) {
                for (int j = 0; j < count; j++) {
                    netInfo.setName(netInfo.getName() + token.nextToken());
                }
            }
            list.add(netInfo);
        }
        return list;
    }
}
