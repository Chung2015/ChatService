package model.bo.impl;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Scanner;

import model.bo.BusinessBo;
import model.dao.DealRegularDao;
import model.dao.TransferDao;
import model.dao.UdpHolePunchingDao;
import model.dao.UserManagerDao;
import model.dao.impl.DealRegularDaoImpl;
import model.dao.impl.TransferDaoImpl;
import model.dao.impl.UdpHolePunchingDaoImpl;
import model.dao.impl.UserManagerDaoImpl;
import model.entity.User;
import model.pojo.MyGlobal;

public class BusinessBoImpl implements BusinessBo {
    
    private TransferDao tDao = new TransferDaoImpl();
    private UserManagerDao uDao = new UserManagerDaoImpl();
    private DealRegularDao drDao = new DealRegularDaoImpl();
    
    private Socket sk;
    private InputStream is;
    private Scanner in;
    private OutputStream os;
    private PrintWriter out; 
    
    private User currentUser = null;
    
    private HashMap<String, String[]> ipPort = MyGlobal.getIpPort();
    
    public BusinessBoImpl() {
        // TODO Auto-generated constructor stub
    }
    public BusinessBoImpl(Socket sk) {
        // TODO Auto-generated constructor stub
        this.sk = sk;
        try {
            is = sk.getInputStream();
            in = new Scanner(is);
            os = sk.getOutputStream();
            out = new PrintWriter(os, true); 
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    /**
     * <br>Description:TODO 先建立tcp连接，接收请求代码，请求代码为0注册，1登陆,2查询，响应代码见MyGlobal.java
     * <br>Author:董嘉文(roco2015@163.com)
     * <br>Date:2016年5月10日
     * @see model.bo.BusinessBo#receive()
     */
    @Override
    public boolean receive() {
        // TODO Auto-generated method stub
        
        // TODO 通过请求代码不同，进行不同的操作
        String requestCode = tDao.receive(in);
        System.out.println(requestCode);
        if(requestCode.equals(MyGlobal.getReqReg())){
            // TODO 注册,注册需要接收三条数据，账号，用户名，密码。需返回ip，port
            String account = tDao.receive(in);
            String userName = tDao.receive(in);
            String password = tDao.receive(in);
            String accountDB = uDao.queryAccount(account);
            String userNameDB = uDao.queryAccount(userName);
            
            if(accountDB == null){
                tDao.respon(out,MyGlobal.getOpsucceed());
                if(userNameDB == null){
                    tDao.respon(out,MyGlobal.getOpsucceed());
                    String ip = sk.getInetAddress().toString().substring(1);
                    User usr = new User();
                    usr.setAccount(account);
                    usr.setUserName(userName);
                    usr.setPassword(password);
                    usr.setIp(ip);
                    if(uDao.reg(usr)){
                        tDao.respon(out,MyGlobal.getOpsucceed());
                        HashMap<String, BusinessBo> bBoMap = MyGlobal.getbBoMap();
                        bBoMap.put(userName, this);
                        setCurrentUser(account, ip, password, userName, sk.getPort());
                        tDao.respon(out,ip);
                        tDao.respon(out,String.valueOf(sk.getPort()));
                        System.out.println("注册成功");
                    }else{
                        tDao.respon(out,MyGlobal.getOpfailUnknown());
                    }
                }               
            }else{
                tDao.respon(out,MyGlobal.getOpfailAccountsamed());
            }
            tDao.respon(out,MyGlobal.getTheend());
            return true;
        }else if(requestCode.equals(MyGlobal.getReqLog())){
            // TODO 登录,登录需要接收两条数据，账号，密码。需返回userName,ip，port
            String account = tDao.receive(in);
            String password = tDao.receive(in);
            String accountDB = uDao.queryAccount(account);
            if(accountDB == null){
                tDao.respon(out,MyGlobal.getOpfailNotheuser());
            }else{
                tDao.respon(out,MyGlobal.getOpsucceed());
                String passwordDB = uDao.queryPassword(account);
                if(password.equals(passwordDB)){
                    String userName = uDao.queryUserNameLog(account);
                    String ipAddr = sk.getInetAddress().toString().substring(1);
                    
                    tDao.respon(out,MyGlobal.getOpsucceed());
                    //至此登陆成功
                    tDao.respon(out, userName);
                    tDao.respon(out, ipAddr);
                    tDao.respon(out, String.valueOf(sk.getPort()));
                    
                    uDao.updateIpAddr(account, ipAddr);
                    HashMap<String, BusinessBo> bBoMap = MyGlobal.getbBoMap();
                    bBoMap.put(userName, this);
                    setCurrentUser(account, ipAddr, password, userName, sk.getPort());
                }else{
                    tDao.respon(out,MyGlobal.getOpfailWrongpassword());
                }
            }
            tDao.respon(out,MyGlobal.getTheend());
            return true;
        }else if(requestCode.equals(MyGlobal.getReqGetfri())){
            // TODO 获取当前用户的联系人列表
            String account = tDao.receive(in);
            String ans = uDao.qFriend(account);
            if(ans == null){
                tDao.respon(out, "0");
            }else{
                String[] friL = drDao.friendList(ans);
                tDao.respon(out, String.valueOf(friL.length));
                for(String userName:friL){
                    String[] res = uDao.qFriInfo(userName);
                    tDao.respon(out, res[0]);   //返回联系人用户名userName
                    tDao.respon(out, res[1]);   //返回联系人ipAddr
                }
            }
            tDao.respon(out,MyGlobal.getTheend());
            return true;
        }else if(requestCode.equals(MyGlobal.getReqConnfriend())){
            // TODO 与目标联系人建立连接
            UdpHolePunchingDao uhpDao = new UdpHolePunchingDaoImpl();
            //userName 为目标B的userName
            String userName = tDao.receive(in);
            contentToTxt("","userName:"+userName);
            String[] temp = null;
            try {
                while(temp == null){
                    Thread.sleep(300);
                    //TODO 拿到A心跳包的数据，userName要A的userName
                    temp = ipPort.get(currentUser.getUserName());
                }
                contentToTxt("","getHeartBeatA:ip"+temp[0]);
                contentToTxt("","getHeartBeatA:port"+temp[1]);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            //TODO 告知B，A找，并返回B心跳包数据，返回值true当前联系人在线，false不在线
            boolean flag = uhpDao.lookforAim(userName, temp[0], temp[1]);
            contentToTxt("","tellB");
            if(flag){
                String[] temp2 = null;
                contentToTxt("","B online");
                try {
                    while(temp2 == null){
                        Thread.sleep(300);
                        //TODO 拿到A心跳包的数据，userName要B的userName
                        temp2 = ipPort.get(userName);
                    }
                    contentToTxt("","getHeartBeatB:ip"+temp2[0]);
                    contentToTxt("","getHeartBeatB:port"+temp2[1]);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                
                tDao.respon(out, MyGlobal.getOpsucceed());
                tDao.respon(out, temp2[0]);   //返回B:ipAddr
                tDao.respon(out, temp2[1]);   //返回B:port
                tDao.respon(out, temp[1]);   //返回A:port
            }else{
                contentToTxt("","B offline");
                tDao.respon(out, MyGlobal.getOpfailConnfri());
            }
            tDao.respon(out,MyGlobal.getTheend());
            return true;
        }else if(requestCode.equals(MyGlobal.getReqQfriend())){
            // TODO 查找联系人
            String userName = tDao.receive(in);
            LinkedList<String> ans = uDao.queryFriend(userName);
            tDao.respon(out,String.valueOf(ans.size()));
            Iterator<String> it = ans.iterator();
            while(it.hasNext()){
                tDao.respon(out, it.next());  //返回联系人用户名userName
            }
            tDao.respon(out,MyGlobal.getTheend());
            return true;
        }else if(requestCode.equals(MyGlobal.getReqAddfriend())){
            // TODO 添加联系人
            String cUserName = tDao.receive(in);
            String userName = tDao.receive(in);
            if(uDao.addFriend(cUserName, userName)){
                tDao.respon(out, MyGlobal.getOpsucceed());
                tDao.respon(out, uDao.queryIpAddr(userName));
            }else{
                tDao.respon(out, MyGlobal.getOpfailAddfri());
            }
            tDao.respon(out,MyGlobal.getTheend());
            return true;
        }else if(requestCode.equals(MyGlobal.getReqDelfriend())){
            // TODO 删除联系人
            String account = tDao.receive(in);
            String userName = tDao.receive(in);
            if(uDao.delFriend(account, userName)){
                tDao.respon(out, MyGlobal.getOpsucceed());
            }else{
                tDao.respon(out, MyGlobal.getOpfailDelfri());
            }
            tDao.respon(out,MyGlobal.getTheend());
            return true;
        }else if(requestCode.equals(MyGlobal.getReqClosetcp())){
            // TODO 关闭连接
            System.out.println("tcp连接关闭");
            return false;
        }else{
            System.out.println("请求代码有误");
            return false;
        }
    }
    private void setCurrentUser(String account, String ipAddr, String password, String userName, int port){
        if(currentUser == null){
            currentUser = new User();
        }
        currentUser.setAccount(account);
        currentUser.setIp(ipAddr);
        currentUser.setPassword(password);
        currentUser.setUserName(userName);
        currentUser.setPort(port);
    }

    @Override
    public User getCurrentUser() {
        return currentUser;
    }
    @Override
    public TransferDao gettDao() {
        return tDao;
    }
    @Override
    public PrintWriter getOut() {
        return out;
    }
    @Override
    protected void finalize() throws Throwable {
        // TODO Auto-generated method stub
        super.finalize();
        MyGlobal.getbBoMap().remove(currentUser.getUserName());
        out.close();
        os.close();
        in.close();
        is.close();
        sk.close();
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
