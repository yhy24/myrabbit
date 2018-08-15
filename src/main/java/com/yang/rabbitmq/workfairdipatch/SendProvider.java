package com.yang.rabbitmq.workfairdipatch;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.yang.rabbitmq.util.ConnectionUtils;

import java.io.IOException;

/**
 * @Author: yhy
 * @Date: 2018/8/14 10:07
 * @Version 1.0
 * fiar dipatche 公平模式
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
            /**
             * 每个消费者发送确认消息之前，消息队列不发送消息到消费者，一次只处理一个。
             * 限制发送同一个消费者，不得超过一个消息
             */
            int prePublish = 1;
            channel.basicQos(prePublish);
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




