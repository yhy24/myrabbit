package com.yang.rabbitmq.routing;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.yang.rabbitmq.util.ConnectionUtils;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @Author: yhy
 * @Date: 2018/8/14 16:15
 * @Version 1.0
 */
public class SendProvider {
    private final static String EXCHANGE_NAME = "test_exchange_direct";
    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = null;
        Channel channel = null;
        try {
//            创建一个连接
            connection = ConnectionUtils.getConnection();
//            创建一个信道
            channel = connection.createChannel();
//            绑定路由
            channel.exchangeDeclare(EXCHANGE_NAME, "direct");
            String msg = "hello direct~~~~~~";
            String routingKey = "info"; //路由键
            channel.basicPublish(EXCHANGE_NAME, routingKey, null, msg.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }finally {
           /* connection.close();
            channel.close();*/
        }


    }
}
