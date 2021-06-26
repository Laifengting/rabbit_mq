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

import java.text.SimpleDateFormat;
import java.util.Date;

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
@RequestMapping ("ttl")
public class SendMsgController {
    private static Logger log = LoggerFactory.getLogger(SendMsgController.class);
    
    @Autowired
    private RabbitTemplate rabbitTemplate;
    
    @GetMapping ("sendMsg/{message}")
    public void sendMsg(@PathVariable ("message") String message) {
        log.info("当前时间：{}，发送一条信息给两个 TTL 队列：{}", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date()), message);
        rabbitTemplate.convertAndSend(MqConstant.X_EXCHANGE, MqConstant.ROUTING_KEY_XA, "消息来自 TTL 为 10 秒 的队列：" + message);
        rabbitTemplate.convertAndSend(MqConstant.X_EXCHANGE, MqConstant.ROUTING_KEY_XB, "消息来自 TTL 为 40 秒 的队列：" + message);
    }
    
    @GetMapping ("sendExpirationMsg/{message}/{ttlTime}")
    public void sendExpirationMsg(@PathVariable ("message") String message, @PathVariable ("ttlTime") String ttlTime) {
        log.info("当前时间：{} ，发送一条时长为：{} 的信息给 QC 队列：{} ",
                 new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date()),
                 ttlTime,
                 message);
        rabbitTemplate
                .convertAndSend(MqConstant.X_EXCHANGE, MqConstant.ROUTING_KEY_XC, "消息来自 TTL 为 10 秒 的队列：" + message, msg -> {
                    msg.getMessageProperties().setExpiration(ttlTime);
                    return msg;
                });
    }
    
    @GetMapping ("sendDelayedMsg/{message}/{delayedTime}")
    public void sendDelayedMsg(@PathVariable ("message") String message, @PathVariable ("delayedTime") Integer delayedTime) {
        log.info("当前时间：{} ，发送一条时长为：{} 的延迟信息给 延迟 队列：{} ",
                 new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date()),
                 delayedTime,
                 message);
        rabbitTemplate
                .convertAndSend(
                        MqConstant.DELAYED_EXCHANGE,
                        MqConstant.DELAYED_ROUTING_KEY,
                        "消息来自延迟队列：" + message, msg -> {
                            msg.getMessageProperties().setDelay(delayedTime);
                            return msg;
                        });
    }
    
}
