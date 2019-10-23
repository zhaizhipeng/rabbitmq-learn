package com.rabbitmq.queue.route;

import com.rabbitmq.client.*;
import com.rabbitmq.utils.ConnectionUtils;

import java.io.IOException;

public class ErrorConsumer {

    public static void main(String[] args) throws Exception{

        Connection connection = ConnectionUtils.newConnection();

        Channel channel = connection.createChannel();

        String exchangeName = "test_route_queue_exchange";

        String queueName = "test_route_queue_error";

        channel.exchangeDeclare(exchangeName,"direct", true, false, null);

        channel.queueDeclare(queueName,true,false,false,null);

        channel.queueBind(queueName,exchangeName,"error");

        channel.basicQos(1);

        channel.basicConsume(queueName, true, new DefaultConsumer(channel) {
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.err.println("消费端:" + new String(body));
            }
        });
    }

}
