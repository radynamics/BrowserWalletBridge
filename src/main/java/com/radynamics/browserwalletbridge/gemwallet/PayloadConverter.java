package com.radynamics.browserwalletbridge.gemwallet;

import com.radynamics.browserwalletbridge.Main;
import org.json.JSONObject;

public class PayloadConverter {
    public static JSONObject toJson(Main.Transaction t) {
        if (t == null) throw new IllegalArgumentException("Parameter 't' cannot be null");

        var json = new JSONObject();
        json.put("amount", t.amount);
        if (!t.ccy.equals("XRP")) {
            json.put("currency", t.ccy);
            json.put("issuer", t.ccyIssuer);
        }
        json.put("destination", t.destination);

        return json;
    }
}
