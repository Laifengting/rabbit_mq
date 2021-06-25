package com.lft.mq.util;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class MQUtils {
    // 1. 创建一个连接工厂
    private static ConnectionFactory connectionFactory;
    
    /**
     * 静态代码块，类加载的时候执行
     * 且只执行一次
     */
    static {
        connectionFactory = new ConnectionFactory();
        // 创建属性配置对象
        Properties properties = new Properties();
        // 指定加载的属性路径和名称
        String property = "rabbitmq.properties";
        // 将配置文件加载成输入流
        InputStream inputStream = MQUtils.class.getClassLoader().getResourceAsStream(property);
        try {
            // 加载到属性对象中
            properties.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        // 2. 设置连接 RabbitMQ 主机地址
        connectionFactory.setHost(properties.getProperty("rabbitmq.host"));
        // 3. 设置连接 RabbitMQ 端口号
        connectionFactory.setPort(Integer.parseInt(properties.getProperty("rabbitmq.port")));
        // 4. 设置连接哪个虚拟主机
        connectionFactory.setVirtualHost(properties.getProperty("rabbitmq.virtualHost"));
        // 5. 设置访问虚拟主机的用户名
        connectionFactory.setUsername(properties.getProperty("rabbitmq.username"));
        // 6. 设置访问虚拟主机的密码
        connectionFactory.setPassword(properties.getProperty("rabbitmq.password"));
    }
    
    /**
     * 获取连接
     * @return
     */
    public static Connection getConnection() {
        try {
            // 7. 创建连接对象
            return connectionFactory.newConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    /**
     * 创建通道
     * @param connection
     * @return
     */
    public static Channel getChannel(Connection connection) {
        try {
            // 8. 创建通道对象
            return connection.createChannel();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public static void closeResources(Channel channel, Connection connection) {
        try {
            if (channel != null) {
                // 9. 关闭通道
                channel.close();
            }
            if (connection != null) {
                // 10. 关闭连接
                connection.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void closeResources(Connection connection) {
        closeResources(null, connection);
    }
    
    public static void closeResources(Channel channel) {
        closeResources(channel, null);
    }
    
    public static void main(String[] args) {
        Connection connection = getConnection();
        System.out.println(connection);
        
        // Channel channel = getChannel(connection);
        // System.out.println(channel);
    }
}
