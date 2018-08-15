package com.yang.rabbitmq.topic;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.yang.rabbitmq.util.ConnectionUtils;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @Author: yhy
 * @Date: 2018/8/14 17:19
 * @Version 1.0
 */
public class SendPublish {

    private final static String EXCHANGE_NAME = "test_exchange_topic";

    public static void main(String[] args) {
        Connection connection = null;
        Channel channel = null;
        try {
//            获取连接
            connection = ConnectionUtils.getConnection();
//            创建一个信道
            channel = connection.createChannel();
//            创建一个一个交换机
            channel.exchangeDeclare(EXCHANGE_NAME, "topic");
//            发送消息
            String msg = "hello topic~~~~~";
            String routingKey = "goods.delete";//路由键 找到指定的队列
            channel.basicPublish(EXCHANGE_NAME, routingKey, null, msg.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
    }
}
