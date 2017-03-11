package controller;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import model.bo.BusinessBo;
import model.bo.UdpRecBo;
import model.bo.impl.BusinessBoImpl;
import model.bo.impl.UdpRecBoImpl;
import model.pojo.MyGlobal;

public class Main {

    static ServerSocket ssBusiness =  MyGlobal.getSsBusiness();
    static Socket socket = null;
    
    public static void main(String[] args) {
        // TODO Auto-generated method stub
        new Thread(new Runnable() {
            
            @Override
            public void run() {
                // TODO Auto-generated method stub
                while(true){
                    try {
                        socket = ssBusiness.accept();
                        System.out.println("一个TCP连接");
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    new Thread(new Runnable() {
                        Socket sk = socket;
                        BusinessBo bBo = new BusinessBoImpl(sk);
                        
                        @Override
                        public void run() {
                            // TODO Auto-generated method stub
                            while(bBo.receive());
                        }

                    }).start();
                }
            }
        }).start();
        
        new Thread(new Runnable() {
            UdpRecBo urBo = new UdpRecBoImpl();
            @Override
            public void run() {
                // TODO Auto-generated method stub
                while(true){
                    urBo.recUdp();
                }
            }
        }).start();
    }
}
