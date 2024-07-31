package com.user.access.management.exceptions;

public class ResourceAlreadyExist extends RuntimeException{
    private static final long serialVersionUID = 1L;

    public ResourceAlreadyExist(String message) {
        super(message);
    }
}

