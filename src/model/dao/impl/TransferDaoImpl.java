package model.dao.impl;

import java.io.PrintWriter;
import java.util.Scanner;

import model.dao.TransferDao;

public class TransferDaoImpl implements TransferDao {

    @Override
    public String receive(Scanner in) {
        // TODO Auto-generated method stub
        String msg = in.nextLine();
        //System.out.println(msg);
        return msg;
    }

    @Override
    public void respon(PrintWriter out,String s) {
        // TODO Auto-generated method stub
        out.println(s);
        out.flush();
    }

}
