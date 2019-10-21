package com.raabbitmq.quickstart;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class Producer {

    public static void main(String[] args) throws Exception {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("localhost");
        connectionFactory.setPort(5672);
        connectionFactory.setUsername("guest");
        connectionFactory.setPassword("guest");
        Connection connection = connectionFactory.newConnection();

        String exchangeName = "test.quick.exchange";
        String queueName = "test_quick_queue";
        String routeKey = "test_quick_route";
        Channel channel = connection.createChannel();

        for (int i = 0; i < 100; i++){
            String body = "hello rabbitmq" + i;
            channel.basicPublish(exchangeName,routeKey,false,null,body.getBytes());
        }
        channel.close();
        connection.close();
    }

}
