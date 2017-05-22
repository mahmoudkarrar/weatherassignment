package com.weather.app.service;

/**
 * Created by Mahmoud.Fathy on 5/21/2017.
 */
public class ServiceException extends RuntimeException{


    public ServiceException(String message) {
        super(message);
    }

    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }

}
