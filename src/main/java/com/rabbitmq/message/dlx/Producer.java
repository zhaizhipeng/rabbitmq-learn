package com.rabbitmq.message.dlx;

import com.rabbitmq.client.*;

public class Producer {

    public static void main(String[] args) throws Exception {

        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("localhost");
        connectionFactory.setPort(5672);
        connectionFactory.setVirtualHost("/");

        Connection connection = connectionFactory.newConnection();
        Channel channel = connection.createChannel();
        String exchangeName = "test_dlx_exchange";
        String routingKey = "test.dlx";

        AMQP.BasicProperties properties = new AMQP.BasicProperties.Builder()
                .deliveryMode(2)
                .expiration("10000")
                .build();

        for (int i = 0; i < 5; i++){
            String msg = "Hello World RabbitMQ Send DLX Message" + i;
            channel.basicPublish(exchangeName, routingKey, true, properties, msg.getBytes());
        }

        channel.close();
        connection.close();
    }
}
