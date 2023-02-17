package apicore.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

public class Throw {

    private Throw(){}

    public static void any(org.slf4j.Logger logger, HttpStatusCode status, String msg){
            var exception = new ApiException(status, msg);
            logger.error(exception.getMessageWithStatus());
            throw exception;
    }
    
    public static void badRequest(org.slf4j.Logger logger, String msg, boolean condition){
        if( condition ){
            var exception = new ApiException(HttpStatus.BAD_REQUEST, msg);
            logger.error(exception.getMessageWithStatus());
            throw exception;
        }
    }
    
    public static void forbidden(org.slf4j.Logger logger, String msg, boolean condition){
        if( condition ){
            var exception = new ApiException(HttpStatus.FORBIDDEN, msg);
            logger.error(exception.getMessageWithStatus());
            throw exception;
        }
    }

    public static void notFound(org.slf4j.Logger logger, String msg, boolean condition){
        if( condition ){
            var exception = new ApiException(HttpStatus.NOT_FOUND, msg);
            logger.error(exception.getMessageWithStatus());
            throw exception;
        }
    }

    public static void unauthorized(org.slf4j.Logger logger, String msg, boolean condition){
        if( condition ){
            var exception = new ApiException(HttpStatus.UNAUTHORIZED, msg);
            logger.error(exception.getMessageWithStatus());
            throw exception;
        }
    }

    public static void internalServer(org.slf4j.Logger logger, String msg, boolean condition){
        if( condition ){
            var exception = new ApiException(HttpStatus.INTERNAL_SERVER_ERROR, msg);
            logger.error(exception.getMessageWithStatus());
            throw exception;
        }
    }

    public static void internalServer(org.slf4j.Logger logger, Throwable throwable, String msg, boolean condition ){
        if( condition ){
            var exception = new ApiException(HttpStatus.INTERNAL_SERVER_ERROR, throwable, msg);
            logger.error(exception.getMessageWithStatus());
            throw exception;
        }
    }

}
