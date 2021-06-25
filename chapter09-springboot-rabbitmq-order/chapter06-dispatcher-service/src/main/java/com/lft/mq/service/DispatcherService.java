package com.lft.mq.service;

/**
 * Class Name:      OrderService
 * Package Name:    com.lft.mq.service
 * <p>
 * Function: 		A {@code OrderService} object With Some FUNCTION.
 * Date:            2021-05-27 13:39
 * <p>
 * @author Laifengting / E-mail:laifengting@foxmail.com
 * @version 1.0.0
 * @since JDK 8
 */
public interface DispatcherService {
    void dispatch(String orderId) throws Exception;
}
