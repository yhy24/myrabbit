package com.yang.rabbitmq.simple;

import com.rabbitmq.client.*;
import com.rabbitmq.client.impl.AMQImpl;
import com.yang.rabbitmq.util.ConnectionUtils;

import java.io.IOException;

/**
 * @Author: yhy
 * @Date: 2018/8/14 9:38
 * @Version 1.0
 */
public class Recv {
    private static final String QUEUE_NAME = "test_simple_queue";
    public static void main(String[] args) {
        Connection connection = null;
        Channel channel = null;
        try {
//            创建一个连接
            connection = ConnectionUtils.getConnection();
//            创建一个通道
            channel = connection.createChannel();
//          创建一个队列
            channel.queueDeclare(QUEUE_NAME, false, false, false, null);
            DefaultConsumer consumer = new DefaultConsumer(channel) {
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                    String msg = new String(body, "utf-8");
                    System.out.println("------msg-----" + msg);
                }
            };
//            监听consumer
            channel.basicConsume(QUEUE_NAME, true, consumer);

        } catch (Exception e) {
        }finally {
            /*if (connection != null) {
                try {
                    connection.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (channel != null) {
                try {
                    channel.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }*/
        }
    }
}
