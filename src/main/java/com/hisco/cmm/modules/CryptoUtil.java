package com.hisco.cmm.modules;

import java.io.UnsupportedEncodingException;
import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;

public class CryptoUtil {
    private static final String charset = "UTF-8";

    /**
     * MD5 Hash 값을 반환한다.
     * 
     * @param str
     *            문자열
     * @return MD5 Hash 값
     */
    public static String getMD5Hash(String str) {
        if (str == null)
            return null;
        else if (str.isEmpty())
            return "";

        try {
            return DigestUtils.md5Hex(str.getBytes(charset));
        } catch (UnsupportedEncodingException uee) {
            throw new RuntimeException(uee.getMessage(), uee);
        }
    }

    /**
     * Base64 인코딩 문자열을 반환한다.
     * 
     * @param str
     *            문자열
     * @return 인코딩 된 문자열
     */
    public static String encodeBase64String(String str) {
        if (str == null)
            return null;
        else if (str.isEmpty())
            return "";

        try {
            return new String(new Base64().encode(str.getBytes(charset)), charset);
        } catch (UnsupportedEncodingException uee) {
            throw new RuntimeException(uee.getMessage(), uee);
        }
    }

    /**
     * Base64 인코딩 문자열을 반환한다.
     * 
     * @param bytes
     *            바이트배열
     * @return 인코딩 된 문자열
     */
    public static String encodeBase64String(byte[] bytes) {
        return encodeBase64String(bytes, charset);
    }

    /**
     * Base64 인코딩 문자열을 반환한다.
     * 
     * @param bytes
     *            바이트배열
     * @param charset
     *            캐릭터셋
     * @return 인코딩 된 문자열
     */
    public static String encodeBase64String(byte[] bytes, String charset) {
        if (bytes == null)
            return null;
        else if (bytes.length <= 0)
            return "";

        try {
            return new String(new Base64().encode(bytes), charset);
        } catch (UnsupportedEncodingException uee) {
            throw new RuntimeException(uee.getMessage(), uee);
        }
    }

    /**
     * Base64 디코딩 문자열을 반환한다.
     * 
     * @param str
     *            문자열
     * @return 디코딩 된 문자열
     */
    public static String decodeBase64String(String str) {
        if (str == null)
            return null;
        else if (str.isEmpty())
            return "";

        try {
            return new String(new Base64().decode(str.getBytes(charset)), charset);
        } catch (UnsupportedEncodingException uee) {
            throw new RuntimeException(uee.getMessage(), uee);
        }
    }

    /**
     * Base64 디코딩 바이트 배열을 반환한다.
     * 
     * @param str
     *            문자열
     * @return 디코딩 된 바이트 배열
     */
    public static byte[] decodeBase64(String str) {
        if (str == null)
            return null;
        else if (str.isEmpty())
            return null;

        try {
            return new Base64().decode(str.getBytes(charset));
        } catch (UnsupportedEncodingException uee) {
            throw new RuntimeException(uee.getMessage(), uee);
        }
    }

