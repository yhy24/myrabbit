package com.yang.rabbitmq.tx;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.yang.rabbitmq.util.ConnectionUtils;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @Author: yhy
 * @Date: 2018/8/14 17:59
 * @Version 1.0
 * 事物的使用
 */
public class TxSend {
    private final static String QUEQUE_NAME = "test_queue_tx";

    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = null;
        Channel channel = null;
            connection = ConnectionUtils.getConnection();
            channel = connection.createChannel();
            channel.queueDeclare(QUEQUE_NAME, false, false, false, null);
//            发送西消息
        try {
            String msg = "hello tx~1~~~~";
//            int i = 4 / 0;
            channel.txSelect();//开启事物
            channel.basicPublish("", QUEQUE_NAME, null, msg.getBytes());
            channel.txCommit(); //提交
        } catch (Exception e) {
            System.out.println("-----txRollback----");
            channel.txRollback();
        }
        }
    }

