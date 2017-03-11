package model.dao.impl;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.HashMap;

import model.bo.BusinessBo;
import model.dao.TransferDao;
import model.dao.UdpHolePunchingDao;
import model.pojo.MyGlobal;

public class UdpHolePunchingDaoImpl implements UdpHolePunchingDao {
    
    HashMap<String, BusinessBo> bBoMap = MyGlobal.getbBoMap();
    
    /**
     * <br>Description:TODO 服务器告知B，A找，并要求B向服务器发送心跳包，以及要求B向A发送探测包
     * <br>Author:董嘉文(roco2015@163.com)
     * <br>Date:2016年6月1日
     * @see model.dao.UdpHolePunchingDao#lookforAim(java.lang.String, java.lang.String, java.lang.String)
     */
    @Override
    public boolean lookforAim(String userName, String ipAddr, String port) {
        // TODO Auto-generated method stub
        
        BusinessBo bBo = bBoMap.get(userName);
        contentToTxt("","getBbBO");
        if(bBo == null){
            return false;
        }
        TransferDao tDao = bBo.gettDao();
        PrintWriter out = bBo.getOut();
        contentToTxt("","getDao Out");
        String msg = MyGlobal.getReqConnfriendreactive()+","+ipAddr+","+String.valueOf(port)+",";
        //TODO 服务器返回客户端B

        contentToTxt("","ResB Msg:"+msg);
        tDao.respon(out, msg);
        //TODO 服务器接收B心跳包数据
        String[] ipPort = null;
        while(ipPort == null){
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            //TODO 拿到A心跳包的数据，userName要A的userName
            ipPort =  MyGlobal.getIpPort().get(userName);
        }
        msg = MyGlobal.getReqConnfriendreactive()+","+ipAddr+","+String.valueOf(port)+","+ipPort[1];
        contentToTxt("","ResB2 Msg:"+msg);
        tDao.respon(out, msg);
        return true;
    }
    public void contentToTxt(String filePath, String content) {  
        String str = new String(); //原有txt内容  
        String s1 = new String();//内容更新  
        try {  
            filePath = "/home/heicoco/chat/server/log.txt";
            File f = new File(filePath);  
            if (f.exists()) {  
                System.out.print("文件存在");  
            } else {  
                System.out.print("文件不存在");  
                f.createNewFile();// 不存在则创建  
            }  
            BufferedReader input = new BufferedReader(new FileReader(f));  
  
            while ((str = input.readLine()) != null) {  
                s1 += str + "\n";  
            }  
            System.out.println(s1);  
            input.close();  
            s1 += content;  
  
            BufferedWriter output = new BufferedWriter(new FileWriter(f));  
            output.write(s1);  
            output.close();  
        } catch (Exception e) {  
            e.printStackTrace();  
  
        }  
    } 
}