    /**
     * AES 암호화.
     * 
     * @param key
     *            비밀키 (128bit)
     * @param bytes
     *            암호화 시킬 바이트 배열
     * @return 암호화 된 바이트 배열
     */
    public static byte[] encodeAES(String key, byte[] bytes) {
        Key k = getAESKey(key);
        String transformation = "AES/ECB/PKCS5Padding";
        try {
            Cipher cipher = Cipher.getInstance(transformation);
            cipher.init(Cipher.ENCRYPT_MODE, k);
            return cipher.doFinal(bytes);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    /**
     * AES 암호화.
     * 
     * @param key
     *            비밀키 (128bit)
     * @param str
     *            암호화 시킬 문자열
     * @return 암호화 된 바이트 배열
     */
    public static byte[] encodeAES(String key, String str) {
        try {
            return encodeAES(key, str.getBytes(charset));
        } catch (UnsupportedEncodingException uee) {
            throw new RuntimeException(uee.getMessage(), uee);
        }
    }

    /**
     * AES 복호화.
     * 
     * @param key
     *            비밀키 (128bit)
     * @param bytes
     *            복호화 시킬 바이트 배열
     * @return 복호화 된 바이트 배열
     */
    public static byte[] decodeAES(String key, byte[] bytes) {
        Key k = getAESKey(key);
        String transformation = "AES/ECB/PKCS5Padding";
        try {
            Cipher cipher = Cipher.getInstance(transformation);
            cipher.init(Cipher.DECRYPT_MODE, k);
            return cipher.doFinal(bytes);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    /**
     * AES 복호화.
     * 
     * @param key
     *            비밀키 (128bit)
     * @param bytes
     *            복호화 시킬 바이트 배열
     * @return 복호화 된 문자열
     */
    public static String decodeAESString(String key, byte[] bytes) {
        try {
            return new String(decodeAES(key, bytes), charset);
        } catch (UnsupportedEncodingException uee) {
            throw new RuntimeException(uee.getMessage());
        }
    }

    /**
     * AES 복호화.
     * 
     * @param key
     * @param text
     * @return
     */
    public static String decodeAESString(String key, String text) {
        if (StringUtil.IsEmpty(text))
            return text;

        try {
            return new String(decodeAES(key, text.getBytes(charset)), charset);
        } catch (UnsupportedEncodingException uee) {
            throw new RuntimeException(uee.getMessage());
        }
    }

    /**
     * DB 저장용 단반향 암호화된 패스워드를 반환한다.
     * 
     * @param inputPassword
     *            입력된 패스워드
     * @return DB 저장용 단반향 암호화된 패스워드
     */
    public static String getEncPassword(String inputPassword) {
        char[] t = getMD5Hash(inputPassword).toCharArray();
        StringBuilder builder = new StringBuilder();
        for (int index = 0; index < t.length; index += 2) {
            builder.append(t[index + 1]);
            builder.append(t[index]);
        }
        return getMD5Hash(builder.toString());
    }

    /**
     * 암호화된 문자열을 반환한다.
     * <br/>
     * Site 공통 비밀키를 이용하여 AES 128bit 암호화.
     * 
     * @param value
     *            문자열
     * @return 암호화된 문자열
     */
    public static String getEncryptValue(String value) {
        if (value == null)
            return null;
        else if (value.isEmpty())
            return "";

        byte[] eb = encodeAES(getSiteSecureKey(), value);
        return encodeBase64String(eb);
    }

    /**
     * 복호화된 문자열을 반환한다.
     * <br/>
     * Site 공통 비밀키를 이용하여 AES 128bit 복호화.
     * 
     * @param value
     *            DB 문자열
     * @return 복호화된 문자열
     */
    public static String getDecryptValue(String value) {
        if (value == null)
            return null;
        else if (value.isEmpty())
            return "";

        byte[] db = decodeBase64(value);
        return decodeAESString(getSiteSecureKey(), db);
    }

    /**
     * 개인별 암호화된 문자열을 반환한다.
     * 
     * @param userId
     *            회원코드
     * @param value
     *            문자열
     * @return 개인별 암호화된 문자열
     */
    public static String getPrivateEncryptValue(String userId, String value) {
        byte[] eb = encodeAES(getUserPrivateKey(userId), value);
        return encodeBase64String(eb);
    }

    /**
     * 개인별 복호화된 문자열을 반환한다.
     * 
     * @param userId
     *            회원코드
     * @param value
     *            DB 문자열
     * @return 개인별 복호화된 문자열
     */
    public static String getPrivateDecryptValue(String userId, String value) {
        byte[] db = decodeBase64(value);
        return decodeAESString(getUserPrivateKey(userId), db);
    }

    // 사이트 비밀키를 반환한다.
    public static String getSiteSecureKey() {
        // return "1234567890123456";
        return "KntoolSiteSecret";
    }

    // 도메인 쿠키 암복호화용 비밀키를 반환한다
    public static String getCookieSecureKey() {
        return "KntoolSecretKey";
    }

    // 회원별 개인키를 반환한다.
    private static String getUserPrivateKey(String userId) {
        String key = getMD5Hash(getMD5Hash(userId));
        if (key == null)
            key = userId;
        char[] keys = key.toCharArray();
        StringBuilder builder = new StringBuilder();
        for (int index = 0; index < keys.length; index += 2) {
            builder.append(keys[index]);
        }
        return builder.toString();
    }

    // AES Key 객체를 생성하여 반환한다.
    private static Key getAESKey(String key) {
        try {
            return new SecretKeySpec(key.getBytes(charset), "AES");
        } catch (UnsupportedEncodingException uee) {
            throw new RuntimeException(uee.getMessage());
        }
    }

    /**
     * PHP에서 비밀번호 암호화 처리(SHA-512 암호화 -> Base64 인코딩)
     * 
     * @param password
     * @throws NoSuchAlgorithmException
     * @throws UnsupportedEncodingException
     */
    /*
     * public static String getPhpPasswordEncode(String password) throws NoSuchAlgorithmException,
     * UnsupportedEncodingException
     * {
     * if (StringUtil.IsEmpty(password)) return null;
     * java.security.MessageDigest md = java.security.MessageDigest.getInstance("SHA-512");
     * md.update(password.trim().getBytes("UTF-8"));
     * return encodeBase64String(md.digest());
     * }
     */
    public static String getPhpPasswordEncode(String password) {
        return getPhpPasswordEncode(password, charset);
    }

    /**
     * PHP에서 비밀번호 암호화 처리(SHA-512 암호화 -> Base64 인코딩)
     * 
     * @param password
     * @param charset
     * @return
     */
    public static String getPhpPasswordEncode(String password, String charset) {
        if (StringUtil.IsEmpty(password))
            return null;

        try {
            java.security.MessageDigest md = java.security.MessageDigest.getInstance("SHA-512");
            md.update(password.trim().getBytes(charset));

            return encodeBase64String(md.digest());
        } catch (Exception e) {
            return password;
        }
    }

    /**
     * AES256 암호화
     * 
     * @param key
     *            암호화키
     * @param text
     *            암호화할 문자
     * @return
     */
    public static String encodeAES256(String key, String text) {
        if (StringUtil.IsEmpty(key) || StringUtil.IsEmpty(text))
            return text;

        try {
            String encrypted = null;
            byte[] source = text.getBytes("UTF-8");

            SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes("UTF-8"), "AES");
            Cipher cipher = Cipher.getInstance("AES/ECB/NoPadding");
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
            int mod = source.length % 16;
            byte[] changeSource = null;

            if (mod != 0) {
                changeSource = new byte[source.length + (16 - mod)];
                System.arraycopy(source, 0, changeSource, 0, source.length);
            } else {
                changeSource = source;
            }

            encrypted = byteArrayToHex(cipher.doFinal(changeSource));

            return encrypted;
        } catch (Exception e) {
            return text;
        }
    }

    /**
     * AES256 복호화
     * 
     * @param key
     *            복호화키
     * @param text
     *            복호화할 문자
     * @return
     */
    public static String decryptAES256(String key, String text) {
        if (StringUtil.IsEmpty(key) || StringUtil.IsEmpty(text))
            return text;

        try {
            String decrypted = null;
            SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes(), "AES");
            Cipher cipher = Cipher.getInstance("AES/ECB/NoPadding");
            cipher.init(Cipher.DECRYPT_MODE, skeySpec);
            decrypted = new String(cipher.doFinal(hexToByteArray(text)), "UTF-8");
            return decrypted.trim();
        } catch (Exception e) {
            return text;
        }
    }

    private static byte[] hexToByteArray(String s) {
        byte[] ret = null;
        if (s != null && s.length() != 0) {
            ret = new byte[s.length() / 2];
            for (int i = 0; i < ret.length; i++) {
                ret[i] = (byte) Integer.parseInt(s.substring(2 * i, 2 * i + 2), 16);
            }
        }
        return ret;
    }

    private static String byteArrayToHex(byte buf[]) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < buf.length; i++) {
            sb.append(String.format("%02X", buf[i]));
        }
        return sb.toString();
    }

