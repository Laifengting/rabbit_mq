package com.lft.mq.task;

import com.lft.mq.entity.Order;
import com.lft.mq.entity.OrderMessage;
import com.lft.mq.service.OrderDataBaseService;
import com.lft.mq.service.OrderMessageService;
import com.lft.mq.util.StringBeanConvert;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.List;

/**
 * Class Name:      TaskService
 * Package Name:    com.lft.mq.task
 * <p>
 * Function: 		A {@code TaskService} object With Some FUNCTION.
 * Date:            2021-05-27 20:21
 * <p>
 * @author Laifengting / E-mail:laifengting@foxmail.com
 * @version 1.0.0
 * @since JDK 8
 */
@EnableScheduling
public class TaskService {
    @Autowired
    private RabbitTemplate rabbitTemplate;
    
    @Autowired
    private OrderMessageService orderMessageService;
    
    @Autowired
    private OrderDataBaseService orderDataBaseService;
    
    // 每 5 秒发送一次
    @Scheduled (cron = "0/5 * * * * ? ")
    public void sendMessage() {
        // 把消息状态为 0 的重新查询出来。投资到 MQ 中。
        Integer status = 0;
        List<OrderMessage> orderMessageList = orderMessageService.selectAllOrderMessagesByStatus(status);
        if (orderMessageList == null) {
            return;
        }
        for (int i = 0; i < orderMessageList.size(); i++) {
            OrderMessage orderMessage = orderMessageList.get(i);
            Order order = orderDataBaseService.getOrderByOrderId(orderMessage.getOrderId());
            if (order == null) {
                continue;
            }
            // 通过 MQ 发送消息
            rabbitTemplate.convertAndSend(
                    "order_fanout_exchange", // 交换机
                    "", // 路由 key
                    StringBeanConvert.beanToString(order), // 消息体
                    new CorrelationData(order.getOrderId())); // 相关数据
        }
    }
}
