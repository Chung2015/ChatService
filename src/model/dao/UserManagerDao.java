package model.dao;

import java.util.LinkedList;

import model.entity.User;

public interface UserManagerDao {
    /**
     * <br>Description:TODO 方法功能描述
     * <br>Author:董嘉文(roco2015@163.com)
     * <br>Date:2016年5月10日
     * @param sel 要选出的
     * @param whe where条件
     * @return
     */
    public String queryAccount(String account);
    public String queryUserName(String userName);
    public String queryPassword(String account);
    public String queryIpAddr(String userName);
    public String queryUserNameLog(String account);
    public void updateIpAddr(String account,String ipAddr);
    public boolean reg(User usr);
    public String qFriend(String account);
    public String[] qFriInfo(String account);
    public LinkedList<String> queryFriend(String account);    //添加界面的查找联系人
    public boolean addFriend(String cUserName, String userName);
    public boolean delFriend(String account, String userName);
}
