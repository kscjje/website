package com.hisco.cmm.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

public class TossUtil {

    public static String getHashData(Map<String, Object> paramMap) throws NoSuchAlgorithmException {
        // String lgdMERTKEY = EgovProperties.getProperty("tosspayments.mertkey." + (String) paramMap.get("CST_MID"));
        // // 상점키

        StringBuffer sb = new StringBuffer();
        sb.append(paramMap.get("LGD_MID"));
        sb.append(paramMap.get("LGD_OID"));
        sb.append(paramMap.get("LGD_AMOUNT"));
        sb.append(paramMap.get("LGD_TIMESTAMP"));
        sb.append(paramMap.get("mertkey")); // org_optinfo.WEBPAYMENTPW
        // sb.append(lgdMERTKEY);

        return hash(sb);
    }

    public static String getHashDataWithRespCode(Map<String, Object> paramMap) throws NoSuchAlgorithmException {
        // String lgdMERTKEY = EgovProperties.getProperty("tosspayments.mertkey." + (String) paramMap.get("CST_MID"));
        // // 상점키

        StringBuffer sb = new StringBuffer();
        sb.append(paramMap.get("LGD_MID"));
        sb.append(paramMap.get("LGD_OID"));
        sb.append(paramMap.get("LGD_AMOUNT"));
        sb.append(paramMap.get("LGD_RESPCODE"));
        sb.append(paramMap.get("LGD_TIMESTAMP"));
        sb.append(paramMap.get("mertkey")); // org_optinfo.WEBPAYMENTPW

        return hash(sb);
    }

    private static String hash(StringBuffer sb) throws NoSuchAlgorithmException {
        byte[] bNoti = sb.toString().getBytes();
        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] digest = md.digest(bNoti);

        StringBuffer strBuf = new StringBuffer();
        for (int i = 0; i < digest.length; i++) {
            int c = digest[i] & 0xff;
            if (c <= 15) {
                strBuf.append("0");
            }
            strBuf.append(Integer.toHexString(c));
        }

        return strBuf.toString();
    }
}
