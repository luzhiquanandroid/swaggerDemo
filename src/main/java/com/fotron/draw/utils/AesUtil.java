package com.fotron.draw.utils;

import com.fotron.draw.core.MyErrorCode;
import com.fotrontimes.core.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import sun.misc.BASE64Decoder;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.Charset;

/**
 * @author: yutong
 * @createDate: 2018/6/29
 * @company: (C) Copyright fotron
 * @since: JDK 1.8
 * @Description:
 */
@Slf4j
public class AesUtil {
    private String key;

    private String iv;

    private AesUtil(String key, String iv) {
        this.key = key;
        this.iv = iv;
    }

    public static AesUtil getInstance(String key, String iv) {
        return new AesUtil(key, iv);
    }

    public String decrypt(String sSrc) {
        try {
            BASE64Decoder base64Decoder = new BASE64Decoder();
            byte[] raw = base64Decoder.decodeBuffer(key);
            SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            IvParameterSpec iv = new IvParameterSpec(base64Decoder.decodeBuffer(this.iv));
            cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
            //先用base64解密
            byte[] encrypted1 = base64Decoder.decodeBuffer(sSrc);
            byte[] original = cipher.doFinal(encrypted1);
            return new String(original, Charset.defaultCharset());
        } catch (Exception ex) {
            log.error("aes 解密失败", ex);
            throw new BusinessException(MyErrorCode.SESSION_KEY_INVALID);
        }
    }
}
