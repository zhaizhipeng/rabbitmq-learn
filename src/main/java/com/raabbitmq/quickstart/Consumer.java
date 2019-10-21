package com.raabbitmq.quickstart;

import com.rabbitmq.client.*;

import java.io.IOException;

public class Consumer {

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
        channel.exchangeDeclare(exchangeName,"direct",false,false,null);
        channel.queueDeclare(queueName,true,true,false,null);
        channel.queueBind(queueName,exchangeName,routeKey);

        channel.basicConsume(queueName, new DefaultConsumer(channel) {
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("consumerTag:"+consumerTag);
                System.out.println("envelope:"+envelope);
                System.out.println("properties:"+properties);
                System.out.println("body:"+new String(body));
            }
        });
    }

}
