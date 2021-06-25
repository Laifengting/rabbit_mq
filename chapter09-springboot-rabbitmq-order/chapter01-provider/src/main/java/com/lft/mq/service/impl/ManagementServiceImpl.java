package com.lft.mq.service.impl;

import com.lft.mq.service.ManagementService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Class Name:      ManagementServiceImpl
 * Package Name:    com.lft.mq.service.impl
 * <p>
 * Function: 		A {@code ManagementServiceImpl} object With Some FUNCTION.
 * Date:            2021-05-26 18:55
 * <p>
 * @author Laifengting / E-mail:laifengting@foxmail.com
 * @version 1.0.0
 * @since JDK 8
 */
@Service
public class ManagementServiceImpl implements ManagementService {
    
    @Autowired
    private RabbitTemplate rabbitTemplate;
    
    /**
     * 模拟创建用户
     * @param username 用户名
     * @param email    邮箱
     * @param address  地址
     * @param role     角色
     */
    @Override
    public void createRole(String username, String email, String address, String role) {
        // 通过 MQ 来完成消息的分发
        // 参数1：交换机名。    参数2：路由key / 队列名    参数3：消息内容
        String exchangeName = "topic_management_exchange";
        String routingKey1 = role + "." + username;
        rabbitTemplate.convertAndSend(exchangeName, routingKey1, username + ":" + email + ":" + address);
    }
}
