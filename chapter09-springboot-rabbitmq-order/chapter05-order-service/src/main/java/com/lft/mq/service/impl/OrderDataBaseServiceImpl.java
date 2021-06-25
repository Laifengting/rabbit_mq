package com.lft.mq.service.impl;

import com.lft.mq.entity.Order;
import com.lft.mq.service.OrderDataBaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Class Name:      OrderDataBaseServiceImpl
 * Package Name:    com.lft.mq.service.impl
 * <p>
 * Function: 		A {@code OrderDataBaseServiceImpl} object With Some FUNCTION.
 * Date:            2021-05-27 14:11
 * <p>
 * @author Laifengting / E-mail:laifengting@foxmail.com
 * @version 1.0.0
 * @since JDK 8
 */
@Service
public class OrderDataBaseServiceImpl implements OrderDataBaseService {
    
    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    @Override
    public Integer saveOrder(Order order) throws Exception {
        // 定义保存 SQL 语句
        String sql = "INSERT INTO mt_order(order_id,user_id,order_content,gmt_created,gmt_modified) VALUES(?,?,?,?,?)";
        // 添加订单记录
        int counte = jdbcTemplate.update(sql, order.getOrderId(), order.getUserId(), order.getOrderContent(), new Date(), new Date());
        // 判断保存订单成功与否
        if (counte != 1) {
            throw new Exception("订单创建失败，原因:[数据库操作失败]");
        }
        // 因为下单可能会出现 rabbit 宕机，就引发消息是没有放入 MQ。为了消息可靠生产，对消息代码生成一次冗余。
        // saveLocalMessage(order);
        return counte;
    }
    
    /**
     * 保存信息到本地
     * @param order
     */
    private void saveLocalMessage(Order order) {
    }
}
