package com.radynamics.browserwalletbridge;

public class BridgeException extends Exception {
    public BridgeException(String errorMessage) {
        super(errorMessage);
    }

    public BridgeException(String errorMessage, Throwable err) {
        super(errorMessage, err);
    }
}
