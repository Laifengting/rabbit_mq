package com.lft.mq.consumer01;

import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * Class Name:      HelloConsumer
 * Package Name:    com.lft.mq.consumer
 * <p>
 * Function: 		A {@code HelloConsumer} object With Some FUNCTION.
 * Date:            2021-05-25 14:40
 * <p>
 * @author Laifengting / E-mail:laifengting@foxmail.com
 * @version 1.0.0
 * @since JDK 8
 */
@Component
@RabbitListener (queuesToDeclare = @Queue (name = "hello", durable = "true", exclusive = "false", autoDelete = "false"))
public class HelloConsumer {
    
    @RabbitHandler
    public void receiveMessage(String message) {
        System.out.println("================================ Message = " + message + " ================================");
    }
}
