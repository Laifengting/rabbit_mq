package com.lft.mq.constant;

/**
 * Class Name:      MQConstants
 * Package Name:    com.lft.mq.constant
 * <p>
 * Function: 		A {@code MQConstants} object With Some FUNCTION.
 * Date:            2021-05-25 11:37
 * <p>
 * @author Laifengting / E-mail:laifengting@foxmail.com
 * @version 1.0.0
 * @since JDK 8
 */
public class MQConstants {
    /**
     * 交换机名称
     */
    public static final String EXCHANGE_NAME_1 = "logs.direct";
    public static final String EXCHANGE_NAME_2 = "logs.topics";
    
    /**
     * 交换机类型
     */
    public static final String EXCHANGE_TYPE_DIRECT = "direct";
    public static final String EXCHANGE_TYPE_TOPIC = "topic";
    
    /**
     * 路由 key
     */
    public static final String ROUTING_KEY_INFO = "info";
    public static final String ROUTING_KEY_WARN = "warn";
    public static final String ROUTING_KEY_ERROR = "error";
}
