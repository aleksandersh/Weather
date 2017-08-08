package com.aleksandersh.weather.network;


/**
 * Created by Vladimir Kondenko on 08.08.17.
 */

public class ErrorContainer {

    private int code;

    private String message;

    private Throwable throwable;

    public ErrorContainer(int code, String message, Throwable throwable) {
        this.code = code;
        this.message = message;
        this.throwable = throwable;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public Throwable getThrowable() {
        return throwable;
    }

}
