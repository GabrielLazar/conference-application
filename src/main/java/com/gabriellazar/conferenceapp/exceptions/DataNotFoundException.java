package com.gabriellazar.conferenceapp.exceptions;

public class DataNotFoundException extends RuntimeException{

    private final String debugMessage;

    public DataNotFoundException(String message){
        super(message);
        this.debugMessage = message;
    }
}
