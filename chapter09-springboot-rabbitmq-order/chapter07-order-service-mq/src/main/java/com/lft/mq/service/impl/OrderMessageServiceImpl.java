package com.lft.mq.service.impl;

import com.lft.mq.entity.OrderMessage;
import com.lft.mq.service.OrderMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

/**
 * Class Name:      OrderMessageServiceImpl
 * Package Name:    com.lft.mq.service.impl
 * <p>
 * Function: 		A {@code OrderMessageServiceImpl} object With Some FUNCTION.
 * Date:            2021-05-27 21:57
 * <p>
 * @author Laifengting / E-mail:laifengting@foxmail.com
 * @version 1.0.0
 * @since JDK 8
 */
public class OrderMessageServiceImpl implements OrderMessageService {
    
    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    @Override
    public List<OrderMessage> selectAllOrderMessagesByStatus(Integer status) {
        String sql = "SELECT * FROM mt_order_message WHERE status = ?";
        return jdbcTemplate.queryForList(sql, OrderMessage.class, status);
    }
}
