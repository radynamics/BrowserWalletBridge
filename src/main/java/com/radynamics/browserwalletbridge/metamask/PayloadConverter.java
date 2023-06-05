package com.radynamics.browserwalletbridge.metamask;

import com.radynamics.browserwalletbridge.Main;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.math.BigInteger;

public class PayloadConverter implements com.radynamics.browserwalletbridge.PayloadConverter {
    public JSONObject toJson(Main.Transaction t) {
        if (t == null) throw new IllegalArgumentException("Parameter 't' cannot be null");

        var json = new JSONObject();
        if (!t.ccy.equals("ETH")) {
            json.put("issuer", t.ccyIssuer);
        }
        json.put("amount", toWei(t.amount));
        json.put("destination", t.destination);

        return json;
    }

    private BigInteger toWei(Double amount) {
        var factor = BigDecimal.TEN.pow(18);
        return BigDecimal.valueOf(amount).multiply(factor).toBigInteger();
    }
}
