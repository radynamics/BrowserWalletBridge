package com.radynamics.browserwalletbridge;

import com.radynamics.browserwalletbridge.gemwallet.GemWallet;
import com.radynamics.browserwalletbridge.httpserver.BridgeEventListener;
import com.radynamics.browserwalletbridge.httpserver.EmbeddedServer;
import org.json.JSONObject;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException, BridgeException {
        var walletApi = new GemWallet();
        var server = new EmbeddedServer(walletApi);
        server.addBridgeEventListener(new BridgeEventListener() {
            @Override
            public void onPayloadSent(com.radynamics.browserwalletbridge.httpserver.Transaction t, String txHash) {
                System.out.println("Success %s, txHash: %s".formatted(t.getAmount(), txHash));
            }

            @Override
            public void onError(com.radynamics.browserwalletbridge.httpserver.Transaction t, String key, String message) {
                System.out.println("Error %s, key: %s, message: %s".formatted(t.getAmount(), key, message));
            }
        });
        server.start();

        var t = new Transaction();
        t.amount = 1.23;
        t.ccy = "XRP";
        t.destination = "rLWQskMM8EoPxaLsmuQxE5rYeP4uX7dhym";

        var future = server.sendPayment(t, PayloadConverter.toJson(t));

        future.join();
        server.stopHttpServer();
    }

    private static class Transaction implements com.radynamics.browserwalletbridge.httpserver.Transaction {
        public double amount;
        public String ccy;
        public String destination;

        @Override
        public double getAmount() {
            return amount;
        }

        @Override
        public String getCcy() {
            return ccy;
        }
    }

    private static class PayloadConverter {
        public static JSONObject toJson(Transaction t) {
            if (t == null) throw new IllegalArgumentException("Parameter 't' cannot be null");

            var json = new JSONObject();
            json.put("amount", t.amount);
            json.put("destination", t.destination);

            return json;
        }
    }
}
