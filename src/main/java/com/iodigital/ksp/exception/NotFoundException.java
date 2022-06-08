package com.iodigital.ksp.exception;

public class NotFoundException extends RuntimeException {

    private static final long serialVersionUID = 58438932465811L;

    public NotFoundException(String message) {
        super(message);
    }
}
