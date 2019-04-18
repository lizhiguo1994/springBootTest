package com.gxs.myboot.util;

import com.sshtools.j2ssh.net.HttpRequest;
import org.thymeleaf.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;

public class SignSortUtil {


    public static String sort(HttpServletRequest request){
        List<String> list = new ArrayList<String>();
        //签名摘要串，不包含商家key
        String sign = "";
        Enumeration enu=request.getParameterNames();
        while(enu.hasMoreElements()){
            String paraName=(String)enu.nextElement();
            if (!"signString".equals(paraName)){
                list.add(paraName + "=" + request.getParameter(paraName) + "&");
            }
        }
        Collections.sort(list);
        for (String signParam : list) {
            sign += signParam;
        }
        return sign;
    }
}
