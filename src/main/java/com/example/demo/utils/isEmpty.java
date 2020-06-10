package com.example.demo.utils;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * @author qq491
 */
public class isEmpty {

    public static Boolean isObjectNotEmpty(Object obj) {
        String str = ObjectUtils.identityToString(obj);
        return StringUtils.isNotBlank(str);
    }

}
