package com.yang.rabbitmq.confirm;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConfirmListener;
import com.rabbitmq.client.Connection;
import com.yang.rabbitmq.util.ConnectionUtils;

import java.io.IOException;
import java.util.Collections;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.concurrent.TimeoutException;

/**
 * @Author: yhy
 * @Date: 2018/8/15 10:30
 * @Version 1.0
 * 回调函数
 */
public class SendConfirm3 {
    private final static String QUEUE_NAME = "test_queue_confirm3";

    public static void main(String[] args) throws IOException, TimeoutException{
//        创建一个连接
        Connection connection = ConnectionUtils.getConnection();
//        创建一个信道
        Channel channel = connection.createChannel();
//        创建一个队列
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
//        生产者调用confirmSelect() 将channel设置为confirm模式 注意:必须是一个新的队列
        channel.confirmSelect();   // 批量发送

//        未确认的消息标识
        final SortedSet<Long> confirmSet = Collections.synchronizedSortedSet(new TreeSet<Long>());

//      通道添加监听
        channel.addConfirmListener(new ConfirmListener() {
//            没有问题回调这个函数
            public void handleAck(long l, boolean b) throws IOException {
                if (b) {
                    System.out.println("--handleAck-------b---"+b);
                    confirmSet.headSet(l+1).clear();
                } else {
                    System.out.println("--handleAck-------b---"+b);
                    confirmSet.remove(l);
                }
            }
//       有问题回调这个函数
            public void handleNack(long l, boolean b) throws IOException {
                if (b) {
                    System.out.println("--handleNack-------b---"+b);
                    confirmSet.headSet(l + 1).clear();
                } else {
                    System.out.println("--handleNack-------b---"+b);
                    confirmSet.headSet(l).clear();
                }
            }
        });

        String msg = "hello confirm55~66~~~~~~";
        while (true) {
            long seqNo = channel.getNextPublishSeqNo();
            channel.basicPublish("", QUEUE_NAME, null, msg.getBytes());
            confirmSet.add(seqNo);
        }

    }
}
