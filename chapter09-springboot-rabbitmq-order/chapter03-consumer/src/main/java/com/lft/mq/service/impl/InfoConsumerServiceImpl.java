package com.lft.mq.service.impl;

import com.lft.mq.service.InfoConsumerService;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

/**
 * Class Name:      EmailConsumerServiceImpl
 * Package Name:    com.lft.mq.service.impl
 * <p>
 * Function: 		A {@code EmailConsumerServiceImpl} object With Some FUNCTION.
 * Date:            2021-05-26 18:02
 * <p>
 * @author Laifengting / E-mail:laifengting@foxmail.com
 * @version 1.0.0
 * @since JDK 8
 */
@Service
@RabbitListener (queues = {"info.direct.queue"})
public class InfoConsumerServiceImpl implements InfoConsumerService {
    @RabbitHandler
    public void receiveMessage(String message) {
        System.out.println("InfoConsumer 收到了 info 类型的日志信息：" + message);
    }
}
