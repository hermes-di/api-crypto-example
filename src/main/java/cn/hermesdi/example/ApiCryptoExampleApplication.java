package cn.hermesdi.example;

import cn.hermesdi.crypto.annotation.EnableApiCrypto;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


/**
 * ApiCrypto
 *
 * @author hermes-di
 **/
// 开启 ApiCrypto 注解
@EnableApiCrypto
@SpringBootApplication
public class ApiCryptoExampleApplication {

    public static void main(String[] args) {

        SpringApplication.run(ApiCryptoExampleApplication.class, args);

    }

}
