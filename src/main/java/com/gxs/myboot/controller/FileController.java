package com.gxs.myboot.controller;

import com.gxs.myboot.util.FileUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FileController {

    private final Logger logger = LoggerFactory.getLogger(HtmlController.class);

    @RequestMapping("/wallet")
    public String wallet(){
        logger.info("请求了/wallet");
        return FileUtil.get("wallet_server_info.log");
    }

    @RequestMapping("/pay")
    public String pay(){
        logger.info("请求了/pay");
        return FileUtil.get("payment_server_info.log");
    }
}
