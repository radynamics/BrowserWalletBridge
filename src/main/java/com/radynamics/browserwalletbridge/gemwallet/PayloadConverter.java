package com.radynamics.browserwalletbridge.gemwallet;

import com.radynamics.browserwalletbridge.Main;
import org.apache.commons.codec.binary.Hex;
import org.json.JSONArray;
import org.json.JSONObject;

import java.nio.charset.StandardCharsets;

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
            json.put("memos", toJsonMemo(t));
        }

        return json;
    }

    private static Long xrpToDrops(Double amount) {
        final int DROPS_PER_XRP = 1000000;
        return Double.valueOf(amount * DROPS_PER_XRP).longValue();
    }

    private static JSONArray toJsonMemo(Main.Transaction t) {
        var memosArray = new JSONArray();
        var memos = new JSONObject();
        memosArray.put(memos);
        var memo = new JSONObject();
        memos.put("memo", memo);
        memo.put("memoType", t.getMemoType() == null ? null : toHex(t.getMemoType()));
        memo.put("memoData", t.getMemo() == null ? null : toHex(t.getMemo()));
        return memosArray;
    }

    private static String toHex(String plain) {
        return new String(Hex.encodeHex(plain.getBytes(StandardCharsets.UTF_8)));
    }
}
