package com.lft.mq.service.impl;

import com.lft.mq.service.DispatcherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

/**
 * Class Name:      DispatcherServiceImpl
 * Package Name:    com.lft.mq.service.impl
 * <p>
 * Function: 		A {@code DispatcherServiceImpl} object With Some FUNCTION.
 * Date:            2021-05-27 13:42
 * <p>
 * @author Laifengting / E-mail:laifengting@foxmail.com
 * @version 1.0.0
 * @since JDK 8
 */
@Service
public class DispatcherServiceImpl implements DispatcherService {
    
    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    /**
     * 运单接收
     * @param orderId
     * @throws Exception
     */
    @Override
    public void dispatch(String orderId) throws Exception {
        // 定义保存 SQL
        String sql = "INSERT INTO mt_dispather(" +
                "dispather_id," +
                "order_id," +
                "status," +
                "user_id," +
                "order_content," +
                "gmt_created," +
                "gmt_modified) VALUES(?,?,?,?,?,?,?)";
        Integer resultCount = jdbcTemplate
                .update(sql,
                        UUID.randomUUID().toString().replaceAll("-", ""),
                        orderId,
                        0,
                        "002",
                        "泡面一份",
                        new Date(),
                        new Date());
        if (resultCount != 1) {
            throw new Exception("下单失败");
        }
    }
}
