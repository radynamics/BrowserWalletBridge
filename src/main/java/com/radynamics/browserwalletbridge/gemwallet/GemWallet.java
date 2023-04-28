package com.radynamics.browserwalletbridge.gemwallet;

import com.radynamics.browserwalletbridge.BrowserApi;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class GemWallet implements BrowserApi {
    @Override
    public String createSendRequestResponse() {
        var is = getClass().getClassLoader().getResourceAsStream("browserwalletbridge/gemwallet/index.html");
        try {
            return new String(is.readAllBytes(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            System.out.println(e);
            return null;
        }
    }
}
