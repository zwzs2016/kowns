package cn.tedu.knows.faq;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class KnowsFaqApplicationTests {

    @Test
    void contextLoads() {
        int i=0;
        int j=i;
        i++;
        System.out.println(j);
    }

}
