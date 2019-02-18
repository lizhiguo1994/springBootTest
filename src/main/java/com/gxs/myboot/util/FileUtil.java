package com.gxs.myboot.util;

import com.sshtools.j2ssh.SshClient;
import com.sshtools.j2ssh.authentication.AuthenticationProtocolState;
import com.sshtools.j2ssh.authentication.PasswordAuthenticationClient;
import com.sshtools.j2ssh.sftp.SftpFile;

import java.io.*;
import java.util.List;

public class FileUtil {
    public static void main(String[] args){
        System.out.println("最终返回：" + get("wallet_server_info.log"));
    }


    /**
     * 从服务器读取文件并写入本地
     * @param fileName
     */
    public static void addFile(String fileName) {
        SshClient client=new SshClient();
        try{
            client.connect("192.168.162.175");
            //设置用户名和密码
            PasswordAuthenticationClient pwd = new PasswordAuthenticationClient();
            pwd.setUsername("tempuser");
            pwd.setPassword("tempuser123");
            int result=client.authenticate(pwd);
            if(result== AuthenticationProtocolState.COMPLETE){//如果连接完成
                System.out.println("==============="+result);
                String path = "wallet_server";
                if (!"wallet_server_info.log".equals(fileName)){
                    path = "payment_server";
                }
                List<SftpFile> list = client.openSftpClient().ls("/hy/logs/"+path);
                for (SftpFile f : list) {
                    System.out.println(f.getFilename());
                    System.out.println(f.getAbsolutePath());
                    if(f.getFilename().equals(fileName)){
                        clearInfoForFile("E:/serverLog/"+f.getFilename());
                        OutputStream os = new FileOutputStream("E:/serverLog/"+f.getFilename());
                        client.openSftpClient().get("/hy/logs/"+path+"/"+fileName, os);
                        //以行为单位读取文件start
                        File file = new File("E:/serverLog/"+fileName);
                        BufferedReader reader = null;
                        try {
                            System.out.println("以行为单位读取文件内容，一次读一整行：");
                            reader = new BufferedReader(new FileReader(file));
                            String tempString = null;
                            int line = 1;//行号
                            //一次读入一行，直到读入null为文件结束
                            while ((tempString = reader.readLine()) != null) {
                                //显示行号
                                System.out.println("line " + line + ": " + tempString);
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


    // 清空已有的文件内容，以便下次重新写入新的内容
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

    // 文件内容的总行数。
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

    /**
     * 删除单个文件
     *
     * @param fileName
     *            要删除的文件的文件名
     * @return 单个文件删除成功返回true，否则返回false
     */
    public static boolean deleteFile(String fileName) {
        File file = new File(fileName);
        // 如果文件路径所对应的文件存在，并且是一个文件，则直接删除
        if (file.exists() && file.isFile()) {
            if (file.delete()) {
                System.out.println("删除单个文件" + fileName + "成功！");
                return true;
            } else {
                System.out.println("删除单个文件" + fileName + "失败！");
                return false;
            }
        } else {
            System.out.println("删除单个文件失败：" + fileName + "不存在！");
            return false;
        }
    }
}
