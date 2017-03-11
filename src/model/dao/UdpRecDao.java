package model.dao;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public interface UdpRecDao {
    public void receive(DatagramSocket ds, DatagramPacket dp) throws IOException;
}
