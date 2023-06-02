package com.radynamics.browserwalletbridge;

import com.radynamics.browserwalletbridge.httpserver.BridgeEventListener;
import com.radynamics.browserwalletbridge.httpserver.EmbeddedServer;
import com.radynamics.browserwalletbridge.metamask.MetaMask;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException, BridgeException {
        // Example sending XRP using GemWallet
        /*var walletApi = new GemWallet();
        var t = new Transaction();
        t.amount = 1.23;
        t.ccy = "XRP";
        t.destination = "rLWQskMM8EoPxaLsmuQxE5rYeP4uX7dhym";*/

        // Example sending ETH using MetaMask
        var walletApi = new MetaMask();
        var t = new Transaction();
        t.amount = 1.23;
        t.ccy = "ETH";
        t.destination = "0x7C94907F2EBe8797C81c1BD30b534BA985773dFD";

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

        var future = server.sendPayment(t, walletApi.createPayloadConverter().toJson(t));

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
