package com.radynamics.browserwalletbridge.httpserver;

public interface BridgeEventListener {
    void onPayloadSent(Transaction t, String txHash);

    void onError(Transaction t, String key, String message);
}
