package com.gxs.myboot.controller;

import com.gxs.myboot.util.Des.Des;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestLog {

    private static final Logger logger = LoggerFactory.getLogger(TestLog.class);

    public static void main(String[] args){

       /* String en = Des.Encrypt3Des("郭新胜", "9498846d97ff772a342fc260", "ToHex16");
        System.out.println(en);*/

        try {
            int i = Integer.parseInt("ab");
        }catch (NumberFormatException e){
            logger.error("转换int失败：" + e.getLocalizedMessage());
        }
    }
}
