package com.lft.mq.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
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
public class MQConfig {
    
    //======================= 1. 声明注册 fanout 模式的交换机 =======================//
    @Bean
    public FanoutExchange fanoutExchange() {
        String exchangeName = "fanout_order_exchange";
        return new FanoutExchange(exchangeName, true, false);
    }
    
    @Bean
    public DirectExchange directExchange() {
        String exchangeName = "direct_logs_exchange";
        return new DirectExchange(exchangeName, true, false);
    }
    
    @Bean
    public TopicExchange topicExchange() {
        String exchangeName = "topic_management_exchange";
        return new TopicExchange(exchangeName, true, false);
    }
    
    //======================= 2. 声明队列 =======================//
    @Bean
    public Queue smsQueue() {
        String queueName = "sms.fanout.queue";
        return new Queue(queueName, true, false, false);
    }
    
    @Bean
    public Queue emailQueue() {
        String queueName = "email.fanout.queue";
        return new Queue(queueName, true, false, false);
    }
    
    @Bean
    public Queue weixinQueue() {
        String queueName = "weixin.fanout.queue";
        return new Queue(queueName, true, false, false);
    }
    
    @Bean
    public Queue infoQueue() {
        String queueName = "info.direct.queue";
        return new Queue(queueName, true, false, false);
    }
    
    @Bean
    public Queue warnQueue() {
        String queueName = "warn.direct.queue";
        return new Queue(queueName, true, false, false);
    }
    
    @Bean
    public Queue errorQueue() {
        String queueName = "error.direct.queue";
        return new Queue(queueName, true, false, false);
    }
    
    @Bean
    public Queue adminQueue() {
        String queueName = "admin.topic.queue";
        return new Queue(queueName, true, false, false);
    }
    
    @Bean
    public Queue testQueue() {
        String queueName = "test.topic.queue";
        return new Queue(queueName, true, false, false);
    }
    
    @Bean
    public Queue devQueue() {
        String queueName = "dev.topic.queue";
        return new Queue(queueName, true, false, false);
    }
    
    //======================= 3. 完成绑定关系（队列和交换机完成绑定关系） =======================//
    @Bean
    public Binding smsQueueToFanoutExchange() {
        return BindingBuilder.bind(smsQueue()).to(fanoutExchange());
    }
    
    @Bean
    public Binding emailQueueToFanoutExchange() {
        return BindingBuilder.bind(emailQueue()).to(fanoutExchange());
    }
    
    @Bean
    public Binding weixingQueueToFanoutExchange() {
        return BindingBuilder.bind(weixinQueue()).to(fanoutExchange());
    }
    
    @Bean
    public Binding infoQueueToFanoutExchange() {
        return BindingBuilder.bind(infoQueue()).to(directExchange()).with("info");
    }
    
    @Bean
    public Binding warnQueueToFanoutExchange() {
        return BindingBuilder.bind(warnQueue()).to(directExchange()).with("warn");
    }
    
    @Bean
    public Binding errorQueueToFanoutExchange() {
        return BindingBuilder.bind(errorQueue()).to(directExchange()).with("error");
    }
    
    @Bean
    public Binding adminQueueToFanoutExchange() {
        return BindingBuilder.bind(adminQueue()).to(topicExchange()).with("admin.#");
    }
    
    @Bean
    public Binding testQueueToFanoutExchange() {
        return BindingBuilder.bind(testQueue()).to(topicExchange()).with("test.#");
    }
    
    @Bean
    public Binding devQueueToFanoutExchange() {
        return BindingBuilder.bind(devQueue()).to(topicExchange()).with("dev.#");
    }
}
