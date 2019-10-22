package com.rabbitmq.message.properties;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.util.HashMap;
import java.util.Map;

public class Producer {

    public static void main(String[] args) throws Exception {

        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("localhost");
        connectionFactory.setPort(5672);
        connectionFactory.setVirtualHost("/");
        connectionFactory.setHandshakeTimeout(20000);

        Connection connection = connectionFactory.newConnection();

        Channel channel = connection.createChannel();
        Map<String,Object> headers = new HashMap<String,Object>();
        headers.put("my1", "111");
        headers.put("my2", "222");

        AMQP.BasicProperties properties = new AMQP.BasicProperties().builder()
                .deliveryMode(2)
                .contentEncoding("utf-8")
                .expiration("10000")
                .headers(headers)
                .build();

        for(int i = 0; i < 5; i++){
            String msg = "Hello RabbitMQ" + i;
            channel.basicPublish("", "test", properties, msg.getBytes());
        }

        channel.close();
        connection.close();
    }
}
