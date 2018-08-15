package com.yang.rabbitmq.spring;

/**
 * @Author: yhy
 * @Date: 2018/8/15 15:40
 * @Version 1.0
 * 消费者指定:在配置文件中进行配置
 */
public class SpringConsumer {
    /**
     * 在配置文件中进行指定这个类以及方法
     * @param msg 从消息队列中获取的消息
     */
    public void consumerTest(String msg) {
        System.out.println("--spring---msg-----" + msg);
    }

}
