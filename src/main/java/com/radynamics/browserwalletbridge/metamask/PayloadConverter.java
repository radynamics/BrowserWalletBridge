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
            throw new IllegalArgumentException("ccy must be 'ETH'");
        }
        json.put("amount", ethToWei(t.amount));
        json.put("destination", t.destination);

        return json;
    }

    private BigInteger ethToWei(Double amount) {
        var weiFactor = BigDecimal.TEN.pow(18);
        return BigDecimal.valueOf(amount).multiply(weiFactor).toBigInteger();
    }
}
