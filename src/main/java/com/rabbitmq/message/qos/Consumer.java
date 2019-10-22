package com.rabbitmq.message.qos;

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

        String exchangeName = "test_qos_exchange";
        String exchangeType = "direct";
        String queueName = "test_qos_queue";
        String routingKey = "test.qos";

        // 声明了一个交换机
        channel.exchangeDeclare(exchangeName, exchangeType,true,false,false,null);

        // 声明了一个队列
        channel.queueDeclare(queueName,true,false,false,null);

        // 建立一个绑定关系
        channel.queueBind(queueName, exchangeName, routingKey);

        // 限流
        channel.basicQos(0,1, false);

        channel.basicConsume(queueName, false, new DefaultConsumer(channel) {
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.err.println("消费端:" + new String(body));
                //channel.basicNack(envelope.getDeliveryTag(),false,false);
                //channel.basicAck(envelope.getDeliveryTag(),false);
            }
        });
    }
}
