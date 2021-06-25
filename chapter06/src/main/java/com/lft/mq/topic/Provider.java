package com.lft.mq.topic;

import com.lft.mq.constant.MQConstants;
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
            // 将通道声明指定交换机  参数1：交接机名称  参数2：交换机类型 direct 表示使用路由模式
            channel.exchangeDeclare(MQConstants.EXCHANGE_NAME_2, MQConstants.EXCHANGE_TYPE_TOPIC, true, false, null);
            
            // 发送消息
            channel.basicPublish(MQConstants.EXCHANGE_NAME_2, "user.info", MessageProperties.PERSISTENT_TEXT_PLAIN, (
                    "Hello" +
                            " " +
                            "Topic Type Info Message ")
                    .getBytes());
            channel.basicPublish(MQConstants.EXCHANGE_NAME_2, "user.warn.lft", MessageProperties.PERSISTENT_TEXT_PLAIN, (
                    "Hello" +
                            " " +
                            "Topic Type Warn Message ")
                    .getBytes());
            channel.basicPublish(MQConstants.EXCHANGE_NAME_2, "user.error.lft", MessageProperties.PERSISTENT_TEXT_PLAIN, (
                    "Hello" +
                            " " +
                            "Topic Type Error Message ")
                    .getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 释放资源
        MQUtils.closeResources(channel, connection);
    }
}
