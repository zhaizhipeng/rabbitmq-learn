package com.rabbitmq.message.qos;

import com.rabbitmq.client.*;

public class Producer {

    public static void main(String[] args) throws Exception {

        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("localhost");
        connectionFactory.setPort(5672);
        connectionFactory.setVirtualHost("/");

        Connection connection = connectionFactory.newConnection();
        Channel channel = connection.createChannel();
        String exchangeName = "test_qos_exchange";
        String routingKey = "test.qos";

        for (int i = 0; i < 5; i++){
            String msg = "Hello World RabbitMQ Send Qos Message" + i;
            channel.basicPublish(exchangeName, routingKey, true,null, msg.getBytes());
        }

        channel.close();
        connection.close();
    }
}
