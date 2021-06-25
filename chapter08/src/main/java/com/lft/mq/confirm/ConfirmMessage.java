package com.lft.mq.confirm;

import com.lft.mq.util.MQUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConfirmCallback;
import com.rabbitmq.client.MessageProperties;

import java.nio.charset.StandardCharsets;
import java.util.UUID;
import java.util.concurrent.ConcurrentNavigableMap;
import java.util.concurrent.ConcurrentSkipListMap;

/**
 * Class Name:      ConfirmMessage
 * Package Name:    com.lft.mq.confirm
 * <p>
 * Function: 		A {@code ConfirmMessage} object With Some FUNCTION.
 * Date:            2021-06-25 8:23
 * <p>
 * @author Laifengting / E-mail:laifengting@foxmail.com
 * @version 1.0.0
 * @since JDK 8
 */
public class ConfirmMessage {
    private static final Integer MESSAGE_COUNT = 1000;
    
    public static void main(String[] args) throws Exception {
        // 单个发布确认 执行时间：699
        // publishConfirmMessageIndividually();
        // 批量发布确认 执行时间：62
        // publishConfirmMessageBatch();
        // 异步批量发布确认 执行时间：23
        publishConfirmMessageAsync();
        
    }
    
    /**
     * 单个发布确认
     */
    public static void publishConfirmMessageIndividually() throws Exception {
        // 获取信道
        Channel channel = MQUtils.getChannel(MQUtils.getConnection());
        // 开启发布确认
        channel.confirmSelect();
        // 生成队列名
        String queueName = UUID.randomUUID().toString().replaceAll("-", "");
        // 声明队列，参数1：队列名，参数2：持久化，参数3：当前连接独占，参数4：其他参数
        channel.queueDeclare(queueName, true, false, false, null);
        // 开始时间
        long start = System.currentTimeMillis();
        Integer count = 0;
        // 批量发消息
        for (Integer i = 0; i < MESSAGE_COUNT; i++) {
            String message = "" + i;
            channel.basicPublish("", queueName, MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes(StandardCharsets.UTF_8));
            // 单个消息发出就马上进行发布确认
            boolean flag = channel.waitForConfirms();
            if (flag) {
                // System.out.println("消息发送成功");
                count++;
            }
        }
        // 结束时间
        long end = System.currentTimeMillis();
        System.out.println("执行时间：" + (end - start));
        System.out.println("确认次数：" + count);
    }
    
    /**
     * 批量发布确认
     */
    public static void publishConfirmMessageBatch() throws Exception {
        // 获取信道
        Channel channel = MQUtils.getChannel(MQUtils.getConnection());
        // 开启发布确认
        channel.confirmSelect();
        // 生成队列名
        String queueName = UUID.randomUUID().toString().replaceAll("-", "");
        // 声明队列，参数1：队列名，参数2：持久化，参数3：当前连接独占，参数4：其他参数
        channel.queueDeclare(queueName, true, false, false, null);
        // 开始时间
        long start = System.currentTimeMillis();
        
        Integer count = 0;
        // 批量发消息
        Integer batchSize = 100;
        for (Integer i = 1; i <= MESSAGE_COUNT; i++) {
            String message = "" + i;
            channel.basicPublish("", queueName, MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes(StandardCharsets.UTF_8));
            if (i % batchSize == 0) {
                boolean flag = channel.waitForConfirms();
                if (flag) {
                    count++;
                }
            }
        }
        
        // 结束时间
        long end = System.currentTimeMillis();
        System.out.println("执行时间：" + (end - start));
        System.out.println("确认次数：" + count);
    }
    
    /**
     * 异步批量发布确认
     */
    public static void publishConfirmMessageAsync() throws Exception {
        // 获取信道
        Channel channel = MQUtils.getChannel(MQUtils.getConnection());
        // 开启发布确认
        channel.confirmSelect();
        // 生成队列名
        String queueName = UUID.randomUUID().toString().replaceAll("-", "");
        // 声明队列，参数1：队列名，参数2：持久化，参数3：当前连接独占，参数4：其他参数
        channel.queueDeclare(queueName, true, false, false, null);
        
        /*
            线程安装有序的一个哈希表。适用于高并发的场景下。
            1. 轻松的将序号与消息进行关联
            2. 轻松批量删除条目。只根给定序号。
            3. 支持高并发（多线程）
         */
        ConcurrentSkipListMap<Long, String> outSandingConfirms = new ConcurrentSkipListMap<>();
        
        // 消息确认成功回调
        ConfirmCallback ackCallback = (deliveryTag, multiple) -> {
            // 2. 删除已经确认的消息，剩下的就是未确认的消息。
            if (multiple) {
                ConcurrentNavigableMap<Long, String> confirmed = outSandingConfirms.headMap(deliveryTag);
                confirmed.clear();
            } else {
                outSandingConfirms.remove(deliveryTag);
            }
            System.out.println("确认的消息：" + deliveryTag);
        };
        // 消息确认失败回调
        ConfirmCallback nackCallback = (deliveryTag, multiple) -> {
            System.out.println("未确认的消息：" + deliveryTag);
            // 3.
            String message = outSandingConfirms.get(deliveryTag);
            System.out.println("未确认的消息是：" + message + " : 未确认的消息 tag：" + deliveryTag);
        };
        
        // 准备消息的监听器，监听哪些消息成功了，哪些消息失败了。【异步通知】
        channel.addConfirmListener(ackCallback, nackCallback);
        
        // 开始时间
        long start = System.currentTimeMillis();
        
        // 批量发消息
        for (Integer i = 1; i <= MESSAGE_COUNT; i++) {
            String message = "" + i;
            channel.basicPublish("", queueName, MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes(StandardCharsets.UTF_8));
            // 1. 记录下所有要发送的消息 消息的总和
            outSandingConfirms.put(channel.getNextPublishSeqNo(), message);
        }
        // 结束时间
        long end = System.currentTimeMillis();
        System.out.println("发布 " + MESSAGE_COUNT + " 个异步发布确认消息，执行时间：" + (end - start) + " ms");
    }
}
