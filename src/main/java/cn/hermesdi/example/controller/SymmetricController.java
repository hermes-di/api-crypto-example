package cn.hermesdi.example.controller;

import cn.hermesdi.crypto.annotation.NotDecrypt;
import cn.hermesdi.crypto.annotation.NotEncrypt;
import cn.hermesdi.crypto.annotation.SymmetricCrypto;
import cn.hermesdi.crypto.constants.SymmetricType;
import cn.hermesdi.example.bean.TestBean;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Collections;

/**
 * 对称性加密、解密
 */
@RestController
public class SymmetricController {

    @Resource
    ObjectMapper objectMapper;

    @NotDecrypt
//    @NotEncrypt
    @SymmetricCrypto(type = SymmetricType.AES_CFB_PKCS7_PADDING)
    @PostMapping("/symmetric")
    public String symmetric(@RequestBody TestBean req) throws JsonProcessingException {

        System.out.println(req.toString());

        TestBean testBean = new TestBean();
        testBean.setAnInt(0);
        testBean.setInteger(1);
        testBean.setString("test string");
        testBean.setStringList(Collections.singletonList("list"));
        testBean.setObjectMap(Collections.singletonMap("test", "map"));

        return objectMapper.writeValueAsString(testBean);
    }
}
