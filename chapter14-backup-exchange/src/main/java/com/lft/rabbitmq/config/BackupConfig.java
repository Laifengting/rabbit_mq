package com.lft.rabbitmq.config;

import com.lft.rabbitmq.constant.MqConstant;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Class Name:      ConfirmConfig
 * Package Name:    com.lft.rabbitmq.config
 * <p>
 * Function: 		配置类 备份交换机（高级）.
 * Date:            2021-06-26 13:30
 * <p>
 * @author Laifengting / E-mail:laifengting@foxmail.com
 * @version 1.0.0
 * @since JDK 8
 */
@Configuration
public class BackupConfig {
    
    //================================= 备份交换机 =================================//
    
    /**
     * 声明备份交换机
     */
    @Bean (MqConstant.BACKUP_EXCHANGE)
    public FanoutExchange confirmExchange() {
        return new FanoutExchange(MqConstant.BACKUP_EXCHANGE);
    }
    
    //================================= 队列 =================================//
    
    /**
     * 声明备份队列
     */
    @Bean (MqConstant.BACKUP_QUEUE)
    public Queue backupQueue() {
        return QueueBuilder.durable(MqConstant.BACKUP_QUEUE).build();
    }
    
    /**
     * 声明警告队列
     */
    @Bean (MqConstant.WARNING_QUEUE)
    public Queue warningQueue() {
        return QueueBuilder.durable(MqConstant.WARNING_QUEUE).build();
    }
    
    //================================= 绑定队列与交换机 =================================//
    
    /**
     * 备份队列 绑定到 备份交换机
     */
    @Bean
    public Binding backupQueueBindToBackupExchange(
            @Qualifier (MqConstant.BACKUP_QUEUE) Queue backupQueue,
            @Qualifier (MqConstant.BACKUP_EXCHANGE) FanoutExchange backupExchange) {
        return BindingBuilder.bind(backupQueue).to(backupExchange);
    }
    
    /**
     * 警告队列 绑定到 备份交换机
     */
    @Bean
    public Binding warningQueueBindToBackupExchange(
            @Qualifier (MqConstant.WARNING_QUEUE) Queue warningQueue,
            @Qualifier (MqConstant.BACKUP_EXCHANGE) FanoutExchange backupExchange) {
        return BindingBuilder.bind(warningQueue).to(backupExchange);
    }
    
}
