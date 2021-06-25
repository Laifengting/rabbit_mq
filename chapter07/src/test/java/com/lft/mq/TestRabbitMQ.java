package com.lft.mq;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest (classes = RabbitMQSpringBootApplication.class)
@RunWith (SpringRunner.class)
public class TestRabbitMQ {
    @Autowired
    private RabbitTemplate rabbitTemplate;
    
    /**
     * 直连模型
     */
    @Test
    public void testHello() {
        rabbitTemplate.convertAndSend("hello", "Hello World!!");
    }
    
    /**
     * 工作队列/任务队列模型
     */
    @Test
    public void testWork() {
        for (int i = 0; i < 10; i++) {
            rabbitTemplate.convertAndSend("work", "Hello Work模型" + i);
        }
    }
    
    /**
     * 测试广播模型
     */
    @Test
    public void testFanout() {
        // 指定向某个交换机发消息
        rabbitTemplate.convertAndSend("logs", "", "Hello Fanout模型");
    }
    
    /**
     * 测试路由模式
     */
    @Test
    public void testDirect() {
        // 指定向某个交换机发消息
        rabbitTemplate.convertAndSend("directs", "error", "Hello Directs 模型");
    }
    
    /**
     * 测试主题模式
     */
    @Test
    public void testTopic() {
        // 指定向某个交换机发消息
        rabbitTemplate.convertAndSend("topics", "user.info", "Hello Topics 模型");
        rabbitTemplate.convertAndSend("topics", "user.error.msg", "Hello Topics 模型");
    }
    
}
