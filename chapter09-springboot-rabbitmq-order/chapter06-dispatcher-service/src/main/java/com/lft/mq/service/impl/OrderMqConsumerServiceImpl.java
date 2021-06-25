package com.lft.mq.service.impl;

import com.lft.mq.service.DispatcherService;
import com.lft.mq.service.OrderMqConsumerService;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;

/**
 * Class Name:      OrderMqConsumerServiceImpl
 * Package Name:    com.lft.mq.service.impl
 * <p>
 * Function: 		A {@code OrderMqConsumerServiceImpl} object With Some FUNCTION.
 * Date:            2021-05-27 13:55
 * <p>
 * @author Laifengting / E-mail:laifengting@foxmail.com
 * @version 1.0.0
 * @since JDK 8
 */
@Service
public class OrderMqConsumerServiceImpl implements OrderMqConsumerService {
    @Autowired
    private DispatcherService dispatcherService;
    
    @Override
    @RabbitListener (queues = {"order.direct.queue"})
    public void messageConsumer(String orderMsg, Channel channel, CorrelationData correlationData,
                                @Header (AmqpHeaders.DELIVERY_TAG) long tag) {
        
    }
}
