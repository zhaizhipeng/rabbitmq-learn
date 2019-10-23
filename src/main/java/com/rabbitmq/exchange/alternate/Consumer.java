package com.rabbitmq.exchange.alternate;

import com.rabbitmq.client.*;
import com.rabbitmq.utils.ConnectionUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 发布/订阅队列模式-消费者
 */
public class Consumer {

    public static void main(String[] args) throws Exception{

        Connection connection = ConnectionUtils.newConnection();

        Channel channel = connection.createChannel();

        String exchangeName = "test_alternate_exchange";

        String queueName = "test_alternate_queue";

        Map<String, Object> arguments = new HashMap<String, Object>();
        arguments.put("alternate-exchange", "alternate_exchange");
        channel.exchangeDeclare(exchangeName,"direct", true, false, arguments);

        channel.queueDeclare(queueName,true,false,false,null);

        channel.queueBind(queueName,exchangeName,"test_alternate_exchange");

        String alternateExchangeName = "alternate_exchange";

        String alternateQueueName = "alternate_queue";

        channel.exchangeDeclare(alternateExchangeName,"fanout", true, false, null);

        channel.queueDeclare(alternateQueueName,true,false,false,null);

        channel.queueBind(alternateQueueName,alternateExchangeName,"");

        channel.basicConsume(queueName, true, new DefaultConsumer(channel) {
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.err.println("消费端:" + new String(body));
            }
        });

        channel.basicConsume(alternateQueueName, true, new DefaultConsumer(channel) {
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.err.println("备份交换机路由：消费端:" + new String(body));
            }
        });
    }
}
