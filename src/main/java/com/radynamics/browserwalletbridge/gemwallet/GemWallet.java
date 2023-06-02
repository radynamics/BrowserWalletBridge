package com.radynamics.browserwalletbridge.gemwallet;

import com.radynamics.browserwalletbridge.BrowserApi;

public class GemWallet implements BrowserApi {
    @Override
    public String getContentRoot() {
        return "gemwallet";
    }
}
