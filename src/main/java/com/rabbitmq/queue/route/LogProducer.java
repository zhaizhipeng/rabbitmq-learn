package com.rabbitmq.queue.route;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.MessageProperties;
import com.rabbitmq.utils.ConnectionUtils;

/**
 * 路由队列模式-生产者
 */
public class LogProducer {

    public static void main(String[] args) throws Exception {

        Connection connection = ConnectionUtils.newConnection();

        Channel channel = connection.createChannel();

        for(int i = 0; i < 100; i++){
            if (i % 2 == 0){
                String msg = "info:" + i;
                channel.basicPublish("test_route_queue_exchange", "info", MessageProperties.PERSISTENT_TEXT_PLAIN, msg.getBytes());
            }else {
                String msg = "error:" + i;
                channel.basicPublish("test_route_queue_exchange", "error", MessageProperties.PERSISTENT_TEXT_PLAIN, msg.getBytes());
            }
        }

        channel.close();
        connection.close();
    }

}
