package com.lft.mq.service.impl;

import com.lft.mq.entity.Order;
import com.lft.mq.service.MqOrderService;
import com.lft.mq.service.MqService;
import com.lft.mq.service.OrderDataBaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Class Name:      MqOrderServiceImpl
 * Package Name:    com.lft.mq.service.impl
 * <p>
 * Function: 		A {@code MqOrderServiceImpl} object With Some FUNCTION.
 * Date:            2021-05-27 16:47
 * <p>
 * @author Laifengting / E-mail:laifengting@foxmail.com
 * @version 1.0.0
 * @since JDK 8
 */
@Service
public class MqOrderServiceImpl implements MqOrderService {
    @Autowired
    private OrderDataBaseService orderDataBaseService;
    
    @Autowired
    private MqService mqService;
    
    @Override
    public void createOrder(Order order) throws Exception {
        // 1. 订单信息 插入订单系统，订单数据库事务
        orderDataBaseService.saveOrder(order);
        // 2. 通过 订单 mq 发送订单消息到 消息队列。
        mqService.sendMessage(order);
    }
}
