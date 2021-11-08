package com.gabriellazar.conferenceapp.exceptions;

public class AttendeeAlreadyExists extends RuntimeException {

    private final String debugMessage;

    public AttendeeAlreadyExists(String message){
        super(message);
        this.debugMessage = message;
    }
}
