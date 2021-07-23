package cn.tedu.knows.faq.kafka;

import cn.tedu.knows.commons.model.Question;
import cn.tedu.knows.commons.vo.Topic;
import cn.tedu.knows.faq.service.IQuestionService;
import cn.tedu.knows.faq.vo.QuestionVo;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
@Slf4j
public class KafkaConsumer {
    @Resource
    private IQuestionService questionService;

    private Gson gson=new Gson();

    @KafkaListener(topics = Topic.QUESTION)
    public void receive(ConsumerRecord<String,String> record){
        String json=record.value();
        QuestionVo questionVo=gson.fromJson(json,QuestionVo.class);
        log.debug("从kafka接收到了消息{}",questionVo);
        //将接收到的问题对象新增到ES
        //questionService.saveQuestion(questionVo);
    }
}
