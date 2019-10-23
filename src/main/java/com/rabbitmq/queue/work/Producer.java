package com.rabbitmq.queue.work;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.MessageProperties;
import com.rabbitmq.utils.ConnectionUtils;

/**
 * 工作队列模式-生产者
 */
public class Producer {

    public static void main(String[] args) throws Exception {

        Connection connection = ConnectionUtils.newConnection();

        Channel channel = connection.createChannel();

        for(int i = 0; i < 100; i++){
            String msg = "Hello RabbitMQ Work Queue" + i;
            channel.basicPublish("test_work_queue_exchange", "test_work_queue", MessageProperties.PERSISTENT_TEXT_PLAIN, msg.getBytes());
        }

        channel.close();
        connection.close();
    }

}
