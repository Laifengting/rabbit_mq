package com.lft.rabbitmq.consumer;

import com.lft.rabbitmq.constant.MqConstant;
import com.rabbitmq.client.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * Class Name:      DeadLetterQueueConsumer
 * Package Name:    com.lft.rabbitmq.consumer
 * <p>
 * Function: 		A {@code DeadLetterQueueConsumer} object With Some FUNCTION.
 * Date:            2021-06-26 10:02
 * <p>
 * @author Laifengting / E-mail:laifengting@foxmail.com
 * @version 1.0.0
 * @since JDK 8
 */
@Component
public class LazyQueueConsumer {
    
    private static final Logger log = LoggerFactory.getLogger(LazyQueueConsumer.class);
    
    // /**
    //  * 接收备份交换机警告队列的消息
    //  * 备份交换机的优先级最高。当设置了备份交换机时，消息返回不触发。
    //  */
    // @RabbitListener (queues = {MqConstant.LAZY_QUEUE + "111"})
    // public void receiveD(Message message, Channel channel) throws Exception {
    //     String msg = new String(message.getBody());
    //     log.error("收到优先级为 {} 的消息，内容是：{}", message.getMessageProperties().getPriority(), msg);
    // }
}
