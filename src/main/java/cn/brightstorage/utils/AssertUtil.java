package cn.brightstorage.utils;

import cn.brightstorage.exception.ForbiddenException;
import org.springframework.util.Assert;

public class AssertUtil {

    public static void isAuthorized(boolean expression){
        isTrue(expression, new ForbiddenException());
    }

    public static void isAuthorized(boolean expression, String message){
        isTrue(expression, new ForbiddenException(message));
    }

    public static void notNull(boolean expression, String message){
        notNull(expression, new IllegalArgumentException(message));
    }

    public static void notNull(boolean expression, RuntimeException e){
        if(!expression){
            throw e;
        }
    }

    public static void isTrue(boolean expression, String message){
        isTrue(expression, new IllegalArgumentException(message));
    }

    public static void isTrue(boolean expression, RuntimeException e){
        if(!expression){
            throw e;
        }
    }

}
