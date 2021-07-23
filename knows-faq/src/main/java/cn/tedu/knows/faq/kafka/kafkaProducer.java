package cn.tedu.knows.faq.kafka;

import cn.tedu.knows.commons.model.Question;
import cn.tedu.knows.commons.vo.Topic;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
@Slf4j
public class kafkaProducer {
    @Resource
    private KafkaTemplate<String ,String > kafkaTemplate;
    private Gson gson=new Gson();

    //编写
    public void sendQuestion(Question question){
        String json=gson.toJson(question);
        kafkaTemplate.send(Topic.QUESTION,json);
        log.debug("发送到消息到kafka:{}",json);
    }
}
