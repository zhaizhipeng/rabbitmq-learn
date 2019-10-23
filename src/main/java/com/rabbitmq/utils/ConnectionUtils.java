package com.rabbitmq.utils;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * 连接管理
 */
public class ConnectionUtils {

    public static Connection newConnection() throws Exception{

        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("localhost");
        connectionFactory.setPort(5672);
        connectionFactory.setVirtualHost("test_queue_host");
        connectionFactory.setHandshakeTimeout(20000);

        Connection connection = connectionFactory.newConnection();
        return connection;
    }

}
