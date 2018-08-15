package com.yang.rabbitmq.publish;

import com.rabbitmq.client.*;
import com.yang.rabbitmq.util.ConnectionUtils;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @Author: yhy
 * @Date: 2018/8/14 15:08
 * @Version 1.0
 */
public class Revc1 {
    private final static String QUEUE_NAME = "test_queue_fanout_email";
    private final static String EXCHANGE_NAME = "exchange_test_fanout";
    public static void main(String[] args) {
        Connection connection = null;
//        Channel channel = null;
        try {
//            创建一个连接
            connection = ConnectionUtils.getConnection();
//            创建一个信道
            final Channel channel = connection.createChannel();
//            创建一个队列
            channel.queueDeclare(QUEUE_NAME,false,false,false,null);
//            绑定交换机和队列
            channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, "");
            DefaultConsumer consumer = new DefaultConsumer(channel) {
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                    String msg = new String(body, "utf-8");
                    System.out.println("--msg1--" + msg);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } finally {
                        System.out.println("revc1---done");
                        channel.basicAck(envelope.getDeliveryTag(), false);
                    }
                }
            };
            boolean preAck = false;
            channel.basicConsume(QUEUE_NAME, preAck, consumer);//进行监听
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
    }

}
