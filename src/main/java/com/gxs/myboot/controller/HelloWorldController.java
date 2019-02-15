package com.gxs.myboot.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldController {

    @RequestMapping("/hello")
    public String helloWorld(){
        System.out.println("请求了/hello");
        return "Hello World";
    }
}
