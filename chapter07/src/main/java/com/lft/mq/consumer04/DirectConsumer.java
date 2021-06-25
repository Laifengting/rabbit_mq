package com.lft.mq.consumer04;

import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
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
public class DirectConsumer {
    @RabbitListener (bindings = {
            @QueueBinding (
                    // 不指定参数，创建临时队列
                    value = @Queue,
                    // 绑定交换交换机
                    exchange = @Exchange (name = "directs", type = "direct", durable = "true", autoDelete = "false"),
                    key = {"info", "warn", "error"})})
    public void receiveMessage1(String message) {
        System.out.println("Message1 = " + message);
    }
    
    @RabbitListener (bindings = {
            @QueueBinding (
                    // 不指定参数，创建临时队列
                    value = @Queue,
                    // 绑定交换交换机
                    exchange = @Exchange (name = "directs", type = "direct", durable = "true", autoDelete = "false"),
                    key = {"error"})})
    public void receiveMessage2(String message) {
        System.out.println("Message2 = " + message);
    }
    
}
