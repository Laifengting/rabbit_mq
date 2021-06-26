package com.lft.rabbitmq.controller;

import com.lft.rabbitmq.constant.MqConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.connection.CorrelationData;
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
@RequestMapping ("confirm")
public class ProviderController {
    private static Logger log = LoggerFactory.getLogger(ProviderController.class);
    
    @Autowired
    private RabbitTemplate rabbitTemplate;
    
    @GetMapping ("sendMsg/{message}")
    public void setMsg(@PathVariable ("message") String message) {
        CorrelationData correlationData = new CorrelationData("1001");
        rabbitTemplate.convertAndSend(
                MqConstant.CONFIRM_EXCHANGE,
                MqConstant.CONFIRM_ROUTING_KEY + "111",
                message, correlationData);
        log.info("发送的消息内容为：{}", message);
    }
}
