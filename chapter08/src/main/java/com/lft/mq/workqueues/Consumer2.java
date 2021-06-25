package com.lft.mq.workqueues;

import com.lft.mq.util.MQUtils;
import com.lft.mq.util.SleepUtil;
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
public class Consumer2 {
    private static Logger log = LoggerFactory.getLogger(Consumer2.class);
    
    public static void main(String[] args) {
        // 获取连接
        Connection connection = MQUtils.getConnection();
        // 获取通道
        Channel channel = MQUtils.getChannel(connection);
        
        try {
            // 通道绑定队列
            channel.queueDeclare("work", true, false, false, null);
            // 指定每次往信道中发送的消息数量，0表示没有限制。如果设置大于 1 表示预取值，每次从信道中获取2条消息。
            channel.basicQos(1);
            // 接收消息
            channel.basicConsume("work", false, new DefaultConsumer(channel) {
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties,
                                           // 消息队列中取出的消息
                                           byte[] body)
                        throws IOException {
                    SleepUtil.sleep(5);
                    System.out.println("Consumer2 消费到的消息为：" + new String(body));
                    // 手动确认，参数1：手动确认消息标识 参数2：是否开启多个消息同时确认。false 每次确认一个。
                    channel.basicAck(envelope.getDeliveryTag(), false);
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
