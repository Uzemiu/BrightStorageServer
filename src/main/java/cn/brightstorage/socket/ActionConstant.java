package cn.brightstorage.socket;

/**
 * socket行为常量
 * 第1位：1表示服务端向客户端的行为，2表示客户端向服务端的行为
 * 第2位：预留
 * 第3~5位：具体行为代号
 */
public class ActionConstant {

    public static final int COMMON_MESSAGE = 10000;

    public static final int ITEM_UPDATED = 10002;



    public static final int ITEM_GET = 20000;

    public static final int ITEM_SAVE = 20001;

    public static final int ITEM_UPDATE = 20002;

    public static final int ITEM_DELETE = 20003;


}
