package com.radynamics.browserwalletbridge;

import com.radynamics.browserwalletbridge.crossmark.Crossmark;
import com.radynamics.browserwalletbridge.httpserver.BridgeEventListener;
import com.radynamics.browserwalletbridge.httpserver.EmbeddedServer;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException, BridgeException {
        // Example sending XRP using GemWallet
        /*var walletApi = new GemWallet();
        var t = new Transaction();
        t.setAmount(1.23, "XRP");
        //t.setAmount(1.23, "USD", "r4u18ao5NTd8nQo9LMEpkbmpRwsJ4vZeua");
        t.destination = "rLWQskMM8EoPxaLsmuQxE5rYeP4uX7dhym";
        t.destinationTag = 123456; // optional
        //t.networkFee = 13; // in drops, optional
        t.setMemo("{ \"data\": \"value 123\", \"price\": 1.23 }", "json");  // optional*/

        // Example sending XRP using Crossmark
        var walletApi = new Crossmark();
        var t = new Transaction();
        t.sender = "rwYb1M4hZcSG6tcAuhvgEwSpsiACKv6BG8";
        t.setAmount(1.23, "XRP");
        //t.setAmount(1.23, "USD", "r4u18ao5NTd8nQo9LMEpkbmpRwsJ4vZeua");
        t.destination = "rLWQskMM8EoPxaLsmuQxE5rYeP4uX7dhym";
        t.destinationTag = 123456; // optional
        t.networkFee = 13; // in drops, optional
        t.setMemo("{ \"data\": \"value 123\", \"price\": 1.23 }", "json");  // optional

        // Example sending with Ethereum using MetaMask
        /*var walletApi = new MetaMask();
        var t = new Transaction();
        // a) send ETH
        t.setAmount(1.23, "ETH");
        // b) send ERC20 token
        //t.setAmount(1.23, "LINK", "0x326C977E6efc84E512bB9C30f76E30c160eD06FB"); // Contract address
        t.destination = "0x7C94907F2EBe8797C81c1BD30b534BA985773dFD";
        t.setMemo("test");*/

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
        private double amount;
        private String ccy;
        private String ccyIssuer;
        public String sender;
        public String destination;
        public Integer destinationTag;
        private String memo;
        private String memoType;
        public Integer networkFee;

        public boolean hasMemo() {
            return memo != null && memo.length() > 0;
        }

        public void setAmount(double amount, String ccy) {
            setAmount(amount, ccy, null);
        }

        public void setAmount(double amount, String ccy, String ccyIssuer) {
            this.amount = amount;
            this.ccy = ccy;
            this.ccyIssuer = ccyIssuer;
        }

        @Override
        public double getAmount() {
            return amount;
        }

        @Override
        public String getCcy() {
            return ccy;
        }

        public String getCcyIssuer() {
            return ccyIssuer;
        }

        public void setMemo(String memo) {
            setMemo(memo, null);
        }

        public void setMemo(String memo, String memoType) {
            this.memo = memo;
            this.memoType = memoType;
        }

        public String getMemo() {
            return memo;
        }

        public String getMemoType() {
            return memoType;
        }
    }
}
