package com.rabbitmq.message.durable;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;
import com.rabbitmq.utils.ConnectionUtils;

public class Producer {

    public static void main(String[] args) throws Exception {

        Connection connection = ConnectionUtils.newConnection();
        Channel channel = connection.createChannel();
        String exchangeName = "test_durable_exchange";
        String routingKey = "test.durable";

        for (int i = 0; i < 50000; i++){
            String msg = "Hello World RabbitMQ Send Durable Message" + i;
            channel.basicPublish(exchangeName, routingKey, true, MessageProperties.PERSISTENT_TEXT_PLAIN, msg.getBytes());
        }

        channel.close();
        connection.close();
    }
}
