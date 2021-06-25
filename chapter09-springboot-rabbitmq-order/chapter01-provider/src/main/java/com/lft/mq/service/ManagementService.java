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
public interface ManagementService {
    
    /**
     * 模拟创建用户
     * @param username 用户名
     * @param email    邮箱
     * @param address  地址
     * @param role     角色
     */
    void createRole(String username, String email, String address, String role);
}
