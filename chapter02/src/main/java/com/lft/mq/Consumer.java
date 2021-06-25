package com.lft.mq;

import com.lft.mq.util.MQUtils;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * Class Name:      Receiver
 * Package Name:    com.lft.mq
 * <p>
 * Function: 		A {@code Receiver} object With Some FUNCTION.
 * Date:            2021-05-24 21:12
 * <p>
 * @author Laifengting / E-mail:laifengting@foxmail.com
 * @version 1.0.0
 * @since JDK 8
 */
public class Consumer {
    private static Logger log = LoggerFactory.getLogger(Consumer.class);
    
    public static void main(String[] args) {
        // 获取连接
        Connection connection = MQUtils.getConnection();
        // 获取通道
        Channel channel = MQUtils.getChannel(connection);
        
        try {
            // 通道绑定队列
            channel.queueDeclare("queue1", true, false, true, null);
            // 接收消息
            channel.basicConsume("queue1", true, new DefaultConsumer(channel) {
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties,
                                           // 消息队列中取出的消息
                                           byte[] body)
                        throws IOException {
                    System.out.println("消费到的消息为：" + new String(body));
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
