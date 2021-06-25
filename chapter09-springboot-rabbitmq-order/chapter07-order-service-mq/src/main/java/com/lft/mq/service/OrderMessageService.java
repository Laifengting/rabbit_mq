package com.lft.mq.service;

import com.lft.mq.entity.OrderMessage;

import java.util.List;

/**
 * Class Name:      OrderMessageService
 * Package Name:    com.lft.mq.service
 * <p>
 * Function: 		A {@code OrderMessageService} object With Some FUNCTION.
 * Date:            2021-05-27 21:55
 * <p>
 * @author Laifengting / E-mail:laifengting@foxmail.com
 * @version 1.0.0
 * @since JDK 8
 */
public interface OrderMessageService {
    
    List<OrderMessage> selectAllOrderMessagesByStatus(Integer status);
}
