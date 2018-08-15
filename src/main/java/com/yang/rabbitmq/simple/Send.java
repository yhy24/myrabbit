package com.yang.rabbitmq.simple;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.yang.rabbitmq.util.ConnectionUtils;
import java.io.IOException;

/**
 * @Author: yhy
 * @Date: 2018/8/13 9:51
 * @Version 1.0
 * 发送消息
 */
public class Send {
//    static Logger logger = LoggerFactory.getLogger(Send.class);
    private static final String QUEUE_NAME = "test_simple_queue";

    public static void main(String[] args) {
        System.out.println("-------1------");
        Connection connection = null;
        Channel channel = null;
        try {
//获取一个连接
            System.out.println("--------2------");
            connection = ConnectionUtils.getConnection();
            System.out.println(connection.getClass());
//获取一个通道
            channel = connection.createChannel();
//        创建队列说明
            channel.queueDeclare(QUEUE_NAME, false, false, false, null);
            String msg = "hello simple rabbit";
            channel.basicPublish("", QUEUE_NAME, null, msg.getBytes());
//        logger.info("---send---logger--"+msg);
            System.out.println("---send---" + msg);
        } catch (Exception e) {
            System.out.println("------e-------"+e.getMessage());
        }finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (IOException e) {
                    System.out.println("--e--connection---"+e.getMessage());
                }
            }
            if (channel != null) {
                try {
                    channel.close();
                } catch (Exception e) {
                    System.out.println("---channel---e----"+e.getMessage());
                }
            }
        }

    }
}
