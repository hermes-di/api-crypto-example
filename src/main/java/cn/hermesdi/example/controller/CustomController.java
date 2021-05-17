package cn.hermesdi.example.controller;

import cn.hermesdi.example.custom.CustomCrypto;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 自定义模式控制器
 */
@RestController
public class CustomController {

    @CustomCrypto(isDecryption = true, isEncryption = true, salt = "123456")
    @PostMapping("/custom")
    public String custom(@RequestBody String text) {
        System.out.println(text);

        return "ABC123456";
    }
}
