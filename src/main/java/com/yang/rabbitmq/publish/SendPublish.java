package com.yang.rabbitmq.publish;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.yang.rabbitmq.util.ConnectionUtils;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @Author: yhy
 * @Date: 2018/8/14 14:53
 * @Version 1.0
 */
public class SendPublish {
    private final static String EXCHANGE_NAME = "exchange_test_fanout";

    public static void main(String[] args) {
        Connection connection = null;
        Channel channel = null;
        try {
//            创建一个连接
            connection = ConnectionUtils.getConnection();
//            创建一个通道
            channel = connection.createChannel();
//            创建一个交换机  faunot:分发 不处理路由
            channel.exchangeDeclare(EXCHANGE_NAME,"fanout");
//            发送消息
            String msg = "hello exchange";
            channel.basicPublish(EXCHANGE_NAME, "", null, msg.getBytes());
            System.out.println("---msg--"+msg);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }finally {
           /* if (connection != null) {
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
