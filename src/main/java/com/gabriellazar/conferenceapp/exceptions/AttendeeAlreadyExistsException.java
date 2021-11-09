package com.gabriellazar.conferenceapp.exceptions;

public class AttendeeAlreadyExistsException extends RuntimeException {

    private final String debugMessage;

    public AttendeeAlreadyExistsException(String message){
        super(message);
        this.debugMessage = message;
    }
}
