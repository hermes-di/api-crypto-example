package cn.hermesdi.example.controller;

import cn.hermesdi.crypto.annotation.EncodingCrypto;
import cn.hermesdi.crypto.annotation.NotDecrypt;
import cn.hermesdi.crypto.annotation.NotEncrypt;
import cn.hermesdi.crypto.constants.EncodingType;
import cn.hermesdi.example.bean.TestBean;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

/**
 * 编码、解码
 */
@RestController
public class EncodingController {

    @NotDecrypt
//    @NotEncrypt
    @EncodingCrypto(encodingType = EncodingType.BASE64)
    @PostMapping("/encoding")
    public TestBean encoding(@RequestBody TestBean req) {

        System.out.println(req.toString());

        TestBean testBean = new TestBean();
        testBean.setAnInt(0);
        testBean.setInteger(1);
        testBean.setString("test string");
        testBean.setStringList(Collections.singletonList("list"));
        testBean.setObjectMap(Collections.singletonMap("test", "map"));

        return testBean;
    }

}
