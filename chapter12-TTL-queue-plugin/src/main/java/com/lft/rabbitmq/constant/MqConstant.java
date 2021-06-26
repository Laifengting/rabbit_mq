package com.lft.rabbitmq.constant;

/**
 * Class Name:      MqConstant
 * Package Name:    com.lft.rabbitmq.constant
 * <p>
 * Function: 		A {@code MqConstant} object With Some FUNCTION.
 * Date:            2021-06-26 9:25
 * <p>
 * @author Laifengting / E-mail:laifengting@foxmail.com
 * @version 1.0.0
 * @since JDK 8
 */
public class MqConstant {
    // 普通交换机的名称
    public static final String X_EXCHANGE = "X";
    public static final String DELAYED_EXCHANGE = "delayed.exchange";
    
    // 死信交换机的名称
    public static final String Y_DEAD_LETTER_EXCHANGE = "Y";
    
    // 自定义交换机类型
    public static final String X_DELAYED_MESSAGE = "x-delayed-message";
    
    // 普通队列的名称
    public static final String QA_QUEUE = "QA";
    public static final String QB_QUEUE = "QB";
    public static final String QC_QUEUE = "QC";
    public static final String DELAYED_QUEUE = "delayed.queue";
    
    // 死信队列的名称
    public static final String QD_DEAD_LETTER_QUEUE = "QD";
    
    // 普通 RoutingKey
    public static final String ROUTING_KEY_XA = "XA";
    public static final String ROUTING_KEY_XB = "XB";
    public static final String ROUTING_KEY_XC = "XC";
    public static final String DELAYED_ROUTING_KEY = "delayed.routingkey";
    
    // 死信 RoutingKey
    public static final String DEAD_LETTER_ROUTING_KEY_YD = "YD";
    
    // 消息过期时间
    public static final Integer TTL_TIME_10_SECOND = 10000;
    public static final Integer TTL_TIME_40_SECOND = 40000;
    
}
