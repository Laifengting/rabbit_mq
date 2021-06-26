package com.lft.rabbitmq.consumer;

import com.lft.rabbitmq.constant.MqConstant;
import com.rabbitmq.client.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

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
public class DeadLetterQueueConsumer {
    
    private static Logger log = LoggerFactory.getLogger(DeadLetterQueueConsumer.class);
    
    // 接收死信消息
    @RabbitListener (queues = {MqConstant.QD_DEAD_LETTER_QUEUE})
    public void receiveD(Message message, Channel channel) throws Exception {
        String msg = new String(message.getBody());
        log.info("当前时间：{}，收到死信队列的消息：{}", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date()), msg);
    }
}
