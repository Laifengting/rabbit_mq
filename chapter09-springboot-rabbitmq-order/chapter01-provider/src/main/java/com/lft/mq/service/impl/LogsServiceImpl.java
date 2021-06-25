package com.lft.mq.service.impl;

import com.lft.mq.service.LogsService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Class Name:      LogsServiceImpl
 * Package Name:    com.lft.mq.service.impl
 * <p>
 * Function: 		A {@code LogsServiceImpl} object With Some FUNCTION.
 * Date:            2021-05-26 18:26
 * <p>
 * @author Laifengting / E-mail:laifengting@foxmail.com
 * @version 1.0.0
 * @since JDK 8
 */
@Service
public class LogsServiceImpl implements LogsService {
    
    @Autowired
    private RabbitTemplate rabbitTemplate;
    
    /**
     * 模拟日志生成
     * @param logId
     * @param logBody
     * @param createTime
     */
    @Override
    public void createLogs(String logLevel, String logBody, Date createTime) {
        // 通过 MQ 来完成消息的分发
        // 参数1：交换机名。    参数2：路由key / 队列名    参数3：消息内容
        String exchangeName = "direct_logs_exchange";
        String routingKey1 = logLevel;
        rabbitTemplate.convertAndSend(exchangeName, routingKey1, logLevel + ":" + logBody + ":" + createTime);
    }
}
