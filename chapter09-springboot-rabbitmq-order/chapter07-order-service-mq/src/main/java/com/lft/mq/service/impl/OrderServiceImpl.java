package com.lft.mq.service.impl;

import com.lft.mq.entity.Order;
import com.lft.mq.service.OrderDataBaseService;
import com.lft.mq.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

/**
 * Class Name:      OrderServiceImpl
 * Package Name:    com.lft.mq.service.impl
 * <p>
 * Function: 		A {@code OrderServiceImpl} object With Some FUNCTION.
 * Date:            2021-05-27 14:06
 * <p>
 * @author Laifengting / E-mail:laifengting@foxmail.com
 * @version 1.0.0
 * @since JDK 8
 */
@Service
public class OrderServiceImpl implements OrderService {
    
    @Autowired
    private OrderDataBaseService orderDataBaseService;
    
    @Override
    @Transactional (rollbackFor = Exception.class) // 订单创建整个方法添加事务
    public void createOrder(Order order) throws Exception {
        // 1. 订单信息 - 插入订单系统，订单数据库事务
        Integer resultOfSaveOrder = orderDataBaseService.saveOrder(order);
        
        // 2. 通过 Http 接口发送订单 信息到 运单信息
        String result = dispatcherHttpApi(order.getOrderId());
        
        // 3. 判断是否成功
        if (!"SUCCESS".equalsIgnoreCase(result)) {
            throw new Exception("订单创建失败，原因是运单接口调用失败!");
        }
    }
    
    /**
     * 模拟 Http 请求接口发送，运单系统，将订单号传过去。
     * @param orderId
     * @return
     */
    private String dispatcherHttpApi(String orderId) {
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        // 链接超时 > 3 秒
        factory.setConnectTimeout(3000);
        // 处理超时 > 2 秒
        factory.setReadTimeout(2000);
        // 发送 http 请求
        String url = "http://localhost:9000/dispatcher/order?orderId=" + orderId;
        RestTemplate restTemplate = new RestTemplate(factory);
        String result = restTemplate.getForObject(url, String.class);
        return result;
    }
}
