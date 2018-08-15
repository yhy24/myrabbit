package com.yang.rabbitmq.confirm;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.yang.rabbitmq.util.ConnectionUtils;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @Author: yhy
 * @Date: 2018/8/15 10:30
 * @Version 1.0
 * 异常先不捕获啦
 * 进行单个发送消息
 */
public class SendConfirm {
    private final static String QUEUE_NAME = "test_queue_confirm1";

    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
//        创建一个连接
        Connection connection = ConnectionUtils.getConnection();
//        创建一个信道
        Channel channel = connection.createChannel();
//        创建一个队列
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
//        生产者调用confirmSelect() 将channel设置为confirm模式 注意:必须是一个新的队列
        channel.confirmSelect();   // 进行单条的发送
        String msg = "hello confirm~~~~~~~";
        channel.basicPublish("", QUEUE_NAME, null, msg.getBytes());
        /**
         *  // 进行单条的发送
         * 发送者发送给rabbit，发送成功后rabbit会返回给发送者一条信息
         */
        //确认消息发送成功
        if (!channel.waitForConfirms()) {
            System.out.println("message send failed");
        } else {
            System.out.println("message send ok");
        }
     /*   connection.close();
        channel.close();*/
    }
}
