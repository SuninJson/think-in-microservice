package com.sen.producer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sen.entity.Merchant;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * @Author: qingshan
 * @Date: 2018/10/20 16:52
 * @Description: 咕泡学院，只为更好的你
 */
@Component
@PropertySource("classpath:rabbitmq.properties")
public class RabbitSender {

    @Value("${com.sen.directexchange}")
    private String directExchange;

    @Value("${com.sen.topicexchange}")
    private String topicExchange;

    @Value("${com.sen.fanoutexchange}")
    private String fanoutExchange;

    @Value("${com.sen.directroutingkey}")
    private String directRoutingKey;

    @Value("${com.sen.topicroutingkey1}")
    private String topicRoutingKey1;

    @Value("${com.sen.topicroutingkey2}")
    private String topicRoutingKey2;


    // 自定义的模板，所有的消息都会转换成JSON发送
    @Autowired
    AmqpTemplate amqpTemplate;

    public void send() throws JsonProcessingException {
        Merchant merchant = new Merchant(1001, "a direct msg : 中原镖局", "汉中省解放路266号");
        amqpTemplate.convertAndSend(directExchange, directRoutingKey, merchant);

        amqpTemplate.convertAndSend(topicExchange, topicRoutingKey1, "a topic msg : shanghai.rabbit.teacher");
        amqpTemplate.convertAndSend(topicExchange, topicRoutingKey2, "a topic msg : changsha.rabbit.student");

        // 发送JSON字符串
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(merchant);
        System.out.println(json);
        amqpTemplate.convertAndSend(fanoutExchange, "", json);
    }


}
