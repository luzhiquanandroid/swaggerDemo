package com.fotron.draw.utils;

import com.fotrontimes.core.utils.HostUtil;
import org.springframework.util.StringUtils;

import java.util.UUID;

/**
 * @author: yutong
 * @createDate: 2018/7/5
 * @company: (C) Copyright fotron
 * @since: JDK 1.8
 * @Description:
 */
public class IdWorker {

    private static long workId;

    private IdWorker() {

    }

    static {
        // 获取主机名，截取后三位
        String hostName = HostUtil.getHostName();
        if (!StringUtils.isEmpty(hostName) && hostName.length() > 4) {
            String hostId = hostName.substring(hostName.length() - 3);
            try {
                workId = Long.parseLong(hostId);
            } catch (NumberFormatException ignore) {
                workId = 1;
            }
        }
    }

    private static class InnerInstance {
        private static com.fotrontimes.core.utils.IdWorker instance = new com.fotrontimes.core.utils.IdWorker(workId);
    }

    public static String getUid() {
        try {
            long id = InnerInstance.instance.nextId();
            return Long.toString(id, 36);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return UUID.randomUUID().toString();
    }

    public static String getNo() {
        try {
            long id = InnerInstance.instance.nextId();
            return id + "";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return System.currentTimeMillis() + "";
    }

}
