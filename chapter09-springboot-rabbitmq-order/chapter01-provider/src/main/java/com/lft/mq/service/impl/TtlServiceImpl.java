package com.lft.mq.service.impl;

import com.lft.mq.service.TtlService;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * Class Name:      TtlServiceImpl
 * Package Name:    com.lft.mq.service.impl
 * <p>
 * Function: 		A {@code TtlServiceImpl} object With Some FUNCTION.
 * Date:            2021-05-26 20:33
 * <p>
 * @author Laifengting / E-mail:laifengting@foxmail.com
 * @version 1.0.0
 * @since JDK 8
 */
@Service
public class TtlServiceImpl implements TtlService {
    @Autowired
    private RabbitTemplate rabbitTemplate;
    
    @Override
    public void createTtl(String uid, String productId, Integer num) {
        // 2. 保存订单
        String orderId = UUID.randomUUID().toString().replaceAll("-", "");
        System.out.println("订单创建成功：" + orderId);
        // 3. 通过 MQ 来完成消息的分发
        // 参数1：交换机名。    参数2：路由key / 队列名    参数3：消息内容
        String exchangeName = "ttl_dirrect_exchange";
        String routingKey = "exprie";
        rabbitTemplate.convertAndSend(exchangeName, routingKey, orderId);
    }
    
    @Override
    public void createTtlMessage(String uid, String productId, Integer num) {
        // 2. 保存订单
        String orderId = UUID.randomUUID().toString().replaceAll("-", "");
        System.out.println("订单创建成功：" + orderId);
        // 3. 通过 MQ 来完成消息的分发
        // 参数1：交换机名。    参数2：路由key / 队列名    参数3：消息内容
        String exchangeName = "ttl_dirrect_exchange";
        String routingKey = "ttl.message";
        
        // 给消息设置过期时间
        MessagePostProcessor messagePostProcessor = new MessagePostProcessor() {
            @Override
            public Message postProcessMessage(Message message) throws AmqpException {
                // 设置消息过期时间，这里用的是字符串
                message.getMessageProperties().setExpiration("5000");
                // 设置消息内容编码字符集
                message.getMessageProperties().setContentEncoding("UTF-8");
                // content_type
                // content_encoding
                // priority
                // correlation_id
                // reply_to
                // expiration
                // message_id
                // timestamp
                // type
                // user_id
                // app_id
                // cluster_id
                return message;
            }
        };
        rabbitTemplate.convertAndSend(exchangeName, routingKey, orderId, messagePostProcessor);
    }
}
