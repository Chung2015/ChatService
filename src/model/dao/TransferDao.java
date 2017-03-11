package model.dao;

import java.io.PrintWriter;
import java.util.Scanner;

public interface TransferDao {

    public String receive(Scanner in);
    public void respon(PrintWriter out,String s);
    
}
