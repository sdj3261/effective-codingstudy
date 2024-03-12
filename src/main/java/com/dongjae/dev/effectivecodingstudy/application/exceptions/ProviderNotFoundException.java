package com.dongjae.dev.effectivecodingstudy.application.exceptions;

public class ProviderNotFoundException extends RuntimeException{
    public ProviderNotFoundException(String message) {
        super(message);
    }
}
