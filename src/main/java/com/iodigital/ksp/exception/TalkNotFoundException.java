package com.iodigital.ksp.exception;

public class TalkNotFoundException extends NotFoundException {

    private static final String MESSAGE = "Ted Talk does not exist.";
    private static final long serialVersionUID = 58439932465823L;

    public TalkNotFoundException() {
        super(MESSAGE);
    }
}
