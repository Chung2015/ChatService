package model.pojo;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.HashMap;

import model.bo.BusinessBo;

public class MyGlobal {
    private final static int PORTSERVER = 40628;
    private final static int PORTCHAT = 21234;
    private static ServerSocket ssBusiness = null;
    private static ServerSocket ssChat = null;
    
    private static HashMap<String, BusinessBo> bBoMap = null;
    private static HashMap<String, String[]> ipPort = null;
    
    private static byte[] byt = new byte[1024];
    
    private final static String OPSUCCEED ="300";
    private final static String OPFAIL_ACCOUNTSAMED ="301";
    private final static String OPFAIL_NOTHEUSER ="302";
    private final static String OPFAIL_WRONGPASSWORD="303";
    private final static String OPFAIL_ADDFRI="304";
    private final static String OPFAIL_DELFRI="305";
    private final static String OPFAIL_CONNFRI="306";
    private final static String OPFAIL_UNKNOWN ="310";

    private final static String THEEND = "409";
    
    private final static String REQ_REG ="501";
    private final static String REQ_LOG ="502";
    private final static String REQ_QCUSER ="503";
    private final static String REQ_GETFRI ="504";
    private final static String REQ_QFRIEND ="505";
    private final static String REQ_ADDFRIEND ="506";
    private final static String REQ_DELFRIEND ="507";
    private final static String REQ_CONNFRIEND ="508";
    private final static String REQ_CLOSETCP ="509";
    private final static String REQ_CONNFRIENDREACTIVE ="510";

    static {
        try {
            ssBusiness = new ServerSocket(PORTSERVER);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        bBoMap = new HashMap<String, BusinessBo>();
        ipPort = new HashMap<String, String[]>();
    }

    public static byte[] getByt() {
        return byt;
    }

    public static int getPortchat() {
        return PORTCHAT;
    }

    public static ServerSocket getSsBusiness() {
        return ssBusiness;
    }

    public static ServerSocket getSsChat() {
        return ssChat;
    }

    public static HashMap<String, BusinessBo> getbBoMap() {
        return bBoMap;
    }

    public static HashMap<String, String[]> getIpPort() {
        return ipPort;
    }

    public static void setbBoMap(HashMap<String, BusinessBo> bBoMap) {
        MyGlobal.bBoMap = bBoMap;
    }

    public static String getOpsucceed() {
        return OPSUCCEED;
    }

    public static String getOpfailAccountsamed() {
        return OPFAIL_ACCOUNTSAMED;
    }

    public static String getOpfailNotheuser() {
        return OPFAIL_NOTHEUSER;
    }

    public static String getOpfailWrongpassword() {
        return OPFAIL_WRONGPASSWORD;
    }

    public static String getOpfailAddfri() {
        return OPFAIL_ADDFRI;
    }

    public static String getOpfailDelfri() {
        return OPFAIL_DELFRI;
    }

    public static String getOpfailConnfri() {
        return OPFAIL_CONNFRI;
    }

    public static String getOpfailUnknown() {
        return OPFAIL_UNKNOWN;
    }

    public static String getTheend() {
        return THEEND;
    }

    public static String getReqReg() {
        return REQ_REG;
    }

    public static String getReqLog() {
        return REQ_LOG;
    }

    public static String getReqQcuser() {
        return REQ_QCUSER;
    }

    public static String getReqGetfri() {
        return REQ_GETFRI;
    }

    public static String getReqQfriend() {
        return REQ_QFRIEND;
    }

    public static String getReqAddfriend() {
        return REQ_ADDFRIEND;
    }

    public static String getReqDelfriend() {
        return REQ_DELFRIEND;
    }

    public static String getReqConnfriend() {
        return REQ_CONNFRIEND;
    }

    public static String getReqClosetcp() {
        return REQ_CLOSETCP;
    }

    public static String getReqConnfriendreactive() {
        return REQ_CONNFRIENDREACTIVE;
    }

    
    
}
