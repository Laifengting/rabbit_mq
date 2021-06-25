package com.lft.mq.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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
public class DlxMQConfig {
    
    //======================= 1. 声明注册交换机 =======================//
    @Bean
    public DirectExchange dlxDirectExchange() {
        // 死信交换机
        String exchangeName = "dlx_dirrect_exchange";
        return new DirectExchange(exchangeName, true, false);
    }
    
    //======================= 2. 声明队列 =======================//
    @Bean
    public Queue dlxDirectQueue() {
        // 用于存储死信的队列
        String queueName = "dlx.direct.queue";
        return new Queue(queueName, true, false, false);
    }
    
    //======================= 3. 完成绑定关系（队列和交换机完成绑定关系） =======================//
    @Bean
    public Binding dlxQueueToFanoutExchange() {
        // 绑定用于死信的队列和交换机
        return BindingBuilder.bind(dlxDirectQueue()).to(dlxDirectExchange()).with("dead");// 死信路由key
    }
    
}
