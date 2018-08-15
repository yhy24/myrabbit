package com.yang.rabbitmq.util;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @Author: yhy
 * @Date: 2018/8/13 9:41
 * @Version 1.0
 * 公共的获取的获取连接的方法
 */
public class ConnectionUtils {
    /**
     * 获取MQ的连接
     * @return
     */
    public static Connection getConnection() throws IOException, TimeoutException {
//        定义一个连接工厂
        ConnectionFactory factory = new ConnectionFactory();
//        设置服务地址
        factory.setHost("192.168.182.142");
        //        设置端口号
        factory.setPort(5672);
        //设置用户名
        factory.setUsername("user_yhy");
//        设置密码
        factory.setPassword("123");

//        设置vhost
        factory.setVirtualHost("/vhost_yhy");
        System.out.println("-------factory-----");
        return factory.newConnection();
    }

}
