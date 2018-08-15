package com.yang.rabbitmq.workfairdipatch;

import com.rabbitmq.client.*;
import com.yang.rabbitmq.util.ConnectionUtils;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @Author: yhy
 * @Date: 2018/8/14 10:21
 * @Version 1.0
 */
public class Recv1 {
    private static final String QUEUE_NAME = "work_simple_queue";

    public static void main(String[] args) {
        Connection connection = null;

        try {
            //        创建一个连接
            connection = ConnectionUtils.getConnection();
//            创建一个通道
            final Channel channel  = connection.createChannel();
            /**
             * channel.queueDeclare(String s, boolean b, boolean b1, boolean b2, Map<String, Object> map)
             *s：队列的名字    b:durale 持久化
             */
            channel.queueDeclare(QUEUE_NAME, false, false, false, null);
//            每次只处理一个请求
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
                        System.out.println("recv1-e");
                    }finally {
                        System.out.println("recv1-done");
//                        手动回执
                        channel.basicAck(envelope.getDeliveryTag(),false);
                    }
                }
            };
            /**
             * boolean autoAck = true（自动确认模式）
             *一旦rabbitMq将消息分发给消费者，就会从内存中删除 ，数据会丢失
             *
             * boolean autoAck = false（手动模式）
             * 如果一个消费者挂了，会发送费其他的消费者，数据不会丢失。rabbit支持西欧阿西应达，告诉rabbitMQ 这个消息我已经处理完成，你可以删除了，然后rabbitMQ就会从内存中删除
             */
            boolean autoAck = false;//自动应达false  消息应达
            channel.basicConsume(QUEUE_NAME, autoAck, consumer);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }

    }



}
