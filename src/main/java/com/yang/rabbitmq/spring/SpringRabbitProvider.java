package com.yang.rabbitmq.spring;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @Author: yhy
 * @Date: 2018/8/15 15:40
 * @Version 1.0
 *spring与rabbit的结合生产者
 */
public class SpringRabbitProvider {
    public static void main(String[] args) throws InterruptedException {

        AbstractApplicationContext ctc = new ClassPathXmlApplicationContext("classpath:spring-rabbit.xml");
//        Rabbit模板 在配置文件中一寄给你指定
        RabbitTemplate template = ctc.getBean(RabbitTemplate.class);
//        发送消息
        template.convertAndSend("hello word~~~~~~spring---哈哈我是生产者");
        Thread.sleep(1000);//指定一秒
//        销毁容器
        ctc.destroy();
    }
}
