package com.radynamics.browserwalletbridge.crossmark;

import com.radynamics.browserwalletbridge.Main;
import com.radynamics.browserwalletbridge.Utils;
import org.json.JSONArray;
import org.json.JSONObject;

public class PayloadConverter implements com.radynamics.browserwalletbridge.PayloadConverter {
    public JSONObject toJson(Main.Transaction t) {
        if (t == null) throw new IllegalArgumentException("Parameter 't' cannot be null");

        var json = new JSONObject();
        json.put("TransactionType", "Payment");
        json.put("Account", t.sender);
        if (t.getCcy().equals("XRP")) {
            json.put("Amount", String.valueOf(Utils.xrplXrpToDrops(t.getAmount())));
        } else {
            var amt = new JSONObject();
            amt.put("value", String.valueOf(t.getAmount()));
            amt.put("currency", t.getCcy());
            amt.put("issuer", t.getCcyIssuer());
            json.put("Amount", amt);
        }
        json.put("Destination", t.destination);
        if (t.destinationTag != null) {
            json.put("DestinationTag", t.destinationTag);
        }

        if (t.networkFee != null) {
            json.put("Fee", t.networkFee);
        }

        if (t.hasMemo()) {
            json.put("Memos", toJsonMemo(t));
        }

        return json;
    }

    private static JSONArray toJsonMemo(Main.Transaction t) {
        var memosArray = new JSONArray();
        var memos = new JSONObject();
        memosArray.put(memos);
        var memo = new JSONObject();
        memos.put("Memo", memo);
        memo.put("MemoType", t.getMemoType() == null ? null : Utils.toHex(t.getMemoType()));
        memo.put("MemoData", t.getMemo() == null ? null : Utils.toHex(t.getMemo()));
        return memosArray;
    }
}
