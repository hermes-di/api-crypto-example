package cn.hermesdi.example;

import cn.hermesdi.crypto.constants.EncodingType;
import cn.hermesdi.crypto.constants.RSASignatureType;
import cn.hermesdi.crypto.util.CryptoUtil;
import cn.hermesdi.crypto.util.EncodingUtil;
import org.junit.jupiter.api.Test;
import org.springframework.util.Base64Utils;

import javax.crypto.Cipher;
import java.nio.charset.StandardCharsets;
import java.security.KeyPair;

/**
 * RSA 测试
 */
public class RsaTest {

    /**
     * 生成密钥
     */
    @Test
    public void generator() throws Exception {

        KeyPair keyPair = CryptoUtil.generatorRsaKeyPair(1024);

        String privateKey = new String(Base64Utils.encode(keyPair.getPrivate().getEncoded()));

        String publicKey = new String(Base64Utils.encode(keyPair.getPublic().getEncoded()));

        System.out.println("---------- privateKey ----------");
        System.out.println(privateKey);


        System.out.println("---------- publicKey ----------");
        System.out.println(publicKey);
    }

    /**
     * 加密、解密
     */
    @Test
    public void rsa() throws Exception {

        String data = "+*=加密文本测试： ==￥@#% 6{} [http://hermesdi.cn]";

//        KeyPair keyPair = CryptoUtil.generatorRsaKeyPair(1024);
//        String privateKey = new String(Base64Utils.encode(keyPair.getPrivate().getEncoded()));
//        String publicKey = new String(Base64Utils.encode(keyPair.getPublic().getEncoded()));


        String privateKey = "MIICXQIBAAKBgQCNcOo/0Up9LAZ8VUdiECCB9x2nWxvs+z3PJECRDs+2EhytwBYP\n" +
                "q6UkS1eNWB9XHSOSsVHnlHAi67KexqjMtyMmqe746dydI0IfgJfzX/en9HA4Q5KN\n" +
                "rI8eFDoGwKRDbudeC6i5D9VU9SiOUuuoc0fIpkzBkp6jkYst3UdDsnzCcwIDAQAB\n" +
                "AoGACWn8jWbWuGIXxGrAp4w/Pac++AaeYzWNtZ3KFl2QAaRO+FmSnlRkV3gUSboV\n" +
                "y+yKk5btxqYgmJJrfxnl3kpKMow5nI9rS7MpoCv+iaDjsDVVCzyFfMeIxVVVc9nB\n" +
                "nB8BlSgz938SIgHzgmwGiUyf031Q6KSZpF4S0QL7DEL08uECQQD9QGpN4p8dypIk\n" +
                "qa3RdEFUcdQu+U7vMMFLiD4c8mtJj7FZQO8O6oqXF9G2oZibVLTvSCISFvxkiYQC\n" +
                "QKDFkk1LAkEAjvneA2T5iLwFsXULBN6n8btz1aRRz/DMZ1evkS5FgWI6nmKkYcZ1\n" +
                "JYSQ+TIoEpJ4AWlz3dQCRv6qVkTGBWBueQJBAMNzOle/xdOFKkL3XTmggmb8rqhO\n" +
                "Kpg5RYTYsi8+/IQ6TMI4dgdKBgnUDTro/lErD7T4M6tIaCPjaakfXbT9U38CQFUN\n" +
                "Wv7V8INgplpccAOjmHB6vHuY1npGmrHXPb+1sO84yfoyX+syLbn11HAJYEE8FFty\n" +
                "qdSKLBijohzTqKDGLrECQQCbE948tpKH8Uo/t6PXZeKvZW/lcY0RENA+mHEcqUyu\n" +
                "VUdJeK/FN3Q7oFYt1Yjal+Ovhykt8osJagmAV3aehhIk";

        String publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCNcOo/0Up9LAZ8VUdiECCB9x2n\n" +
                "Wxvs+z3PJECRDs+2EhytwBYPq6UkS1eNWB9XHSOSsVHnlHAi67KexqjMtyMmqe74\n" +
                "6dydI0IfgJfzX/en9HA4Q5KNrI8eFDoGwKRDbudeC6i5D9VU9SiOUuuoc0fIpkzB\n" +
                "kp6jkYst3UdDsnzCcwIDAQAB";


        System.out.println("---------- privateKey ----------");
        System.out.println(privateKey);
        System.out.println("---------- publicKey ----------");
        System.out.println(publicKey);

        // 加密
        String encryptData = CryptoUtil.asymmetry(
                "RSA",
                "RSA/ECB/PKCS1Padding",
                Cipher.ENCRYPT_MODE,
                publicKey,
                EncodingType.BASE64,
                data,
                EncodingType.BASE64,
                StandardCharsets.UTF_8
        );

        // 签名
        byte[] bytes = CryptoUtil.resSignature(
                RSASignatureType.MD5withRSA,
                EncodingUtil.decode(EncodingType.BASE64, encryptData, StandardCharsets.UTF_8),
                EncodingUtil.decode(EncodingType.BASE64, privateKey, StandardCharsets.UTF_8)
        );

        String signature = Base64Utils.encodeToString(bytes);

        // 验证签名
        boolean verify = CryptoUtil.resSignatureVerify(
                RSASignatureType.MD5withRSA,
                EncodingUtil.decode(EncodingType.BASE64, encryptData, StandardCharsets.UTF_8),
                EncodingUtil.decode(EncodingType.BASE64, signature, StandardCharsets.UTF_8),
                EncodingUtil.decode(EncodingType.BASE64, publicKey, StandardCharsets.UTF_8)
        );


        // 解密
        String decryptData = CryptoUtil.asymmetry(
                "RSA",
                "RSA/ECB/PKCS1Padding",
                Cipher.DECRYPT_MODE,
                privateKey,
                EncodingType.BASE64,
                encryptData,
                EncodingType.BASE64,
                StandardCharsets.UTF_8
        );

        System.out.println("\n验签结果：" + verify);

        System.out.println("\n加密前数据：" + data);
        System.out.println("\n加密后数据：" + encryptData);
        System.out.println("\n解密后数据：" + decryptData);

        System.out.println("\n对比结果：" + data.equals(decryptData));

    }
}
