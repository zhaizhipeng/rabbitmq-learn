package com.rabbitmq.queue.topic;

import com.rabbitmq.client.*;
import com.rabbitmq.utils.ConnectionUtils;
import java.io.IOException;

public class Producer {

    public static void main(String[] args) throws Exception {
        Connection connection = ConnectionUtils.newConnection();
        Channel channel = connection.createChannel();
        String exchangeName = "test_topic_queue_exchange";
        String msg = "Hello World RabbitMQ Topic Queue Message";

        channel.addReturnListener(new ReturnListener() {
            public void handleReturn(int replyCode, String replyText, String exchange, String routingKey, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("监听到返回的消息：" + new String(body));
            }
        });

        channel.basicPublish(exchangeName, "", true, null, msg.getBytes());

        channel.basicPublish(exchangeName, "lazy.test.hello", true, null, msg.getBytes());

        channel.basicPublish(exchangeName, "test.orange.hello", true, null, msg.getBytes());

        channel.basicPublish(exchangeName, "test.hello.rabbit", true, null, msg.getBytes());
    }

}
