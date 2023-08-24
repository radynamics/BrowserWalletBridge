package com.radynamics.browserwalletbridge;

import org.apache.commons.codec.binary.Hex;

import java.nio.charset.StandardCharsets;

public final class Utils {
    public static Long xrplXrpToDrops(Double amount) {
        final int DROPS_PER_XRP = 1000000;
        return Double.valueOf(amount * DROPS_PER_XRP).longValue();
    }

    public static String toHex(String plain) {
        return new String(Hex.encodeHex(plain.getBytes(StandardCharsets.UTF_8)));
    }
}
