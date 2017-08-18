package com.acupt.util;

import com.google.common.base.CaseFormat;
import org.apache.commons.lang3.StringUtils;

/**
 * Created by liujie on 2017/7/18.
 */
public class StringUtil extends StringUtils {

    public static String lowerFirstChar(String str) {
        if (isBlank(str)) {
            return str;
        }
        char ch = str.charAt(0);
        if (ch >= 'A' && ch <= 'Z') {
            char[] chars = str.toCharArray();
            chars[0] = (char) (ch + 32);
            return String.valueOf(chars);
        }
        return str;
    }

    public static String lowerAndLineDiv(String str) {
        if (isBlank(str)) {
            return str;
        }
        char[] chars = str.toCharArray();
        StringBuilder builder = new StringBuilder(chars.length);
        boolean preLowered = false;
        for (int i = 0; i < chars.length; i++) {
            char ch = chars[i];
            if (ch >= 'A' && ch <= 'Z') {
                if (i == 0) {
                    builder.append((char) (ch + 32));
                } else {
                    if (!preLowered) {
                        builder.append('_');
                    }
                    builder.append((char) (ch + 32));
                }
                preLowered = true;
            } else {
                builder.append(ch);
                preLowered = false;
            }
        }
        return builder.toString();
    }

    public static String lowerUnderscore2lowerCamel(String str) {
        if (isBlank(str)) {
            return str;
        }
        return CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, str);
    }

    public static void main(String[] args) {
        System.out.println(lowerUnderscore2lowerCamel("Is_Admin"));
    }
}
