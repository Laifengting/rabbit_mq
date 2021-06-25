package com.lft.mq.topic;

import com.lft.mq.constant.MQConstants;
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
public class Consumer1 {
    private static Logger log = LoggerFactory.getLogger(Consumer1.class);
    
    public static void main(String[] args) {
        // 获取连接
        Connection connection = MQUtils.getConnection();
        // 获取通道
        Channel channel = MQUtils.getChannel(connection);
        
        try {
            // 通道绑定交换机
            channel.exchangeDeclare(MQConstants.EXCHANGE_NAME_2, "topic", true, false, null);
            // 临时队列
            String queueName = channel.queueDeclare().getQueue();
            // 绑定交换机和临时队列
            // 接收 user 开头的两个单词
            channel.queueBind(queueName, MQConstants.EXCHANGE_NAME_2, "user.*");
            // 接收 三个单词，并且 user 在中间
            channel.queueBind(queueName, MQConstants.EXCHANGE_NAME_2, "*.user.*");
            // 设置每次从通道中获取一个消息
            channel.basicQos(1);
            // 消费消息
            channel.basicConsume(queueName, false, new DefaultConsumer(channel) {
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties,
                                           // 消息队列中取出的消息
                                           byte[] body)
                        throws IOException {
                    System.out.println("Consumer1 消费到的消息为：" + new String(body));
                    // 手动确认，参数1：手动确认消息标识 参数2：是否开启多个消息同时确认。false 每次确认一个。
                    channel.basicAck(envelope.getDeliveryTag(), false);
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
