# BrowserWalletBridge
This Java project lets a user sign cryptocurrency transactions with a browser wallet extension from a desktop software. It starts an embedded HttpServer and start users browser to send requests to a browser wallet extension.

Currently implemented Wallets are:
- [XRPL] [Crossmark](https://crossmark.io)
- [XRPL] [GemWallet](https://gemwallet.app)
- [Ethereum] [MetaMask](https://metamask.io)

### Steps to use this code
See Main.java for a simple example.

### Hints ###
- EmbeddedServer.java runs temporary a HttpServer on 127.0.0.1 listening on port 58905. Use setPort() to use another port before starting the server.
- For every wallet extension a specific html file located in /resources/browserwalletbridge/ is served.
- payload ("tx") is a Base64 encoded json. Its structure is specific for a wallet extension.
- Sent amount "amt" and currency "ccy" are used to better usability
