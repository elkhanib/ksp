package com.iodigital.ksp.exception;

public class RecordNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 58439932465823L;

    public RecordNotFoundException() {
        super("Could not found TedTalk with provided id");
    }
}
