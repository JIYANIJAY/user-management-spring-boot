package com.user.access.management.exceptions;

public class ResourceNotExist extends RuntimeException{
    private static final long serialVersionUID = 1L;

    public ResourceNotExist(String message) {
        super(message);
    }
}
