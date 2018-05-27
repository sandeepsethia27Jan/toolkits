package com.interviewtest.exceptions;

/**
 * Created by ssethia on 5/26/2018.
 */
public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String message) {
        super(message);
    }
}
