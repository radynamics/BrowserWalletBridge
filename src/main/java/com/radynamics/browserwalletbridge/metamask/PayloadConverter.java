package com.radynamics.browserwalletbridge.metamask;

import com.radynamics.browserwalletbridge.Main;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.math.BigInteger;

public class PayloadConverter implements com.radynamics.browserwalletbridge.PayloadConverter {
    public JSONObject toJson(Main.Transaction t) {
        if (t == null) throw new IllegalArgumentException("Parameter 't' cannot be null");

        var json = new JSONObject();
        var isEth = t.getCcy().equals("ETH");
        if (!isEth) {
            json.put("issuer", t.getCcyIssuer());
        }
        json.put("amount", toWei(t.getAmount()));
        json.put("destination", t.destination);
        if (t.hasMemo()) {
            if (!isEth) {
                throw new IllegalArgumentException("Memos are only available for transactions in ETH. ERC20 payments don't support payloads.");
            }
            json.put("memo", t.memo);
        }

        return json;
    }

    private BigInteger toWei(Double amount) {
        var factor = BigDecimal.TEN.pow(18);
        return BigDecimal.valueOf(amount).multiply(factor).toBigInteger();
    }
}