    /**
     * AES256 암호화(시간 정보 포함)
     * 
     * @param key
     * @param text
     * @return
     */
    public static String encodeAES256WithExpireTime(String key, String text) {
        if (StringUtil.IsEmpty(key) || StringUtil.IsEmpty(text))
            return text;

        StringBuffer sb = new StringBuffer();
        sb.append(text).append("-").append(System.currentTimeMillis());

        return encodeAES256(key, sb.toString());
    }

    /**
     * AES256 복호화(만료 시간 60초 정보체크)
     * 
     * @param key
     *            암호화 키
     * @param text
     *            암호화 문자열
     * @return
     */
    public static String decryptAES256WithExpireTime(String key, String text) {
        return decryptAES256WithExpireTime(key, text, 60000);
    }

    /**
     * AES256 복호화(만료 시간 정보체크)
     * 
     * @param key
     *            암호화 키
     * @param text
     *            암호화 문자열
     * @param expried_time
     *            만료 시간
     * @return
     */
    public static String decryptAES256WithExpireTime(String key, String text, long expried_time) {
        if (StringUtil.IsEmpty(key) || StringUtil.IsEmpty(text))
            return text;

        String dec = decryptAES256(key, text);
        int idx = dec.lastIndexOf("-");
        if (idx > 0) {
            String dec_text = dec.substring(0, idx);
            long time = StringUtil.String2Long(dec.substring(idx + 1), 0);

            if (System.currentTimeMillis() - time > expried_time || time - System.currentTimeMillis() > expried_time)
                return null;
            else
                return dec_text;
        } else {
            return null;
        }
    }

