package com.radynamics.browserwalletbridge;

import org.apache.commons.codec.binary.Hex;

import java.nio.charset.StandardCharsets;
import java.util.Optional;

public final class Utils {
    public static final Integer xrplMainnet = 0;
    public static final Integer xrplTestnet = 1;
    public static final Integer xrplDevnet = 2;
    public static final Integer xahauMainnet = 21337;
    public static final Integer xahauTestnet = 21338;

    public static Optional<String> nativeToken(Integer networkId) {
        if (xrplMainnet.equals(networkId) || xrplTestnet.equals(networkId)) {
            return Optional.of("XRP");
        }
        if (xahauMainnet.equals(networkId) || xahauTestnet.equals(networkId)) {
            return Optional.of("XAH");
        }
        return Optional.empty();
    }

    public static Optional<Integer> networkId(Integer networkId) {
        // For compatibility with existing chains, the NetworkID field must be omitted on any network with a Network ID of 1024 or less,
        // but must be included on any network with a Network ID of 1025 or greater. (https://xrpl.org/transaction-common-fields.html#networkid-field)
        if (networkId >= 1025) {
            return Optional.of(networkId);
        }
        return Optional.empty();
    }

    public static Long xrplXrpToDrops(Double amount) {
        final int DROPS_PER_XRP = 1000000;
        return Double.valueOf(amount * DROPS_PER_XRP).longValue();
    }

    public static String toHex(String plain) {
        return new String(Hex.encodeHex(plain.getBytes(StandardCharsets.UTF_8)));
    }
}
