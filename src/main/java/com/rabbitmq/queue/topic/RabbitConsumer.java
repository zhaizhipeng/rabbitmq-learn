package com.rabbitmq.queue.topic;

import com.rabbitmq.client.*;
import com.rabbitmq.utils.ConnectionUtils;

import java.io.IOException;

public class RabbitConsumer {

    public static void main(String[] args) throws Exception{

        Connection connection = ConnectionUtils.newConnection();
        Channel channel = connection.createChannel();

        String exchangeName = "test_topic_queue_exchange";
        String exchangeType = "topic";
        String queueName = "test_topic_queue_rabbit";
        String routingKey = "*.*.rabbit";

        channel.exchangeDeclare(exchangeName, exchangeType,true,false,false,null);

        channel.queueDeclare(queueName,true,false,false,null);

        channel.queueBind(queueName, exchangeName, routingKey);

        channel.basicConsume(queueName, true, new DefaultConsumer(channel) {
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.err.println("消费端:" + new String(body));
            }
        });
    }
}
