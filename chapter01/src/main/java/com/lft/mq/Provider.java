package com.lft.mq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Class Name:      Sender
 * Package Name:    com.lft.mq
 * <p>
 * Function: 		A {@code Sender} object With Some FUNCTION.
 * Date:            2021-05-24 21:12
 * <p>
 * @author Laifengting / E-mail:laifengting@foxmail.com
 * @version 1.0.0
 * @since JDK 8
 */
public class Provider {
    private static Logger log = LoggerFactory.getLogger(Provider.class);
    
    /**
     * 消息生产者
     */
    @Test
    public void testSendMessage() {
        // 1. 创建连接 MQ 的连接工厂对象
        log.info("创建连接工厂");
        ConnectionFactory connectionFactory = new ConnectionFactory();
        // 2. 设置连接 RabbitMQ 主机地址
        connectionFactory.setHost("192.168.56.188");
        // 3. 设置连接 RabbitMQ 端口号
        connectionFactory.setPort(5672);
        // 4. 设置连接哪个虚拟主机
        connectionFactory.setVirtualHost("/ems");
        // 5. 设置访问虚拟主机的用户名
        connectionFactory.setUsername("ems");
        // 6. 设置访问虚拟主机的密码
        connectionFactory.setPassword("123456");
        
        Connection connection = null;
        Channel channel = null;
        try {
            // 7. 获取连接对象
            connection = connectionFactory.newConnection();
            // 8. 获取连接通道
            channel = connection.createChannel();
            // 9. 通道绑定对应的消息队列
            // queue 队列名称,如果不存在自动创建
            // durable 如果设置 true 表示声明一个持久型的队列，当服务重启时会保留队列。
            // exclusive 如果设置 true 表示声明一个独占队列，此连接受限。
            // autoDelete 如果设置 true 表示我们声明一个自动删除的队列，服务将在不使用该队列时删除它。
            // arguments 队列的其他属性（额外参数）
            channel.queueDeclare("queue1", false, false, false, null);
            // 10. 发布消息
            // exchange 交换机名称
            // routingKey 路由 key (要发布到哪个队列中)
            // mandatory 强制标志 —— 可选
            // immediate 立刻标志（RabbitMQ 服务器不支持） —— 可选
            // props 消息的其他属性-路由标头等 (传递消息额外设置)
            // body 消息体 (消息的具体内容)
            channel.basicPublish("", "queue1", null, "Hello RabbitMQ".getBytes());
            
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (channel != null) {
                try {
                    // 11. 关闭通道
                    channel.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                try {
                    // 11. 关闭连接
                    connection.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
