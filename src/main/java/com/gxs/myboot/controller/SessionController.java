package com.gxs.myboot.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
public class SessionController {

    private final Logger logger = LoggerFactory.getLogger(SessionController.class);

    @RequestMapping("/index")
    public void index(HttpServletRequest request, HttpServletResponse response){
        HttpSession session = request.getSession();
        String sessionId = session.getId();
        logger.info("index_sessionID:{}", sessionId);
        session.setAttribute("name", "zhangsan");
        //设置session有效期5分钟
        session.setMaxInactiveInterval(60*5);
        //创建一个cookie存放sessionID
        Cookie cookie = new Cookie("JSESSIONID", sessionId);
        cookie.setMaxAge(60*5);
        response.addCookie(cookie);
    }

    @RequestMapping("/test")
    @ResponseBody
    public String testSession(HttpServletRequest request, HttpServletResponse response){
        HttpSession session = request.getSession();
        String sessionId = session.getId();
        logger.info("test_sessionID:{}", sessionId);
        String name = (String) session.getAttribute("name");
        logger.info("name:{}", name);
        return name;
    }

    @RequestMapping("/quit")
    @ResponseBody
    public String quit(HttpServletRequest request, HttpServletResponse response){
        HttpSession session = request.getSession();
        session.invalidate();
        logger.info("删除session成功");
        return "删除session成功";
    }

}
