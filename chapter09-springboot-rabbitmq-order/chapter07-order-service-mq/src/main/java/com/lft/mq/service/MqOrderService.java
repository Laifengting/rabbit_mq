package com.lft.mq.service;

import com.lft.mq.entity.Order;

/**
 * Class Name:      MqOrderService
 * Package Name:    com.lft.mq.service
 * <p>
 * Function: 		A {@code MqOrderService} object With Some FUNCTION.
 * Date:            2021-05-27 16:45
 * <p>
 * @author Laifengting / E-mail:laifengting@foxmail.com
 * @version 1.0.0
 * @since JDK 8
 */
public interface MqOrderService {
    void createOrder(Order order) throws Exception;
}
