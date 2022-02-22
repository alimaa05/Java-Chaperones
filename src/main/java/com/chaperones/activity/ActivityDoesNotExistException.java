package com.chaperones.activity;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ActivityDoesNotExistException extends RuntimeException {
    public ActivityDoesNotExistException(String message){super(message);}

}
