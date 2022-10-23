package com.hisco.cmm.util;

/**
 * @Class Name : ScrAES256Util.java
 * @Description : ScrAES256Util
 * @Copyright (c) HISCO㈜ All right reserved.
 *            ------------------------------------------------------------------------
 *            Modification Information
 *            ------------------------------------------------------------------------
 *            수정일 / 수정자 / 수정내용
 *            2020. 10. 16. / 전영석 / 최초작성
 *            ------------------------------------------------------------------------
 */

import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ScrAES256Util {

    private final String iv;
    private final Key keySpec;

    public ScrAES256Util(String strKey) throws UnsupportedEncodingException {

        log.debug("---------------------------------1");

        this.iv = strKey.substring(0, 15);

        log.debug(this.iv);

        log.debug("---------------------------------2");
        byte[] keyBytes = new byte[16];
        log.debug("---------------------------------3");
        byte[] b = strKey.getBytes("UTF-8");
        log.debug("---------------------------------4");
        int len = b.length;
        log.debug("---------------------------------5");
        if (len > keyBytes.length) {
            log.debug("---------------------------------6");
            len = keyBytes.length;
        }

        log.debug("---------------------------------7");

        System.arraycopy(b, 0, keyBytes, 0, len);

        log.debug("---------------------------------8");

        SecretKeySpec keySpec = new SecretKeySpec(keyBytes, "AES");

        log.debug("---------------------------------9");

        this.keySpec = keySpec;

        log.debug("---------------------------------10");

    }

    /**
     * AES256 으로 암호화 한다.
     *
     * @param str
     *            암호화할 문자열
     * @return
     * @throws NoSuchAlgorithmException
     * @throws GeneralSecurityException
     * @throws UnsupportedEncodingException
     */
    public String fn_AesEncrypt(String strValue)
            throws NoSuchAlgorithmException, GeneralSecurityException, UnsupportedEncodingException {

        log.debug("---------------------------------11");

        Cipher c = Cipher.getInstance("AES/CBC/PKCS5Padding");

        log.debug("---------------------------------12");

        c.init(Cipher.ENCRYPT_MODE, keySpec, new IvParameterSpec(iv.getBytes()));

        log.debug("---------------------------------13");

        byte[] encrypted = c.doFinal(strValue.getBytes("UTF-8"));

        log.debug("---------------------------------14");

        String enStr = new String(Base64.encodeBase64(encrypted));

        log.debug("---------------------------------15");

        return enStr;
    }

    /**
     * AES256으로 암호화된 txt 를 복호화한다.
     *
     * @param str
     *            복호화할 문자열
     * @return
     * @throws NoSuchAlgorithmException
     * @throws GeneralSecurityException
     * @throws UnsupportedEncodingException
     */
    public String fn_AesDecrypt(String strValue)
            throws NoSuchAlgorithmException, GeneralSecurityException, UnsupportedEncodingException {

        Cipher c = Cipher.getInstance("AES/CBC/PKCS5Padding");

        c.init(Cipher.DECRYPT_MODE, keySpec, new IvParameterSpec(iv.getBytes()));

        byte[] byteStr = Base64.decodeBase64(strValue.getBytes());

        return new String(c.doFinal(byteStr), "UTF-8");
    }

}