package com.lft.mq.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * Class Name:      MQConfig
 * Package Name:    com.lft.mq.config
 * <p>
 * Function: 		A {@code MQConfig} object With Some FUNCTION.
 * Date:            2021-05-26 17:38
 * <p>
 * @author Laifengting / E-mail:laifengting@foxmail.com
 * @version 1.0.0
 * @since JDK 8
 */
@Configuration
public class MqConfig {
    
    //======================= 1. 声明 交换机 =======================//
    
    /**
     * 普通订单交换机
     */
    @Bean
    public FanoutExchange orderFanoutExchange() {
        String exchangeName = "order_fanout_exchange";
        return new FanoutExchange(exchangeName, true, false);
    }
    
    /**
     * 死信订单交换机
     */
    @Bean
    public FanoutExchange deadLetterOrderFanoutExchange() {
        String exchangeName = "dead_letter_order_fanout_exchange";
        return new FanoutExchange(exchangeName, true, false);
    }
    
    //======================= 2. 声明 队列 =======================//
    
    /**
     * 普通订单消息队列
     */
    @Bean
    public Queue orderFanoutQueue() {
        String queueName = "order.fanout.queue";
        // 添加消息队列的参数，指定死信交换机
        Map<String, Object> args = new HashMap<>();
        args.put("x-dead-letter-exchange", "dead_letter_order_fanout_exchange");
        return new Queue(queueName, true, false, false, args);
    }
    
    /**
     * 死信订单消息队列
     */
    @Bean
    public Queue deadLetterOrderFanoutQueue() {
        String queueName = "dead.letter.order.fanout.queue";
        return new Queue(queueName, true, false, false);
    }
    
    //======================= 3. 完成绑定关系（队列和交换机完成绑定关系） =======================//
    
    /**
     * 普通订单消息队列绑定到普通订单交换机上
     */
    @Bean
    public Binding orderFanoutQueueToOrderFanoutExchange() {
        return BindingBuilder.bind(orderFanoutQueue()).to(orderFanoutExchange());
    }
    
    /**
     * 死信订单消息队列绑定到死信订单交换机上
     */
    @Bean
    public Binding deadLetterOrderFanoutQueueToDeadLetterOrderFanoutExchange() {
        return BindingBuilder.bind(deadLetterOrderFanoutQueue()).to(deadLetterOrderFanoutExchange());
    }
}
