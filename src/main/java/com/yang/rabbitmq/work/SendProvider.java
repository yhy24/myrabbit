package com.yang.rabbitmq.work;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.yang.rabbitmq.util.ConnectionUtils;

import java.io.IOException;

/**
 * @Author: yhy
 * @Date: 2018/8/14 10:07
 * @Version 1.0
 * 生产者的使用
 */
public class SendProvider {
    private static final String QUEUE_NAME = "work_simple_queue";

    public static void main(String[] args) {
        Connection connection = null;
        Channel channel = null;
        try {
//            获取连接
            connection = ConnectionUtils.getConnection();
//            获取通道
            channel = connection.createChannel();
//           创建一个队列
            channel.queueDeclare(QUEUE_NAME, false, false, false, null);
//            向一个队列中添加信息
            int i = 0;
            while (i < 30) {
                String msg = "-msg-:";
                msg = msg + i;
                channel.basicPublish("", QUEUE_NAME, null, msg.getBytes());
                i++;
                System.out.println("--provider--"+msg);
                Thread.sleep(2000);
            }
        } catch (Exception e) {
            System.out.println("---e---" + e.getMessage());
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (channel != null) {
                    try {
                        channel.close();
                    } catch (Exception e) {
                        System.out.println("---e-msg-" + e.getMessage());
                    }
                }
            }
        }
    }
}




