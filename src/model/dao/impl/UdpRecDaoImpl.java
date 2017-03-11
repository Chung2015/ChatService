package model.dao.impl;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.HashMap;

import model.dao.UdpRecDao;
import model.pojo.MyGlobal;

public class UdpRecDaoImpl implements UdpRecDao {
    
    private HashMap<String, String[]> ipPort = MyGlobal.getIpPort();
    
    @Override
    public void receive(DatagramSocket ds, DatagramPacket dp) throws IOException {
        // TODO Auto-generated method stub
        ds.receive(dp);
        String[] temp = new String[2];
        temp[0] = dp.getAddress().toString().substring(1);
        temp[1] = String.valueOf(dp.getPort());
        String userName = new String(dp.getData(), dp.getOffset(), dp.getLength());
        ipPort.put(userName, temp);
    }

}
