package com.rabbitmq.exchange.alternate;

import com.rabbitmq.client.*;
import com.rabbitmq.utils.ConnectionUtils;

import java.io.IOException;

/**
 * 发布/订阅-队列模式-生产者
 */
public class Producer {

    public static void main(String[] args) throws Exception {

        Connection connection = ConnectionUtils.newConnection();

        Channel channel = connection.createChannel();

        for(int i = 0; i < 100; i++){
            String msg = "Hello RabbitMQ" + i;
            channel.basicPublish("test_alternate_exchange", "", false, null, msg.getBytes());
        }

        channel.addReturnListener(new ReturnListener() {
            public void handleReturn(int replyCode, String replyText, String exchange, String routingKey, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("监听到的返回的消息："+ new String(body));
            }
        });

        channel.close();
        connection.close();
    }

}
