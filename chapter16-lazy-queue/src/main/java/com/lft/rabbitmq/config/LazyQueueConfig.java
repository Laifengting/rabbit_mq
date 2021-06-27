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
public class LazyQueueConfig {
    
    /**
     * 交换机
     */
    @Bean (MqConstant.LAZY_EXCHANGE)
    public DirectExchange lazyExchange() {
        return ExchangeBuilder.directExchange(MqConstant.LAZY_EXCHANGE).durable(false).build();
    }
    /**
     * 交换机
     */
    @Bean (MqConstant.LAZY_EXCHANGE2)
    public DirectExchange lazy2Exchange() {
        return ExchangeBuilder.directExchange(MqConstant.LAZY_EXCHANGE2).durable(false).build();
    }
    
    /**
     * 队列
     */
    @Bean (MqConstant.LAZY_QUEUE)
    public Queue lazyQueue() {
        return QueueBuilder
                .durable(MqConstant.LAZY_QUEUE)
                .build();
    }
    /**
     * 队列
     */
    @Bean (MqConstant.LAZY_QUEUE2)
    public Queue lazy2Queue() {
        return QueueBuilder
                .durable(MqConstant.LAZY_QUEUE2)
                .withArgument("x-queue-mode", "lazy")
                .build();
    }
    
    /**
     * 绑定
     */
    @Bean
    public Binding lazyQueueBindToLazyExchange(
            @Qualifier (MqConstant.LAZY_QUEUE) Queue lazyQueue,
            @Qualifier (MqConstant.LAZY_EXCHANGE) DirectExchange lazyExchange
    ) {
        return BindingBuilder.bind(lazyQueue).to(lazyExchange).with(MqConstant.LAZY_ROUTING_KEY);
        
    }
    
    /**
     * 绑定
     */
    @Bean
    public Binding lazy2QueueBindToLazy2Exchange(
            @Qualifier (MqConstant.LAZY_QUEUE2) Queue lazyQueue,
            @Qualifier (MqConstant.LAZY_EXCHANGE2) DirectExchange lazyExchange
    ) {
        return BindingBuilder.bind(lazyQueue).to(lazyExchange).with(MqConstant.LAZY_ROUTING_KEY2);
        
    }
}
