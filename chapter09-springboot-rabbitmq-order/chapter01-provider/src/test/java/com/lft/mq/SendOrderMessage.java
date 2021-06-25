package com.lft.mq;

import com.lft.mq.service.LogsService;
import com.lft.mq.service.ManagementService;
import com.lft.mq.service.OrderService;
import com.lft.mq.service.TtlService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

/**
 * Class Name:      SendOrderMessage
 * Package Name:    com.lft.mq
 * <p>
 * Function: 		A {@code SendOrderMessage} object With Some FUNCTION.
 * Date:            2021-05-26 17:47
 * <p>
 * @author Laifengting / E-mail:laifengting@foxmail.com
 * @version 1.0.0
 * @since JDK 8
 */
@SpringBootTest
public class SendOrderMessage {
    @Autowired
    private OrderService orderService;
    
    @Autowired
    private LogsService logsService;
    
    @Autowired
    private ManagementService managementService;
    
    @Autowired
    private TtlService ttlService;
    
    @Test
    public void testCreateOrder() {
        orderService.createOrder("1", "1", 10);
    }
    
    @Test
    public void testCreateLogs() {
        logsService.createLogs("error", "这是一条 error 日志", new Date());
    }
    
    @Test
    public void testCreateRole() {
        managementService.createRole("laifengting", "laifengting@foxmail.com", "西亭", "dev");
    }
    
    @Test
    public void testTtl() {
        ttlService.createTtl("laifengting", "laifengting@foxmail.com", 10);
    }
    
    @Test
    public void testTtlMessage() {
        ttlService.createTtlMessage("laifengting", "laifengting@foxmail.com", 10);
    }
    
}
