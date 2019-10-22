package com.rabbitmq.message.ack;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class Producer {

    public static void main(String[] args) throws Exception {

        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("localhost");
        connectionFactory.setPort(5672);
        connectionFactory.setVirtualHost("/");

        Connection connection = connectionFactory.newConnection();
        Channel channel = connection.createChannel();
        String exchangeName = "test_ack_exchange";
        String routingKey = "test.ack";

        for (int i = 0; i < 5; i++){
            String msg = "Hello World RabbitMQ Send Ack Message" + i;
            channel.basicPublish(exchangeName, routingKey, true,null, msg.getBytes());
        }

        channel.close();
        connection.close();
    }
}
