package com.gxs.myboot.controller;

import jdk.nashorn.internal.ir.RuntimeNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class HtmlController {

    private final Logger logger = LoggerFactory.getLogger(HtmlController.class);

    @RequestMapping("/hh")
    public String index(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Cookie[] cookies = request.getCookies();
        if (cookies == null){
            logger.info("没有获取到任何Cookie");
            return "/index";
        }
        Cookie userCookie = null;
        for (Cookie cokie : cookies){
            //筛选自己需要的cookie
            if (cokie.getName().equals("username")){
                userCookie = cokie;
            }
        }
        if (userCookie == null){
            logger.info("当前没有已登录的账号，跳至登录页");
            return "/index";
        }
        logger.info("当前存在登录的账号，跳至首页");
        return "/main";

    }


    @RequestMapping("/login")
    public String login(HttpServletRequest request, HttpServletResponse response) throws IOException {
        logger.info("login方法被访问");
        //应该有账号密码的校验后保存cookie，否则跳转登录失败页面
        String username = request.getParameter("username");
        //创建cookie对象，赋值key-value
        Cookie userCookie = new Cookie("username",username);
        //设置cookie有效期（单位秒）
        userCookie.setMaxAge(60);
        //设置可以访问此cookie的域名
        userCookie.setDomain("localhost");
        //设置可以访问此cookie的路径
        userCookie.setPath("/");
        //设置是否只能通过https来传递此cookie
        userCookie.setSecure(false);
        //若此属性为true，则只有在http请求头中会带有此cookie的信息，而不能通过document.cookie来访问此cookie
        userCookie.setHttpOnly(true);
        //将cookie添加到response中
        response.addCookie(userCookie);
        return "/main";
    }



}
