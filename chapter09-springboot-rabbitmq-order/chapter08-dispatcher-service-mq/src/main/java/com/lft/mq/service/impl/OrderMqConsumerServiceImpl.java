package com.lft.mq.service.impl;

import com.lft.mq.entity.Order;
import com.lft.mq.service.DispatcherService;
import com.lft.mq.service.OrderMqConsumerService;
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
public class OrderMqConsumerServiceImpl implements OrderMqConsumerService {
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
    @RabbitListener (queues = {"order.fanout.queue"})
    public void messageConsumer(String orderMsg, Channel channel, CorrelationData correlationData,
                                @Header (AmqpHeaders.DELIVERY_TAG) long tag) throws Exception {
        try {
            // 1. 获取消息队列的消息
            System.out.println("收到 MQ 的消息是：" + orderMsg + ", count = " + count++);
            Order order = StringBeanConvert.stringToBean(orderMsg, Order.class);
            
            String orderId = order.getOrderId();
            
            // 2. 派单处理（保存运单）
            dispatcherService.dispatch(orderId);
            
            // 3. 手动 ack 告诉 MQ 消息已经正常消费
            System.out.println(1 / 0); // 模拟异常
            channel.basicAck(tag, false);
        } catch (Exception e) {
            // 如果出现异常的情况下，根据实际的情况去进行重发。
            // 重发一次后，丢失，还是日志，存库根据自己的业务场景决定。
            // 参数1：消息 tag
            // 参数2：false 多条处理
            // 参数3：requeue 是否重发。false 不会重发，会把消息打入到死信队列。true 会死循环的重发，建议如果使用 true 的话，不要加 try / catch 否则就会造成死循环。
            channel.basicNack(tag, false, false);
        }
        
    }
}
