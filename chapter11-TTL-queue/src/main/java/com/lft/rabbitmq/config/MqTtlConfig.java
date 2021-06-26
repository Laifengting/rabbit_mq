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

import java.util.HashMap;
import java.util.Map;

/**
 * Class Name:      MqConfig
 * Package Name:    com.lft.rabbitmq.config
 * <p>
 * Function: 		A {@code MqConfig} object With Some FUNCTION.
 * Date:            2021-06-26 9:26
 * <p>
 * @author Laifengting / E-mail:laifengting@foxmail.com
 * @version 1.0.0
 * @since JDK 8
 */
@Configuration
public class MqTtlConfig {
    //================================= 交换机 =================================//
    
    /**
     * 声明普通交换机
     */
    @Bean (MqConstant.X_EXCHANGE)
    public DirectExchange xExchange() {
        return new DirectExchange(MqConstant.X_EXCHANGE);
    }
    
    /**
     * 声明死信交换机
     */
    @Bean (MqConstant.Y_DEAD_LETTER_EXCHANGE)
    public DirectExchange yExchange() {
        return new DirectExchange(MqConstant.Y_DEAD_LETTER_EXCHANGE);
    }
    
    //================================= 队列 =================================//
    
    /**
     * 声明普通队列QA
     * 消息过期时间是 10 秒
     */
    @Bean (MqConstant.QA_QUEUE)
    public Queue qaQueue() {
        Map<String, Object> arguments = new HashMap<>(3);
        // 设置死信交换机
        arguments.put("x-dead-letter-exchange", MqConstant.Y_DEAD_LETTER_EXCHANGE);
        // 设置死信RoutingKey
        arguments.put("x-dead-letter-routing-key", MqConstant.DEAD_LETTER_ROUTING_KEY_YD);
        // 设置 TTL 消息过期时间
        arguments.put("x-message-ttl", MqConstant.TTL_TIME_10_SECOND);
        return QueueBuilder.durable(MqConstant.QA_QUEUE).withArguments(arguments).build();
    }
    
    /**
     * 声明普通队列QB
     * 消息过期时间是 40 秒
     */
    @Bean (MqConstant.QB_QUEUE)
    public Queue qbQueue() {
        Map<String, Object> arguments = new HashMap<>(3);
        // 设置死信交换机
        arguments.put("x-dead-letter-exchange", MqConstant.Y_DEAD_LETTER_EXCHANGE);
        // 设置死信RoutingKey
        arguments.put("x-dead-letter-routing-key", MqConstant.DEAD_LETTER_ROUTING_KEY_YD);
        // 设置 TTL 消息过期时间
        arguments.put("x-message-ttl", MqConstant.TTL_TIME_40_SECOND);
        return QueueBuilder.durable(MqConstant.QB_QUEUE).withArguments(arguments).build();
    }
    
    /**
     * 声明普通队列QC
     * 不设置消息过期时间
     */
    @Bean (MqConstant.QC_QUEUE)
    public Queue qcQueue() {
        Map<String, Object> arguments = new HashMap<>(2);
        // 设置死信交换机
        arguments.put("x-dead-letter-exchange", MqConstant.Y_DEAD_LETTER_EXCHANGE);
        // 设置死信RoutingKey
        arguments.put("x-dead-letter-routing-key", MqConstant.DEAD_LETTER_ROUTING_KEY_YD);
        // // 设置 TTL 消息过期时间
        // arguments.put("x-message-ttl", MqConstant.TTL_TIME_40_SECOND);
        return QueueBuilder.durable(MqConstant.QC_QUEUE).withArguments(arguments).build();
    }
    
    /**
     * 声明死信队列QD
     */
    @Bean (MqConstant.QD_DEAD_LETTER_QUEUE)
    public Queue qdQueue() {
        return QueueBuilder.durable(MqConstant.QD_DEAD_LETTER_QUEUE).build();
    }
    
    //================================= 绑定队列与交换机 =================================//
    
    /**
     * QA 队列 绑定到 X 交换机
     */
    @Bean
    public Binding qaBindToxExchange(@Qualifier (MqConstant.QA_QUEUE) Queue qaQueue,
                                     @Qualifier (MqConstant.X_EXCHANGE) DirectExchange xExchange) {
        return BindingBuilder.bind(qaQueue).to(xExchange).with(MqConstant.ROUTING_KEY_XA);
    }
    
    /**
     * QB 队列 绑定到 X 交换机
     */
    @Bean
    public Binding qbBindToxExchange(@Qualifier (MqConstant.QB_QUEUE) Queue qbQueue,
                                     @Qualifier (MqConstant.X_EXCHANGE) DirectExchange xExchange) {
        return BindingBuilder.bind(qbQueue).to(xExchange).with(MqConstant.ROUTING_KEY_XB);
    }
    
    /**
     * QC 队列 绑定到 X 交换机
     */
    @Bean
    public Binding qcBindToxExchange(@Qualifier (MqConstant.QC_QUEUE) Queue qcQueue,
                                     @Qualifier (MqConstant.X_EXCHANGE) DirectExchange xExchange) {
        return BindingBuilder.bind(qcQueue).to(xExchange).with(MqConstant.ROUTING_KEY_XC);
    }
    
    /**
     * QD 死信队列 绑定到 Y 死信交换机
     */
    @Bean
    public Binding qdBindToyExchange(@Qualifier (MqConstant.QD_DEAD_LETTER_QUEUE) Queue qdQueue,
                                     @Qualifier (MqConstant.Y_DEAD_LETTER_EXCHANGE) DirectExchange yExchange) {
        return BindingBuilder.bind(qdQueue).to(yExchange).with(MqConstant.DEAD_LETTER_ROUTING_KEY_YD);
    }
    
}
