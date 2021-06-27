package com.lft.rabbitmq.controller;

import com.lft.rabbitmq.constant.MqConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Class Name:      SendMsgController
 * Package Name:    com.lft.rabbitmq.controller
 * <p>
 * Function: 		A {@code SendMsgController} object With Some FUNCTION.
 * Date:            2021-06-26 9:54
 * <p>
 * @author Laifengting / E-mail:laifengting@foxmail.com
 * @version 1.0.0
 * @since JDK 8
 */
@RestController
@RequestMapping ("priority")
public class PriorityController {
    private static final Logger log = LoggerFactory.getLogger(PriorityController.class);
    
    @Autowired
    private RabbitTemplate rabbitTemplate;
    
    @GetMapping ("sendMsg/{message}")
    public void setMsg(@PathVariable ("message") String message) {
        rabbitTemplate.convertAndSend(
                MqConstant.PRIORITY_EXCHANGE,
                MqConstant.PRIORITY_ROUTING_KEY,
                message + "P7", msg -> {
                    msg.getMessageProperties().setPriority(7);
                    return msg;
                });
        log.info("发送的消息内容为：{}", message + "P7");
        
        rabbitTemplate.convertAndSend(
                MqConstant.PRIORITY_EXCHANGE,
                MqConstant.PRIORITY_ROUTING_KEY,
                message + "P5", msg -> {
                    msg.getMessageProperties().setPriority(5);
                    return msg;
                });
        log.info("发送的消息内容为：{}", message + "P5");
        
        rabbitTemplate.convertAndSend(
                MqConstant.PRIORITY_EXCHANGE,
                MqConstant.PRIORITY_ROUTING_KEY,
                message + "P9", msg -> {
                    msg.getMessageProperties().setPriority(9);
                    return msg;
                });
        log.info("发送的消息内容为：{}", message + "P9");
        
        rabbitTemplate.convertAndSend(
                MqConstant.PRIORITY_EXCHANGE,
                MqConstant.PRIORITY_ROUTING_KEY,
                message + "P1", msg -> {
                    msg.getMessageProperties().setPriority(1);
                    return msg;
                });
        log.info("发送的消息内容为：{}", message + "P1");
    }
}
