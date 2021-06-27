package com.lft.rabbitmq.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * Class Name:      MyCallBack
 * Package Name:    com.lft.rabbitmq.config
 * <p>
 * Function: 		A {@code MyCallBack} object With Some FUNCTION.
 * Date:            2021-06-26 13:49
 * <p>
 * @author Laifengting / E-mail:laifengting@foxmail.com
 * @version 1.0.0
 * @since JDK 8
 */
@Component
public class MyCallBack implements RabbitTemplate.ConfirmCallback, RabbitTemplate.ReturnCallback {
    
    private static final Logger log = LoggerFactory.getLogger(MyCallBack.class);
    
    @Autowired
    private RabbitTemplate rabbitTemplate;
    
    @PostConstruct
    public void init() {
        // 实现了一个类的内部接口。需要将该实现类，注入到类中。
        rabbitTemplate.setConfirmCallback(this);
        rabbitTemplate.setReturnCallback(this);
    }
    
    /**
     * 交换机确认回调方法
     * 1. 发消息 交换机接收到了回调
     * *    1.1 correlationData 保存回调消息的 ID 及相关信息
     * *    1.2 ack 交换机收到消息 true
     * *    1.3 cause null
     * 2. 发消息 交换机接收失败了回调
     * *    2.1 correlationData 保存回调消息的 ID 及相关信息
     * *    2.2 ack 未收到消息 false
     * *    2.3 cause 可选的失败原因。
     * @param correlationData correlation data for the callback.
     * @param ack             true for ack, false for nack
     * @param cause           An optional cause, for nack, when available, otherwise null.
     */
    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        String id = correlationData != null ? correlationData.getId() : "";
        if (ack) {
            log.info("交换机已经收到了消息ID为：{} 的消息", id);
        } else {
            log.info("交换机未收到了消息ID为：{} 的消息,因为：{}", id, cause);
        }
    }
    
    /**
     * 在消息传递过程中不可达目的地时将消息返回给生产者
     * 只有不可达目的地的时候，才进行回退
     */
    @Override
    public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
        log.error("消息 {} ，被交换机 {} 退回，退回原因：{} 路由 Key ：{}", new String(message.getBody()), exchange, replyText, routingKey);
    }
}
