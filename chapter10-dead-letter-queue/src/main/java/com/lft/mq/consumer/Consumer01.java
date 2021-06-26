package com.lft.mq.consumer;

import com.lft.mq.util.MQUtils;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.CancelCallback;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;
import com.rabbitmq.client.Delivery;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * Class Name:      Consumer01
 * Package Name:    com.lft.mq.consumer
 * <p>
 * Function: 		A {@code Consumer01} object With Some FUNCTION.
 * Date:            2021-06-25 17:48
 * <p>
 * @author Laifengting / E-mail:laifengting@foxmail.com
 * @version 1.0.0
 * @since JDK 8
 */
public class Consumer01 {
    // 普通交换机
    private static final String NORMAL_EXCHANGE = "normal_exchange";
    // 死信交换机
    private static final String DEAD_EXCHANGE = "dead_exchange";
    // 普通队列名
    private static final String NORMAL_QUEUE = "normal_queue";
    // 死信队列名
    private static final String DEAD_QUEUE = "dead_queue";
    
    public static void main(String[] args) throws IOException {
        Channel channel = MQUtils.getChannel(MQUtils.getConnection());
        // 声明死信和普通交换机类型为 Direct
        channel.exchangeDeclare(NORMAL_EXCHANGE, BuiltinExchangeType.DIRECT);
        channel.exchangeDeclare(DEAD_EXCHANGE, BuiltinExchangeType.DIRECT);
        
        channel.basicQos(1);
        
        Map<String, Object> arguments = new HashMap<>();
        // 过期时间，可以在消费时指定。也可以在生产者上指定消息过期时间。
        // arguments.put("x-message-ttl", 100000);
        // 正常队列设置死信交换机
        arguments.put("x-dead-letter-exchange", DEAD_EXCHANGE);
        // 设置死信 routing-key
        arguments.put("x-dead-letter-routing-key", "lisi");
        // 设置队列的最大容量。
        // arguments.put("x-max-length", 6);
        
        // 声明普通队列
        channel.queueDeclare(NORMAL_QUEUE, true, false, false, arguments);
        // 声明死信队列
        channel.queueDeclare(DEAD_QUEUE, true, false, false, null);
        
        // 绑定普通的交换机与普通队列
        channel.queueBind(NORMAL_QUEUE, NORMAL_EXCHANGE, "zhangsan");
        // 绑定死信的交换机与死信队列
        channel.queueBind(DEAD_QUEUE, DEAD_EXCHANGE, "lisi");
        
        System.out.println("C1 已经准备好接收消息。。。");
        
        DeliverCallback deliverCallback = new DeliverCallback() {
            @Override
            public void handle(String consumerTag, Delivery message) throws IOException {
                String msg = new String(message.getBody(), StandardCharsets.UTF_8);
                if ("info5".equals(msg)) {
                    System.out.println("C1 拒绝的消息是：" + msg);
                    channel.basicReject(message.getEnvelope().getDeliveryTag(), false);
                } else {
                    System.out.println("C1 接收的消息是：" + msg);
                    channel.basicAck(message.getEnvelope().getDeliveryTag(), false);
                }
                
            }
        };
        CancelCallback cancelCallback = new CancelCallback() {
            @Override
            public void handle(String consumerTag) throws IOException {
            
            }
        };
        channel.basicConsume(NORMAL_QUEUE, false, deliverCallback, cancelCallback);
    }
}
