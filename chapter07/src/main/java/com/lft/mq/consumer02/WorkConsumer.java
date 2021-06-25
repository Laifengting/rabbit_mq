package com.lft.mq.consumer02;

import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * Class Name:      WorkConsumer
 * Package Name:    com.lft.mq.consumer
 * <p>
 * Function: 		A {@code WorkConsumer} object With Some FUNCTION.
 * Date:            2021-05-25 14:57
 * <p>
 * @author Laifengting / E-mail:laifengting@foxmail.com
 * @version 1.0.0
 * @since JDK 8
 */
@Component
public class WorkConsumer {
    
    @RabbitListener (queuesToDeclare = @Queue (name = "work", durable = "true", exclusive = "false", autoDelete = "false"))
    public void receiveMessage1(String message) {
        System.out.println("Message1 = " + message);
    }
    
    @RabbitListener (queuesToDeclare = @Queue (name = "work", durable = "true", exclusive = "false", autoDelete = "false"))
    public void receiveMessage2(String message) {
        System.out.println("Message2 = " + message);
    }
    
}
