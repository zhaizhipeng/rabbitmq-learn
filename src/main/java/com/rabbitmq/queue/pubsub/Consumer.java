package com.rabbitmq.queue.pubsub;

import com.rabbitmq.client.*;
import com.rabbitmq.utils.ConnectionUtils;

import java.io.IOException;

/**
 * 发布/订阅队列模式-消费者
 */
public class Consumer {

    public static void main(String[] args) throws Exception{

        Connection connection = ConnectionUtils.newConnection();

        Channel channel = connection.createChannel();

        String exchangeName = "test_pubsub_queue_exchange";

        String queueName = "test_pubsub_queue_1";

        channel.exchangeDeclare(exchangeName,"fanout", true, false, null);

        channel.queueDeclare(queueName,true,false,false,null);

        channel.queueBind(queueName,exchangeName,"");

        channel.basicQos(1);

        channel.basicConsume(queueName, true, new DefaultConsumer(channel) {
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.err.println("消费端:" + new String(body));
            }
        });
    }
}