    /**
     * SHA256 암호화
     * 
     * @param text
     * @return
     */
    /*
     * public static String getSHA256(String text)
     * {
     * try
     * {
     * MessageDigest enc = MessageDigest.getInstance("SHA-256");
     * enc.update(text.getBytes());
     * byte byteData[] = enc.digest();
     * StringBuffer sb = new StringBuffer();
     * for (int i=0, length=byteData.length; i<length; i++)
     * {
     * sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
     * }
     * return sb.toString();
     * }
     * catch (NoSuchAlgorithmException e)
     * {
     * //e.printStackTrace();
     * return text;
     * }
     * }
     */
    /*
     * public static String getSHA256(String text)
     * {
     * return org.apache.commons.codec.digest.DigestUtils.sha256Hex(text);
     * }
     */

    /**
     * SHA384 암호화
     * 
     * @param text
     * @return
     */
    /*
     * public static String getSHA384(String text)
     * {
     * return org.apache.commons.codec.digest.DigestUtils.sha384Hex(text);
     * }
     */

    /**
     * SHA512 암호화
     * 
     * @param text
     * @return
     */
    public static String getSHA512(String text) {
        // return org.apache.commons.codec.digest.DigestUtils.sha512Hex(text);

        if (StringUtil.IsEmpty(text))
            return null;

        try {
            MessageDigest sha = MessageDigest.getInstance("SHA-512");
            sha.update(text.getBytes());

            StringBuffer sb = new StringBuffer();
            byte[] mb = sha.digest();
            for (int i = 0; i < mb.length; i++) {
                byte temp = mb[i];
                String s = Integer.toHexString(new Byte(temp));
                while (s.length() < 2) {
                    s = "0" + s;
                }

                sb.append(s.substring(s.length() - 2));
            }

            return sb.toString();
        } catch (Exception e) {
            return null;
        }
    }
}
