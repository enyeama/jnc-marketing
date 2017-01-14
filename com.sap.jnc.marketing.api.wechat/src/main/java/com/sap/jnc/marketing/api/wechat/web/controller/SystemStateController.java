package com.sap.jnc.marketing.api.wechat.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Calendar;

import javax.servlet.http.HttpSession;

/**
 * @author I071053 Diouf Du
 */
@RestController
@RequestMapping("/system")
public class SystemStateController {

    private static Logger LOGGER = LoggerFactory.getLogger(SystemStateController.class);

    @RequestMapping("/state")
    public String checkState(HttpSession session) throws UnknownHostException {
        LOGGER.info("check server state at {}", Calendar.getInstance());
        InetAddress host = InetAddress.getLocalHost();
        String ip = host.getHostAddress().toString();
        String hostname = host.getHostName().toString();
        return "Working, session id: " + session.getId() + "<br />" + "ip: " + ip + ", host name: " + hostname;
    }
}
