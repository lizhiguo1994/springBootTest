package com.gxs.myboot.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestLog {

    private static final Logger logger = LoggerFactory.getLogger(TestLog.class);

    public static void main(String[] args){
        logger.info("我爱{}，1314", "小萌萌");
        logger.debug("这是一个大BUG");
    }
}
