package com.lft.mq;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
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
        log.info("创建连接工厂");
        ConnectionFactory connectionFactory = new ConnectionFactory();
        log.info("设置主机地址");
        connectionFactory.setHost("192.168.56.188");
        log.info("设置主机端口");
        connectionFactory.setPort(5672);
        log.info("设置虚拟主机");
        connectionFactory.setVirtualHost("/ems");
        log.info("设置用户名");
        connectionFactory.setUsername("ems");
        log.info("设置密码");
        connectionFactory.setPassword("123456");
        
        Connection connection = null;
        Channel channel = null;
        try {
            log.info("创建连接对象");
            connection = connectionFactory.newConnection();
            log.info("创建连接通道");
            channel = connection.createChannel();
            log.info("通道绑定队列");
            channel.queueDeclare("queue1", false, false, false, null);
            log.info("消费消息");
            // queue 消费消息队列的队列名
            // autoAck 开启消息的自动确认机制
            // consumer 消费时的回调接口
            channel.basicConsume("queue1", true, new DefaultConsumer(channel) {
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties,
                                           // 消息队列中取出的消息
                                           byte[] body)
                        throws IOException {
                    System.out.println("消费到的消息为：" + new String(body));
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        // finally {
        //     if (channel != null) {
        //         try {
        //             // 11. 关闭通道
        //             channel.close();
        //         } catch (Exception e) {
        //             e.printStackTrace();
        //         }
        //     }
        //     if (connection != null) {
        //         try {
        //             // 11. 关闭连接
        //             connection.close();
        //         } catch (Exception e) {
        //             e.printStackTrace();
        //         }
        //     }
        // }
    }
}
