package apicore.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

public class ApiException extends RuntimeException{

    private final HttpStatusCode httpStatus;

    public ApiException(HttpStatusCode httpStatus,  String message) {
        super(message);
        this.httpStatus = httpStatus;
    }

    public ApiException(HttpStatus httpStatus, Throwable throwable, String message) {
        super(message, throwable);
        this.httpStatus = httpStatus;
    }

    public HttpStatusCode getHttpStatus() {
        return httpStatus;
    }

    public String getMessageWithStatus() {
        return  (httpStatus == null ? HttpStatus.INTERNAL_SERVER_ERROR : httpStatus.value())  + " - " + getMessage();
    }

    
}
