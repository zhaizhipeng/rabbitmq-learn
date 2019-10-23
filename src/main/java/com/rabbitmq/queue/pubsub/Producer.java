package com.rabbitmq.queue.pubsub;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.MessageProperties;
import com.rabbitmq.utils.ConnectionUtils;

/**
 * 发布/订阅-队列模式-生产者
 */
public class Producer {

    public static void main(String[] args) throws Exception {

        Connection connection = ConnectionUtils.newConnection();

        Channel channel = connection.createChannel();

        for(int i = 0; i < 100; i++){
            String msg = "Hello RabbitMQ Pubsub Queue" + i;
            channel.basicPublish("test_pubsub_queue_exchange", "", MessageProperties.PERSISTENT_TEXT_PLAIN, msg.getBytes());
        }

        channel.close();
        connection.close();
    }

}
