package com.radynamics.browserwalletbridge.gemwallet;

import com.radynamics.browserwalletbridge.Main;
import org.json.JSONObject;

public class PayloadConverter implements com.radynamics.browserwalletbridge.PayloadConverter {
    public JSONObject toJson(Main.Transaction t) {
        if (t == null) throw new IllegalArgumentException("Parameter 't' cannot be null");

        var json = new JSONObject();
        json.put("amount", t.amount);
        if (!t.ccy.equals("XRP")) {
            json.put("currency", t.ccy);
            json.put("issuer", t.ccyIssuer);
        }
        json.put("destination", t.destination);

        if (t.hasMemo()) {
            throw new IllegalArgumentException("Memos are currently not supported by GemWallet.");
        }

        return json;
    }
}
