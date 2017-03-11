package model.bo.impl;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

import model.bo.UdpRecBo;
import model.dao.UdpRecDao;
import model.dao.impl.UdpRecDaoImpl;
import model.pojo.MyGlobal;

public class UdpRecBoImpl implements UdpRecBo {

    private DatagramPacket dp;
    private DatagramSocket ds;
    private UdpRecDao urDao = new UdpRecDaoImpl();
    public UdpRecBoImpl(){
        try {
            ds = new DatagramSocket(MyGlobal.getPortchat());
        } catch (SocketException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        dp = new DatagramPacket(MyGlobal.getByt(), MyGlobal.getByt().length);
    }
    @Override
    public void recUdp() {
        // TODO Auto-generated method stub
        try {
            urDao.receive(ds, dp);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
