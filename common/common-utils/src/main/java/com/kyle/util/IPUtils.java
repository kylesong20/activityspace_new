package com.kyle.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.net.InetAddress;
import java.net.UnknownHostException;

@Slf4j
public class IPUtils {
    public static String getServerIP() {
        String ip = null;
        try {
            //获取当前服务器ip
            ip = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            log.error("获取当前服务器ip报错", e);
        }
        return ip;
    }

}
