package com.rabbitmq.message.durable;

import com.rabbitmq.client.*;
import com.rabbitmq.utils.ConnectionUtils;

import java.io.IOException;

public class Consumer {

    public static void main(String[] args) throws Exception{

        Connection connection = ConnectionUtils.newConnection();
        final Channel channel = connection.createChannel();

        String exchangeName = "test_durable_exchange";
        String exchangeType = "direct";
        String queueName = "test_durable_queue";
        String routingKey = "test.durable";

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
