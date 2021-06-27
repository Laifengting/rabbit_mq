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
@RequestMapping ("lazy")
public class LazyController {
    private static final Logger log = LoggerFactory.getLogger(LazyController.class);
    
    @Autowired
    private RabbitTemplate rabbitTemplate;
    
    @GetMapping ("sendMsg/{message}")
    public void setMsg(@PathVariable ("message") String message) {
        for (int i = 0; i < 1000000; i++) {
            rabbitTemplate.convertAndSend(
                    MqConstant.LAZY_EXCHANGE2,
                    MqConstant.LAZY_ROUTING_KEY2,
                    message + i);
            // log.info("发送的消息内容为：{}", message + i);
        }
    }
}
