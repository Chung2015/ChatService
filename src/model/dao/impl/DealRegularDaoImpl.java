package model.dao.impl;

import model.dao.DealRegularDao;

public class DealRegularDaoImpl implements DealRegularDao {

    @Override
    public String[] friendList(String info) {
        // TODO Auto-generated method stub
        return info.split(",");
    }

}
