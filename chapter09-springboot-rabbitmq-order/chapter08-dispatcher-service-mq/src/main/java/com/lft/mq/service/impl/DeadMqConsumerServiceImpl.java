package com.lft.mq.service.impl;

import com.lft.mq.entity.Order;
import com.lft.mq.service.DeadMqConsumerService;
import com.lft.mq.service.DispatcherService;
import com.lft.mq.util.StringBeanConvert;
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
public class DeadMqConsumerServiceImpl implements DeadMqConsumerService {
    @Autowired
    private DispatcherService dispatcherService;
    private int count = 1;
    
    /**
     * 解决消息重试的几种方案：
     * 1. 控制重试次数 + 死信队列
     * 2. try-catch + 手动 ack
     * 3. try-catch + 手动 ack + 死信队列处理 + 人工干预
     * @param orderMsg
     * @param channel
     * @param correlationData
     * @param tag
     * @throws Exception
     */
    @Override
    @RabbitListener (queues = {"dead.letter.order.fanout.queue"})
    public void messageConsumer(String orderMsg, Channel channel, CorrelationData correlationData,
                                @Header (AmqpHeaders.DELIVERY_TAG) long tag) throws Exception {
        try {
            // 1. 获取消息队列的消息
            System.out.println("收到 MQ 的消息是：" + orderMsg);
            Order order = StringBeanConvert.stringToBean(orderMsg, Order.class);
            
            String orderId = order.getOrderId();
            
            // 2. 派单处理（保存运单）
            // 考虑幂等性问题。
            dispatcherService.dispatch(orderId);
            
            // 3. 手动 ack 告诉 MQ 消息已经正常消费
            channel.basicAck(tag, false);
        } catch (Exception e) {
            // 死信队列也出错。需要人工干预。
            System.out.println("人工干预一下");
            System.out.println("发短信预警");
            System.out.println("把消息转移到别的存储 DB");
            channel.basicNack(tag, false, false);
        }
        
    }
}
