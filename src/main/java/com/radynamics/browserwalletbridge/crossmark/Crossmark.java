package com.radynamics.browserwalletbridge.crossmark;

import com.radynamics.browserwalletbridge.BrowserApi;

public class Crossmark implements BrowserApi {
    @Override
    public String getContentRoot() {
        return "crossmark";
    }

    @Override
    public PayloadConverter createPayloadConverter() {
        return new PayloadConverter();
    }
}
