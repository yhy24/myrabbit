package com.yang.rabbitmq.confirm;

import com.rabbitmq.client.*;
import com.yang.rabbitmq.util.ConnectionUtils;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @Author: yhy
 * @Date: 2018/8/15 11:10
 * @Version 1.0
 */
public class Recv3 {
    private final static String QUEUE_NAME = "test_queue_confirm3";

    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
//        创建一个连接
        Connection connection = ConnectionUtils.getConnection();
//        创建一个信道
        Channel channel = connection.createChannel();
//        创建一个队列
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);

        DefaultConsumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String msg = new String(body, "utf-8");
                System.out.println("-----msg1-----" + msg);
            }
        };
        channel.basicConsume(QUEUE_NAME, false, consumer);
    }
}
