package com.gxs.myboot.util;

import com.sshtools.j2ssh.SshClient;
import com.sshtools.j2ssh.authentication.AuthenticationProtocolState;
import com.sshtools.j2ssh.authentication.PasswordAuthenticationClient;
import com.sshtools.j2ssh.sftp.SftpFile;

import java.io.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

public class FileUtil {
    public static void main(String[] args){
        //System.out.println("最终返回：" + get("wallet_server_info.log"));
        //红包金额
        BigDecimal redPacketAmount =  new BigDecimal(1.00);
        //红包数量
        int redPacketNum = 3;
        BigDecimal receiveAmountBack = redPacketAmount.divide(new BigDecimal(redPacketNum), RoundingMode.HALF_UP);
        System.out.println(receiveAmountBack);
    }


    /**
     * 从服务器读取文件并写入本地
     * @param fileName
     */
    public static void addFile(String fileName) {
        SshClient client=new SshClient();
        String server_ip = "";
        String username = "";
        String password = "";
        String path = "";

        if ("wallet_server_info.log".equals(fileName)){
            server_ip = "192.168.162.175";
            username = "tempuser";
            password = "tempuser123";
            path = "/hy/logs/wallet_server/";
        }
        if ("payment_wallet_info.log".equals(fileName)){
            server_ip = "192.168.162.173";
            username = "tempuser";
            password = "tempuser123";
            path = "/hy/logs/payment_wallet/";
        }
        if ("payment_server_info.log".equals(fileName)){
            server_ip = "192.168.162.175";
            username = "tempuser";
            password = "tempuser123";
            path = "/hy/logs/payment_server/";
        }



        try{
            client.connect(server_ip);
            //设置用户名和密码
            PasswordAuthenticationClient pwd = new PasswordAuthenticationClient();
            pwd.setUsername(username);
            pwd.setPassword(password);
            int result=client.authenticate(pwd);
            if(result== AuthenticationProtocolState.COMPLETE){//如果连接完成
                System.out.println("==============="+result);
                List<SftpFile> list = client.openSftpClient().ls(path);
                for (SftpFile f : list) {
//                    System.out.println(f.getFilename());
//                    System.out.println(f.getAbsolutePath());
                    if(f.getFilename().equals(fileName)){
                        clearInfoForFile("E:/serverLog/"+f.getFilename());
                        OutputStream os = new FileOutputStream("E:/serverLog/"+f.getFilename());
                        client.openSftpClient().get(path + fileName, os);
                        //以行为单位读取文件start
                        File file = new File("E:/serverLog/"+fileName);
                        BufferedReader reader = null;
                        try {
                            System.out.println("以行为单位读取文件内容，一次读一整行：");
                            reader = new BufferedReader(new FileReader(file));
                            int line = 1;//行号
                            //一次读入一行，直到读入null为文件结束
                            while ((reader.readLine()) != null) {
                                //显示行号
                                line++;
                            }
                            reader.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        } finally {
                            if (reader != null) {
                                try {
                                    reader.close();
                                } catch (IOException e1) {
                                }
                            }
                        }
                        //以行为单位读取文件end
                    }
                }
            }
        }catch(IOException e){
            e.printStackTrace();
        }
    }


    /**
     * 清空已有的文件内容，以便下次重新写入新的内容
     * @param fileName
     */
    public static void clearInfoForFile(String fileName) {
        File file =new File(fileName);
        try {
            if(!file.exists()) {
                file.createNewFile();
            }
            FileWriter fileWriter =new FileWriter(file);
            fileWriter.write("");
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * 从本地文件读取后10行数据
     * @param fileName
     * @return
     */
    public static String get(String fileName){
        addFile(fileName);
        fileName = "E:/serverLog/" + fileName;
        String retString = "";
        try {
            int lines = getTotalLines(fileName);

            BufferedReader in = new BufferedReader(new InputStreamReader(
                    new FileInputStream(fileName)));
            LineNumberReader reader = new LineNumberReader(in);
            int line = 1;
            String tempString = "";
            while ((tempString = reader.readLine()) != null) {
                //显示行号
                if (lines-10 < line && line <= lines){
                    System.out.println("---------gxs-----------"+tempString);
                    retString += tempString + "\r\n</br>";
                }
                line++;
            }
            reader.close();
            in.close();
            return retString;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 获取文件总行数
     * @param fileName
     * @return
     * @throws IOException
     */
    static int getTotalLines(String fileName) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(
                new FileInputStream(fileName)));
        LineNumberReader reader = new LineNumberReader(in);
        String s = reader.readLine();
        int lines = 0;
        while (s != null) {
            lines++;
            s = reader.readLine();
        }
        reader.close();
        in.close();
        return lines;
    }


}
