package com.sen.basic;


import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackages = "com.sen.basic")
public class BasicSender {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(BasicSender.class);
        RabbitAdmin rabbitAdmin = context.getBean(RabbitAdmin.class);
        RabbitTemplate rabbitTemplate = context.getBean(RabbitTemplate.class);

        rabbitTemplate.convertAndSend("", "SEN_BASIC_FIRST_QUEUE", "-------- a direct msg");

        rabbitTemplate.convertAndSend("SEN_BASIC_TOPIC_EXCHANGE", "shanghai.rabbit.teacher", "-------- a topic msg : shanghai.rabbit.teacher");
        rabbitTemplate.convertAndSend("SEN_BASIC_TOPIC_EXCHANGE", "changsha.rabbit.student", "-------- a topic msg : changsha.rabbit.student");

        rabbitTemplate.convertAndSend("SEN_BASIC_FANOUT_EXCHANGE", "", "-------- a fanout msg");


    }
}
