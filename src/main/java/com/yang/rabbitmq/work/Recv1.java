package com.yang.rabbitmq.work;

import com.rabbitmq.client.*;
import com.yang.rabbitmq.util.ConnectionUtils;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @Author: yhy
 * @Date: 2018/8/14 10:21
 * @Version 1.0
 * 消费者1
 */
public class Recv1 {
    private static final String QUEUE_NAME = "work_simple_queue";

    public static void main(String[] args) {
        Connection connection = null;
        Channel channel = null;
        try {
            //        创建一个连接
            connection = ConnectionUtils.getConnection();
//            创建一个通道
            channel = connection.createChannel();
            channel.queueDeclare(QUEUE_NAME, false, false, false, null);
//            每次只处理一个消费者
            channel.basicQos(1);
//            创建一个消费者
            DefaultConsumer consumer = new DefaultConsumer(channel) {
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                    String msg = new String(body, "utf-8");
                    System.out.println("---msg-recv1--" + msg);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        System.out.println("recv1-done");
                    }
                }
            };
            boolean autoAck = true;
            channel.basicConsume(QUEUE_NAME, autoAck, consumer);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }

    }



}
