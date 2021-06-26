package com.lft.rabbitmq.config;

import com.lft.rabbitmq.constant.MqConstant;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.CustomExchange;
import org.springframework.amqp.core.Queue;
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
public class MqDelayedQueueConfig {
    //================================= 交换机 =================================//
    
    /**
     * 声明延迟交换机
     * 基于插件的延迟消息交换机
     */
    @Bean (MqConstant.DELAYED_EXCHANGE)
    public CustomExchange delayedExchange() {
        Map<String, Object> arguments = new HashMap<>();
        arguments.put("x-delayed-type", "direct");
        return new CustomExchange(
                MqConstant.DELAYED_EXCHANGE, // 交换机名称
                MqConstant.X_DELAYED_MESSAGE, // 交换机类型
                true, // 持久化
                true, // 自动删除
                arguments); // 其他参数
    }
    
    //================================= 队列 =================================//
    
    /**
     * 声明延迟队列
     */
    @Bean (MqConstant.DELAYED_QUEUE)
    public Queue delayedQueue() {
        return new Queue(MqConstant.DELAYED_QUEUE);
    }
    
    //================================= 绑定队列与交换机 =================================//
    
    /**
     * 延迟 队列 绑定到 延迟 交换机
     */
    @Bean
    public Binding delayedQueueBindToDelayedExchange(
            @Qualifier (MqConstant.DELAYED_QUEUE) Queue delayedQueue,
            @Qualifier (MqConstant.DELAYED_EXCHANGE) CustomExchange delayedExchange) {
        return BindingBuilder.bind(delayedQueue).to(delayedExchange).with(MqConstant.DELAYED_ROUTING_KEY).noargs();
    }
    
}
