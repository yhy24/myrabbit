package com.yang.rabbitmq.routing;

import com.rabbitmq.client.*;
import com.yang.rabbitmq.util.ConnectionUtils;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @Author: yhy
 * @Date: 2018/8/14 16:52
 * @Version 1.0
 */
public class Recv2 {
    private final static String EXCHANGE_NAME = "test_exchange_direct";
    private final static String QUEUE_NAME = "test_queue_direct_2";

    public static void main(String[] args) {
        Connection connection = null;
        try {
//            创建一个连接
            connection = ConnectionUtils.getConnection();
//            创建一个信道
            final Channel channel = connection.createChannel();
//            创建一个队列
            channel.queueDeclare(QUEUE_NAME, false, false, false, null);
//            绑定队列和路由器
            channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, "error");
            channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, "info");
            channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, "test");
            channel.basicQos(1);//保证只发一次
            /*消费消息*/
            DefaultConsumer consumer = new DefaultConsumer(channel) {
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                    String msg = new String(body, "utf-8");
                    try {
                        System.out.println("------msg2---" + msg);
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } finally {
                        channel.basicAck(envelope.getDeliveryTag(), false);
                    }
                }
            };
            boolean preACK = false;//
            channel.basicConsume(QUEUE_NAME,preACK, consumer);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
    }


}
