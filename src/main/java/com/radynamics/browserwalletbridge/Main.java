package com.radynamics.browserwalletbridge;

import com.radynamics.browserwalletbridge.gemwallet.GemWallet;
import com.radynamics.browserwalletbridge.httpserver.BridgeEventListener;
import com.radynamics.browserwalletbridge.httpserver.EmbeddedServer;

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

        var future = server.sendPayment(t, com.radynamics.browserwalletbridge.gemwallet.PayloadConverter.toJson(t));

        future.join();
        server.stopHttpServer();
    }

    public static class Transaction implements com.radynamics.browserwalletbridge.httpserver.Transaction {
        public double amount;
        public String ccy;
        public String destination;
        public String ccyIssuer;

        @Override
        public double getAmount() {
            return amount;
        }

        @Override
        public String getCcy() {
            return ccy;
        }
    }
}
