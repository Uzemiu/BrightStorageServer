package cn.brightstorage.exception;

public class ForbiddenException extends RuntimeException{

    public ForbiddenException(){this("对不起，你没有权限进行此操作");}

    public ForbiddenException(String message){
        super(message == null ? "对不起，你没有权限进行此操作" : message);
    }
}
