package com.yicj.study.proto.common;

import org.apache.commons.codec.binary.Base64;

public class CommonUtil {

    public static String base64Encode(byte[] input) {
        return Base64.encodeBase64String(input);
    }

    public static byte[] base64Decode(String base64Str) {
        return Base64.decodeBase64(base64Str);
    }

}
