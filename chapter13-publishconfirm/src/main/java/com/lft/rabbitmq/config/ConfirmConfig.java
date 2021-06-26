package com.lft.rabbitmq.config;

import com.lft.rabbitmq.constant.MqConstant;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Class Name:      ConfirmConfig
 * Package Name:    com.lft.rabbitmq.config
 * <p>
 * Function: 		配置类 发布确认（高级）.
 * Date:            2021-06-26 13:30
 * <p>
 * @author Laifengting / E-mail:laifengting@foxmail.com
 * @version 1.0.0
 * @since JDK 8
 */
@Configuration
public class ConfirmConfig {
    
    //================================= 交换机 =================================//
    
    /**
     * 声明发布确认交换机
     */
    @Bean (MqConstant.CONFIRM_EXCHANGE)
    public DirectExchange confirmExchange() {
        return new DirectExchange(MqConstant.CONFIRM_EXCHANGE);
    }
    
    //================================= 队列 =================================//
    
    /**
     * 声明发布确认队列
     */
    @Bean (MqConstant.CONFIRM_QUEUE)
    public Queue confirmQueue() {
        return QueueBuilder.durable(MqConstant.CONFIRM_QUEUE).build();
    }
    
    //================================= 绑定队列与交换机 =================================//
    
    /**
     * 发布确认队列 绑定到 发布确认交换机
     */
    @Bean
    public Binding confirmQueueBindToConfirmExchange(
            @Qualifier (MqConstant.CONFIRM_QUEUE) Queue confirmQueue,
            @Qualifier (MqConstant.CONFIRM_EXCHANGE) DirectExchange confirmExchange) {
        return BindingBuilder.bind(confirmQueue).to(confirmExchange).with(MqConstant.CONFIRM_ROUTING_KEY);
    }
    
}
