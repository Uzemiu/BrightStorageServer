package cn.brightstorage.exception;

public class UnauthenticatedException extends RuntimeException{

    public UnauthenticatedException(){super();}

    public UnauthenticatedException(String message){super(message);}
}
