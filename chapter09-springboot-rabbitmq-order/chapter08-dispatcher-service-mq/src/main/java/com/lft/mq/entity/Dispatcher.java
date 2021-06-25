package com.lft.mq.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * Class Name:      Order
 * Package Name:    com.lft.mq.entity
 * <p>
 * Function: 		A {@code Order} object With Some FUNCTION.
 * Date:            2021-05-27 13:21
 * <p>
 * @author Laifengting / E-mail:laifengting@foxmail.com
 * @version 1.0.0
 * @since JDK 8
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Dispatcher implements Serializable {
    private static final long serialVersionUID = 1L;
    private String dispatcherId;
    private String orderId;
    private Integer status;
    private String userId;
    private String orderContent;
    private Date gmtCreated;
    private Date gmtModified;
}
