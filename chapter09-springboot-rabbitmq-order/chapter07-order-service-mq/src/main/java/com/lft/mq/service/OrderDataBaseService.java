package com.lft.mq.service;

import com.lft.mq.entity.Order;

/**
 * Class Name:      OrderDataBaseService
 * Package Name:    com.lft.mq.service.impl
 * <p>
 * Function: 		A {@code OrderDataBaseService} object With Some FUNCTION.
 * Date:            2021-05-27 14:10
 * <p>
 * @author Laifengting / E-mail:laifengting@foxmail.com
 * @version 1.0.0
 * @since JDK 8
 */
public interface OrderDataBaseService {
    
    Integer saveOrder(Order order) throws Exception;
    
    Order getOrderByOrderId(String orderId);
}
