package com.wesa.elasticsearch.core.utils;

import org.apache.commons.codec.digest.DigestUtils;

public class PasswordUtils {

	public static String md5(String input) {
        if(input == null)
          return null;
        return DigestUtils.md5Hex(input);
    }
	
}
