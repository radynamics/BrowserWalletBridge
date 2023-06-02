package com.radynamics.browserwalletbridge.metamask;

import com.radynamics.browserwalletbridge.BrowserApi;

public class MetaMask implements BrowserApi {
    @Override
    public String getContentRoot() {
        return "metamask";
    }

    @Override
    public PayloadConverter createPayloadConverter() {
        return new PayloadConverter();
    }
}
