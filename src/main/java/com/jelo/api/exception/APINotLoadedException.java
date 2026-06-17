package com.jelo.api.exception;

public class APINotLoadedException extends RuntimeException {
    public APINotLoadedException() {
        super("Unable to retrieve JeloAPI because the API is not yet loaded.");
    }
}
