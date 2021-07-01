package cn.tedu.knows.portal.cn.tedu.knows;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
public class protal {

    //加密测试
    @Test
    void encode(){
        //多数流行的加密规则
        PasswordEncoder encoder=new BCryptPasswordEncoder();
        //将密码12345加密后返回加密结果
        //随机加盐技术
        String pwd=encoder.encode("123456");
        System.out.println(pwd);
        //$2a$10$1z2TUa1UDtOpUAUgEd0gd.uGHhvrj3p/UR0oCQJOET.KsEIGyYN/.
    }
    @Test
    void match(){
        PasswordEncoder encoder=new BCryptPasswordEncoder();
        //调用matcher方法验证字符串结果
        //1明文字符串 2加密字符串
        boolean matches = encoder.matches("123456", "$2a$10$1z2TUa1UDtOpUAUgEd0gd.uGHhvrj3p/UR0oCQJOET.KsEIGyYN/.");
        System.out.println(matches);
    }
}
