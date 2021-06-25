package com.lft.mq.workqueues;

import com.lft.mq.util.MQUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.MessageProperties;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

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
        // 获取连接
        Connection connection = MQUtils.getConnection();
        // 获取通道
        Channel channel = MQUtils.getChannel(connection);
        try {
            // 开启发布确认
            channel.confirmSelect();
            // 通道绑定对应的消息队列
            channel.queueDeclare("work", true, false, false, null);
            for (int i = 1; i <= 20; i++) {
                // 生产消息
                channel.basicPublish("", "work", MessageProperties.PERSISTENT_TEXT_PLAIN, ("Hello Work Queues " + i).getBytes());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        MQUtils.closeResources(channel, connection);
    }
}
