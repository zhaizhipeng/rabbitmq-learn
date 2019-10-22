package com.rabbitmq.message.unreachable;

import com.rabbitmq.client.*;

import java.io.IOException;

public class Consumer {

    public static void main(String[] args) throws Exception{

        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("localhost");
        connectionFactory.setPort(5672);
        connectionFactory.setVirtualHost("/");
        connectionFactory.setHandshakeTimeout(20000);
        connectionFactory.setAutomaticRecoveryEnabled(true);
        connectionFactory.setNetworkRecoveryInterval(3000);

        Connection connection = connectionFactory.newConnection();
        final Channel channel = connection.createChannel();

        String exchangeName = "test_return_exchange";
        String exchangeType = "direct";
        String queueName = "test_return_queue";
        String routingKey = "test.return";

        // 声明了一个交换机
        channel.exchangeDeclare(exchangeName, exchangeType,true,false,false,null);

        // 声明了一个队列
        channel.queueDeclare(queueName,true,false,false,null);

        // 建立一个绑定关系
        channel.queueBind(queueName, exchangeName, routingKey);

        channel.basicConsume(queueName, true, new DefaultConsumer(channel) {
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.err.println("消费端:" + new String(body));
            }
        });
    }
}