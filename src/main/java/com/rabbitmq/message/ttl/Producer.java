package com.rabbitmq.message.ttl;

import com.rabbitmq.client.AMQP;
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
        String exchangeName = "test_ttl_exchange";
        String routingKey = "test.ttl";

        AMQP.BasicProperties properties = new AMQP.BasicProperties.Builder()
                .expiration("10000")
                .build();

        for (int i = 0; i < 5; i++){
            String msg = "Hello World RabbitMQ Send TTL Message" + i;
            Thread.sleep(10000);
            channel.basicPublish(exchangeName, routingKey, true, properties, msg.getBytes());
        }

        channel.close();
        connection.close();
    }
}
