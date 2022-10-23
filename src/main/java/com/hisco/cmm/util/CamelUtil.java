package com.hisco.cmm.util;

/**
 * Camel 형태의 Utility
 * 
 * @author 전영석
 * @since 2020.08.07
 * @version 1.0, 2020.08.07
 *          ------------------------------------------------------------------------
 *          작성자 일자 내용
 *          ------------------------------------------------------------------------
 *          전영석 2020.08.07 최초작성
 */

public class CamelUtil {

    public static String convert2CamelCase(String underScore) {

        if ((underScore.indexOf('_') < 0) && (Character.isLowerCase(underScore.charAt(0)))) {
            return underScore;
        }

        StringBuilder sbResult = new StringBuilder();

        boolean boolNextUpper = false;
        int intLen = underScore.length();

        for (int i = 0; i < intLen; i++) {

            char currentChar = underScore.charAt(i);
            if (currentChar == '_') {
                boolNextUpper = true;
            } else if (boolNextUpper) {
                sbResult.append(Character.toUpperCase(currentChar));
                boolNextUpper = false;
            } else {
                sbResult.append(Character.toLowerCase(currentChar));
            }
        }

        return sbResult.toString();
    }
}