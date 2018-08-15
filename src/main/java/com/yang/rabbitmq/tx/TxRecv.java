package com.yang.rabbitmq.tx;

import com.rabbitmq.client.*;
import com.yang.rabbitmq.util.ConnectionUtils;

import java.io.IOException;

/**
 * @Author: yhy
 * @Date: 2018/8/14 18:09
 * @Version 1.0
 * 事物得我使用
 */
public class TxRecv {
    private final static String QUEQUE_NAME = "test_queue_tx";

    public static void main(String[] args) {
        Connection connection = null;
        Channel channel = null;
        try {
            connection = ConnectionUtils.getConnection();
            channel = connection.createChannel();
            channel.queueDeclare(QUEQUE_NAME, false, false, false, null);

            channel.basicConsume(QUEQUE_NAME,false,new DefaultConsumer(channel){
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                    String msg = new String(body,"utf-8");
                    System.out.println("---msg-tx---"+msg);
                }
            });

        }catch (Exception e){

        }
    }
}
