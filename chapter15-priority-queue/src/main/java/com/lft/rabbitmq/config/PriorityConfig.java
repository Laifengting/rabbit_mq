package com.lft.rabbitmq.config;

import com.lft.rabbitmq.constant.MqConstant;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.ExchangeBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Class Name:      PriorityConfig
 * Package Name:    com.lft.rabbitmq.config
 * <p>
 * Function: 		A {@code PriorityConfig} object With Some FUNCTION.
 * Date:            2021-06-27 8:10
 * <p>
 * @author Laifengting / E-mail:laifengting@foxmail.com
 * @version 1.0.0
 * @since JDK 8
 */
@Configuration
public class PriorityConfig {
    
    /**
     * 交换机
     */
    @Bean (MqConstant.PRIORITY_EXCHANGE)
    public DirectExchange priorityExchange() {
        return ExchangeBuilder.directExchange(MqConstant.PRIORITY_EXCHANGE).durable(true).build();
    }
    
    /**
     * 队列
     */
    @Bean (MqConstant.PRIORITY_QUEUE)
    public Queue priorityQueue() {
        return QueueBuilder.durable(MqConstant.PRIORITY_QUEUE).withArgument("x-max-priority", 10).build();
    }
    
    /**
     * 绑定
     */
    @Bean
    public Binding priorityQueueBindToPriorityExchange(
            @Qualifier (MqConstant.PRIORITY_QUEUE) Queue priorityQueue,
            @Qualifier (MqConstant.PRIORITY_EXCHANGE) DirectExchange priorityExchange
    ) {
        return BindingBuilder.bind(priorityQueue).to(priorityExchange).with(MqConstant.PRIORITY_ROUTING_KEY);
        
    }
}
