package cn.hermesdi.example.config;

import cn.hermesdi.crypto.algorithm.*;
import cn.hermesdi.crypto.bean.ApiCryptoBody;
import cn.hermesdi.example.custom.CustomApiCrypto;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

/**
 * 为了减少不必要的消耗，用户可根据自身需求注册不同的实现模式到容器以使用。
 * </p>
 * 目前所有的已实现模式都实现于 `ApiCryptoAlgorithm` 接口，需要什么模式将该接口其对应的实现类 @Bean 注入即可。
 */
@Configuration
public class ApiCryptoConfiguration {

    /**
     * 自定义模式 Bean
     */
    @Bean
    public ApiCryptoAlgorithm customApiCrypto() {
        return new CustomApiCrypto();
    }

    /**
     * 对称性加密解密 Bean
     */
    @Bean
    public ApiCryptoAlgorithm symmetricApiCrypto() {
        SymmetricApiCrypto symmetricApiCrypto = new SymmetricApiCrypto();

        // 自定义请求内容解析
        symmetricApiCrypto.setiApiRequestBody((annotation, inputStream) -> {

            try {

                byte[] byteArr = new byte[inputStream.available()];
                inputStream.read(byteArr);
                String str = new String(byteArr);

                String[] strings = str.split("&");

                return new ApiCryptoBody().setData(strings[0]).setIv(strings[1]);

            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }

        });


        // 自定义响应内容格式
        symmetricApiCrypto.setiApiResponseBody((annotation, cryptoBody) -> {

            String data = cryptoBody.getData();
            String iv = cryptoBody.getIv();

            return data + "&" + iv;

        });


        return symmetricApiCrypto;
    }


    /**
     * 摘要加密 Bean
     */
    @Bean
    public ApiCryptoAlgorithm digestApiCrypto() {
        return new DigestApiCrypto();
    }

    /**
     * 非对称性加密解密 Bean
     */
    @Bean
    public ApiCryptoAlgorithm asymmetryApiCrypto() {
        return new AsymmetryApiCrypto();
    }

    /**
     * 签名 Bean
     */
    @Bean
    public ApiCryptoAlgorithm signatureApiCrypto() {
        return new SignatureApiCrypto();
    }

    /**
     * 内容 编码、解码 Bean
     */
    @Bean
    public ApiCryptoAlgorithm encodingApiCrypto() {
        return new EncodingApiCrypto();
    }
}
