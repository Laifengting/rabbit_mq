package com.lft.mq.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Class Name:      MqConfig
 * Package Name:    com.lft.mq.config
 * <p>
 * Function: 		A {@code MqConfig} object With Some FUNCTION.
 * Date:            2021-05-27 13:59
 * <p>
 * @author Laifengting / E-mail:laifengting@foxmail.com
 * @version 1.0.0
 * @since JDK 8
 */
@Configuration
public class MqConfig {
    
    //======================= 1. 声明注册交换机 =======================//
    @Bean
    public DirectExchange dlxDirectExchange() {
        String exchangeName = "order_dirrect_exchange";
        return new DirectExchange(exchangeName, true, false);
    }
    
    //======================= 2. 声明队列 =======================//
    @Bean
    public Queue dlxDirectQueue() {
        String queueName = "order.direct.queue";
        return new Queue(queueName, true, false, false);
    }
    
    //======================= 3. 完成绑定关系（队列和交换机完成绑定关系） =======================//
    @Bean
    public Binding dlxQueueToFanoutExchange() {
        // 绑定用队列和交换机
        return BindingBuilder.bind(dlxDirectQueue()).to(dlxDirectExchange()).with("order");
    }
}
