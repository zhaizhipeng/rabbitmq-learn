package com.rabbitmq.queue.simple;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.utils.ConnectionUtils;

/**
 * 简单队列模式-生产者
 */
public class Producer {

    public static void main(String[] args) throws Exception {

        Connection connection = ConnectionUtils.newConnection();

        Channel channel = connection.createChannel();

        for(int i = 0; i < 100; i++){
            String msg = "Hello RabbitMQ Simple Queue" + i;
            channel.basicPublish("test_simple_queue_exchange", "test_simple_queue", null, msg.getBytes());
        }

        channel.close();
        connection.close();
    }

}
