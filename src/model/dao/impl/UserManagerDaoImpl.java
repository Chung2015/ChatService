package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

import model.dao.UserManagerDao;
import model.entity.User;

public class UserManagerDaoImpl implements UserManagerDao {

    @Override
    public String queryAccount(String account) {
        // TODO Auto-generated method stub
        
        String sql = "select account from user where account=?";
        
        Connection conn = MyFactory.getUserDBConnection();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        String ans = null;
        
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, account);
            rs = pstmt.executeQuery();
            while(rs.next()){
                ans = rs.getString("account");
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        return ans;
    }

    @Override
    public String queryUserName(String userName) {
        // TODO Auto-generated method stub
        String sql = "select userName from user where userName=?";
        
        Connection conn = MyFactory.getUserDBConnection();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        String ans = null;
        
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, userName);
            rs = pstmt.executeQuery();
            while(rs.next()){
                ans = rs.getString("userName");
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        return ans;
    }

    @Override
    public String queryUserNameLog(String account) {
        // TODO Auto-generated method stub
        String sql = "select userName from user where account=?";
        
        Connection conn = MyFactory.getUserDBConnection();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        String ans = null;
        
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, account);
            rs = pstmt.executeQuery();
            while(rs.next()){
                ans = rs.getString("userName");
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        return ans;
    }

    @Override
    public String queryPassword(String account) {
        // TODO Auto-generated method stub
        String sql = "select password from user where account=?";
        
        Connection conn = MyFactory.getUserDBConnection();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        String ans = null;
        
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, account);
            rs = pstmt.executeQuery();
            while(rs.next()){
                ans = rs.getString("password");
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        return ans;
    }

    @Override
    public String queryIpAddr(String userName) {
        // TODO Auto-generated method stub
        String sql = "select ipAddr from user where userName=?";
        
        Connection conn = MyFactory.getUserDBConnection();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        String ans = null;
        
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, userName);
            rs = pstmt.executeQuery();
            while(rs.next()){
                ans = rs.getString("ipAddr");
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        return ans;
    }

    @Override
    public void updateIpAddr(String account, String ipAddr) {
        // TODO Auto-generated method stub
        String sql = "update user set ipAddr=? where account = ?";
        
        Connection conn = MyFactory.getUserDBConnection();
        PreparedStatement pstmt = null;
        
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, ipAddr);
            pstmt.setString(2, account);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Override
    public boolean reg(User usr) {
        // TODO Auto-generated method stub
        String sql = "insert into user(account,password,userName,ipAddr) values(?,?,?,?)";
        
        Connection conn = MyFactory.getUserDBConnection();
        PreparedStatement pstmt = null;
        boolean res  = false;
        
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, usr.getAccount());
            pstmt.setString(2, usr.getPassword());
            pstmt.setString(3, usr.getUserName());
            pstmt.setString(4, usr.getIp());
            pstmt.executeUpdate();
            res = true;
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return res;
    }


    @Override
    public String qFriend(String account) {
        // TODO Auto-generated method stub
        String sql = "select friend from user where account=?";
        
        Connection conn = MyFactory.getUserDBConnection();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        String ans = null;
        
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, account);
            rs = pstmt.executeQuery();
            while(rs.next()){
                ans = rs.getString("friend");
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        return ans;
    }

    @Override
    public String[] qFriInfo(String userName) {
        // TODO Auto-generated method stub
        String sql = "select userName,ipAddr from user where userName=?";
        
        Connection conn = MyFactory.getUserDBConnection();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        String ans[] = new String[3];
        
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, userName);
            rs = pstmt.executeQuery();
            while(rs.next()){
                ans[0] = rs.getString("userName");
                ans[1] = rs.getString("ipAddr");
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        return ans;
    }

    @Override
    public LinkedList<String> queryFriend(String userName) {
        // TODO Auto-generated method stub
        String sql = "select userName from user where userName like ?";
        
        Connection conn = MyFactory.getUserDBConnection();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        LinkedList<String> ans = new LinkedList<String>();
        
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, "%"+userName+"%");
            rs = pstmt.executeQuery();
            while(rs.next()){
                ans.add(rs.getString("userName"));
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        return ans;
    }

    @Override
    public boolean addFriend(String cUserName, String userName) {
        // TODO Auto-generated method stub
        String sql = "select friend from user where userName = ?";
        String sql1 = "update user set friend=? where userName = ?";
        
        Connection conn = MyFactory.getUserDBConnection();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        int i = 0;
        
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, cUserName);
            rs = pstmt.executeQuery();
            String ans = "";
            while(rs.next()){
                ans = rs.getString("friend");
            }
            if(ans == null){
                ans = "";
            }
            ans = ans + userName + ",";
            pstmt = conn.prepareStatement(sql1);
            pstmt.setString(1, ans);
            pstmt.setString(2, cUserName);
            i = pstmt.executeUpdate();
            
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        if(i>0){
            return true;
        }else{
            return false;
        }
        
    }

    @Override
    public boolean delFriend(String account, String userName) {
        // TODO Auto-generated method stub
        String sql = "select friend from user where account = ?";
        String sql1 = "update user set friend=? where account = ?";
        
        Connection conn = MyFactory.getUserDBConnection();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        int i = 0;
        
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, account);
            rs = pstmt.executeQuery();
            String ans = "";
            while(rs.next()){
                ans = rs.getString("friend");
            }
            if(ans == null){
                ans = "";
            }
            String[] temp = ans.split((userName+ ","));
            ans="";
            for(String tempp:temp){
                ans+=tempp;
            }
            pstmt = conn.prepareStatement(sql1);
            pstmt.setString(1, ans);
            pstmt.setString(2, account);
            i = pstmt.executeUpdate();
            
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        if(i>0){
            return true;
        }else{
            return false;
        }
    }




}
