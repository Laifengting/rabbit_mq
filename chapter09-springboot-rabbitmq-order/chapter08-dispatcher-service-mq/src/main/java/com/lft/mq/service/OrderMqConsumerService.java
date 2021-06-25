package com.lft.mq.service;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;

/**
 * Class Name:      OrderService
 * Package Name:    com.lft.mq.service
 * <p>
 * Function: 		A {@code OrderService} object With Some FUNCTION.
 * Date:            2021-05-27 13:39
 * <p>
 * @author Laifengting / E-mail:laifengting@foxmail.com
 * @version 1.0.0
 * @since JDK 8
 */
public interface OrderMqConsumerService {
    void messageConsumer(
            String orderMsg,
            Channel channel,
            CorrelationData correlationData,
            @Header (AmqpHeaders.DELIVERY_TAG) long tag) throws Exception;
}
