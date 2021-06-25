package com.lft.mq.service;

/**
 * Class Name:      OrderService
 * Package Name:    com.lft.mq.service
 * <p>
 * Function: 		A {@code OrderService} object With Some FUNCTION.
 * Date:            2021-05-26 17:28
 * <p>
 * @author Laifengting / E-mail:laifengting@foxmail.com
 * @version 1.0.0
 * @since JDK 8
 */
public interface OrderService {
    
    /**
     * 模拟用户下单
     * @param uid       用户id
     * @param productId 商品id
     * @param num       商品数量
     */
    void createOrder(String uid, String productId, Integer num);
}
