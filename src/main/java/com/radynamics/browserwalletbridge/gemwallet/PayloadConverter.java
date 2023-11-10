package com.radynamics.browserwalletbridge.gemwallet;

import com.radynamics.browserwalletbridge.BridgeException;
import com.radynamics.browserwalletbridge.Main;
import com.radynamics.browserwalletbridge.Utils;
import org.json.JSONArray;
import org.json.JSONObject;

public class PayloadConverter implements com.radynamics.browserwalletbridge.PayloadConverter {
    public JSONObject toJson(Main.Transaction t) throws BridgeException {
        if (t == null) throw new IllegalArgumentException("Parameter 't' cannot be null");

        var json = new JSONObject();
        if (t.getCcy().equals("XRP")) {
            json.put("amount", Utils.xrplXrpToDrops(t.getAmount()));
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

        if (t.networkId != null) {
            throw new BridgeException("GemWallet doens't support param NetworkID,");
        }

        return json;
    }

    private static JSONArray toJsonMemo(Main.Transaction t) {
        var memosArray = new JSONArray();
        var memos = new JSONObject();
        memosArray.put(memos);
        var memo = new JSONObject();
        memos.put("memo", memo);
        memo.put("memoType", t.getMemoType() == null ? null : Utils.toHex(t.getMemoType()));
        memo.put("memoData", t.getMemo() == null ? null : Utils.toHex(t.getMemo()));
        return memosArray;
    }
}
