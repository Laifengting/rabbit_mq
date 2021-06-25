package com.lft.mq.service.impl;

import com.lft.mq.entity.Order;
import com.lft.mq.service.MqService;
import com.lft.mq.util.StringBeanConvert;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

/**
 * Class Name:      MqServiceImpl
 * Package Name:    com.lft.mq.service.impl
 * <p>
 * Function: 		A {@code MqServiceImpl} object With Some FUNCTION.
 * Date:            2021-05-27 16:48
 * <p>
 * @author Laifengting / E-mail:laifengting@foxmail.com
 * @version 1.0.0
 * @since JDK 8
 */
@Service
public class MqServiceImpl implements MqService {
    
    @Autowired
    private RabbitTemplate rabbitTemplate;
    
    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    /**
     * //   @PostConstruct 注解好多人以为是 Spring 提示的，其实是 Java 自己的注解。
     * Java 中该注解的说明：@PostConstruct 该注解用来修改一个非静态的 void() 方法。
     * 被 @PostConstruct 修饰的方法会在服务器加载 Servlet 的时候运行，并且只会被服务器执行一次。
     * 被 @PostConstruct 修改的方法会在构造函数之后执行，在初始化方法 init() 之前执行。
     */
    @Override
    @PostConstruct
    public void regCallback() {
        // 消息发送成功以后，给予生产者的消息回执，来确保生产者的可靠性。
        rabbitTemplate.setConfirmCallback(new RabbitTemplate.ConfirmCallback() {
            @Override
            public void confirm(CorrelationData correlationData, boolean ack, String cause) {
                System.out.println("Cause: " + cause);
                String orderId = correlationData.getId();
                // 如果  ack 为 true 代表消息已经收到。
                if (!ack) {
                    // 表示消息没有被确认
                    
                    // 这里可能要进行其他的方式进行存储
                    System.out.println("MQ 队列应答失败，orderId是：" + orderId);
                    return;
                }
                // 消息确认了。
                try {
                    // 准备修改状态的 SQL
                    String sql = "UPDATE mt_order_message SET status = 1 WHERE order_id = ?";
                    // 执行消息冗余表中的状态修改
                    int count = jdbcTemplate.update(sql, orderId);
                    if (count == 1) {
                        System.out.println("本地冗余消息状态修改成功，消息成功投递到消息队列中...");
                    }
                } catch (Exception e) {
                    System.out.println("本地冗余消息状态修改失败，出现异常：" + e.getMessage());
                }
            }
        });
    }
    
    @Override
    public void sendMessage(Order order) {
        // 通过 MQ 发送消息
        rabbitTemplate.convertAndSend(
                "order_fanout_exchange", // 交换机
                "", // 路由 key
                StringBeanConvert.beanToString(order), // 消息体
                new CorrelationData(order.getOrderId())); // 相关数据
    }
}
