package com.github.scribejava.core.services;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;

public class CommonsEncoder extends Base64Encoder {

    @Override
    public String encode(byte[] bytes) {
            return Base64.encode(bytes);
    }

    @Override
    public String getType() {
        return "CommonsCodec";
    }

    public static boolean isPresent() {
        try {
            Class.forName("org.apache.commons.codec.binary.Base64");
            return true;
        } catch (ClassNotFoundException e) {
            return false;
        }
    }
}
