package com.lft.mq.consumer;

import com.lft.mq.util.MQUtils;
import com.rabbitmq.client.CancelCallback;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;
import com.rabbitmq.client.Delivery;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * Class Name:      Consumer01
 * Package Name:    com.lft.mq.consumer
 * <p>
 * Function: 		A {@code Consumer01} object With Some FUNCTION.
 * Date:            2021-06-25 17:48
 * <p>
 * @author Laifengting / E-mail:laifengting@foxmail.com
 * @version 1.0.0
 * @since JDK 8
 */
public class Consumer02 {
    // 死信队列名
    private static final String DEAD_QUEUE = "dead_queue";
    
    public static void main(String[] args) throws IOException {
        Channel channel = MQUtils.getChannel(MQUtils.getConnection());
        System.out.println("C2 已经准备好接收消息。。。");
        
        DeliverCallback deliverCallback = new DeliverCallback() {
            @Override
            public void handle(String consumerTag, Delivery message) throws IOException {
                System.out.println("C2 接收的消息是：" + new String(message.getBody(), StandardCharsets.UTF_8));
            }
        };
        CancelCallback cancelCallback = new CancelCallback() {
            @Override
            public void handle(String consumerTag) throws IOException {
            
            }
        };
        channel.basicConsume(DEAD_QUEUE, true, deliverCallback, cancelCallback);
    }
}
