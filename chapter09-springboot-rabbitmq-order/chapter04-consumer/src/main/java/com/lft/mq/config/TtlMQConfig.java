package com.lft.mq.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
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
public class TtlMQConfig {
    
    //======================= 1. 声明注册 fanout 模式的交换机 =======================//
    @Bean
    public DirectExchange ttlDirectExchange() {
        String exchangeName = "ttl_dirrect_exchange";
        // 用户设置交换机参数
        Map<String, Object> map = new HashMap<>();
        // 如果消息到此交换机上不能被路由，则发送这些消息到指定名字的备用交换机上。
        map.put("alternate-exchange", "altx");
        return new DirectExchange(exchangeName, true, false);
    }
    
    //======================= 2. 声明队列 =======================//
    @Bean
    public Queue ttlDirectQueue() {
        String queueName = "ttl.direct.queue";
        // 设置队列参数
        Map<String, Object> map = new HashMap<>();
        // 指定队列中的所有消息过期时间 int 类型
        map.put("x-message-ttl", 5000);
        // // 指定队列自动过期时间 int 类型
        // map.put("x-expires", 10000);
        // 指定队列中能存放的最大消息数量 int 类型
        map.put("x-max-length", 5);
        // // 指定队列所有消息体的所占字节的大小。 int 类型 单位是 字节
        // map.put("x-max-length-bytes", 1024);
        // 队列溢出行为：drop-head、reject-publish 或 reject-publish-dlx
        // Quorum队列只支持 drop-head 和 reject-publish
        // String 类型
        map.put("x-overflow", "reject-publish");
        // 指定死信交换机名，当消息被拒绝或者过期时，会发布到死交换机绑定的队列中，以备后用。
        map.put("x-dead-letter-exchange", "dlx_dirrect_exchange");
        // 指定死信路由 key，如果没有指定会使用原始发布消息时的路由key String 类型（fanout 模式不需要配置这个）
        map.put("x-dead-letter-routing-key", "dead");
        // // 指定队列一次只能让一个消费者来消费消息。 Boolean 类型
        // map.put("x-single-active-consumer", true);
        // // 指定队列的最高优先级级别 int 类型
        // map.put("x-max-priority", 10);
        // // 指定队列的运行模式，default 和 lazy String 类型
        // // 如果是 lazy 延迟模式，队列会将消息尽可能多的保存在磁盘上，以减少内存的使用。
        // // 如果是 default 模式，队列会将消息保留在内存中，以保证尽可能的快的传递消息。
        // map.put("x-queue-mode", "default");
        // // 将队列设置为主位置模式，确定在节点集群上声明时队列主位置所依据的规则。
        // // min-masters：选择master数最少的那个服务节点
        // // client-local：选择与client相连接的那个服务节点
        // // random：随机分配
        // map.put("x-queue-master-locator", "default");
        
        return new Queue(queueName, true, false, false, map);
    }
    
    @Bean
    public Queue ttlMessageDirectQueue() {
        String queueName = "ttl.message.direct.queue";
        return new Queue(queueName, true, false, false);
    }
    
    //======================= 3. 完成绑定关系（队列和交换机完成绑定关系） =======================//
    @Bean
    public Binding ttlQueueToFanoutExchange() {
        return BindingBuilder.bind(ttlDirectQueue()).to(ttlDirectExchange()).with("exprie");
    }
    
    @Bean
    public Binding ttlMessageQueueToFanoutExchange() {
        return BindingBuilder.bind(ttlMessageDirectQueue()).to(ttlDirectExchange()).with("ttl.message");
    }
    
}
