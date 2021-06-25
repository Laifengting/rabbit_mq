package com.lft.mq.service;

import java.util.Date;

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
public interface LogsService {
    
    /**
     * 模拟日志生成
     * @param logId
     * @param logBody
     * @param createTime
     */
    void createLogs(String logLevel, String logBody, Date createTime);
}
