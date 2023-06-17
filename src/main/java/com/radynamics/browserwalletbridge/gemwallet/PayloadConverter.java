package com.radynamics.browserwalletbridge.gemwallet;

import com.radynamics.browserwalletbridge.Main;
import org.json.JSONObject;

public class PayloadConverter implements com.radynamics.browserwalletbridge.PayloadConverter {
    public JSONObject toJson(Main.Transaction t) {
        if (t == null) throw new IllegalArgumentException("Parameter 't' cannot be null");

        var json = new JSONObject();
        if (t.getCcy().equals("XRP")) {
            json.put("amount", xrpToDrops(t.getAmount()));
        } else {
            json.put("amount", t.getAmount());
            json.put("currency", t.getCcy());
            json.put("issuer", t.getCcyIssuer());
        }
        json.put("destination", t.destination);
        if (t.destinationTag != null) {
            json.put("destinationTag", t.destinationTag);
        }
        if (t.networkFee != null) {
            json.put("fee", t.networkFee);
        }

        if (t.hasMemo()) {
            throw new IllegalArgumentException("Memos are currently not supported by GemWallet.");
        }

        return json;
    }

    private static Long xrpToDrops(Double amount) {
        final int DROPS_PER_XRP = 1000000;
        return Double.valueOf(amount * DROPS_PER_XRP).longValue();
    }
}
