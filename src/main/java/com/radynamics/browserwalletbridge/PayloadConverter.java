package com.radynamics.browserwalletbridge;

import org.json.JSONObject;

public interface PayloadConverter {
    JSONObject toJson(Main.Transaction t);
}
