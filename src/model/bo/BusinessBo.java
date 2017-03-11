package model.bo;

import java.io.PrintWriter;

import model.dao.TransferDao;
import model.entity.User;

public interface BusinessBo {
    
    public boolean receive();
    public User getCurrentUser();
    public TransferDao gettDao();
    public PrintWriter getOut();
    
}
