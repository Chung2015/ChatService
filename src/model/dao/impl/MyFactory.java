package model.dao.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MyFactory {
    
    static{
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
    public static Connection getUserDBConnection(){
        Connection conn = null;
        try {
            conn = DriverManager.getConnection("jdbc:sqlite:ChatHeicoco.db");
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return conn;
    }
    
}