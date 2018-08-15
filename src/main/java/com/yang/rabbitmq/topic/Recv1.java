package com.yang.rabbitmq.topic;

import com.rabbitmq.client.*;
import com.yang.rabbitmq.util.ConnectionUtils;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @Author: yhy
 * @Date: 2018/8/14 17:24
 * @Version 1.0
 */
public class Recv1 {
    private final static String EXCHANGE_NAME = "test_exchange_topic";
    private final static String QUEUE_NAME = "test_queue_topic_1";
    public static void main(String[] args) {
        Connection connection = null;
        try {
//            创建一个连接
            connection = ConnectionUtils.getConnection();
//            获取信道
            final Channel channel = connection.createChannel();
//            创建队列
            channel.queueDeclare(QUEUE_NAME, false, false, false, null);
//            绑定队列和交换机
            channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, "goods.add");
            channel.basicQos(1);//表示只发送一个消息
            DefaultConsumer consumer = new DefaultConsumer(channel) {
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                    String msg = new String(body, "utf-8");
                    try {
                        System.out.println("--msg1---" + msg);
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } finally {
                        channel.basicAck(envelope.getDeliveryTag(), false);
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
            boolean preACk = false;//
            channel.basicConsume(QUEUE_NAME, preACk, consumer);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
    }

}
