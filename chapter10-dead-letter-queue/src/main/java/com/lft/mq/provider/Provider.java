package com.lft.mq.provider;

import com.lft.mq.util.MQUtils;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * Class Name:      Provider
 * Package Name:    com.lft.mq.provider
 * <p>
 * Function: 		A {@code Provider} object With Some FUNCTION.
 * Date:            2021-06-25 22:03
 * <p>
 * @author Laifengting / E-mail:laifengting@foxmail.com
 * @version 1.0.0
 * @since JDK 8
 */
public class Provider {
    private static final String NORMAL_EXCHANGE = "normal_exchange";
    
    public static void main(String[] args) throws IOException {
        Channel channel = MQUtils.getChannel(MQUtils.getConnection());
        // 死信消息，设置TTL时间
        // AMQP.BasicProperties properties = new AMQP.BasicProperties().builder().expiration("10000").build();
        AMQP.BasicProperties properties = null;
        
        // 生产消息
        for (int i = 1; i <= 10; i++) {
            String message = "info" + i;
            channel.basicPublish(NORMAL_EXCHANGE, "zhangsan", properties, message.getBytes(StandardCharsets.UTF_8));
        }
    }
}
