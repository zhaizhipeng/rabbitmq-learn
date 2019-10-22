package com.rabbitmq.message.dlx;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Consumer {

    public static void main(String[] args) throws Exception{

        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("localhost");
        connectionFactory.setPort(5672);
        connectionFactory.setVirtualHost("/");
        connectionFactory.setHandshakeTimeout(20000);
        connectionFactory.setAutomaticRecoveryEnabled(true);
        connectionFactory.setNetworkRecoveryInterval(3000);

        Connection connection = connectionFactory.newConnection();
        final Channel channel = connection.createChannel();

        String exchangeName = "test_dlx_exchange";
        String exchangeType = "direct";
        String queueName = "test_dlx_queue";
        String routingKey = "test.dlx";

        channel.exchangeDeclare(exchangeName, exchangeType,true,false,false, null);

        Map<String, Object> agruments = new HashMap<String, Object>();
        agruments.put("x-dead-letter-exchange", "dlx.exchange");
        channel.queueDeclare(queueName,true,false,false, agruments);

        channel.queueBind(queueName, exchangeName, routingKey);

        // 声明死信队列
        String deadExchange = "dlx.exchange";
        String deadQueueName = "dlx_queue";
        channel.exchangeDeclare(deadExchange, "topic", true, false, null);
        channel.queueDeclare(deadQueueName, true, false, false, null);
        channel.queueBind(deadQueueName, deadExchange,"#");

        // 限流
        channel.basicQos(0,1, false);

        channel.basicConsume(queueName, false, new DefaultConsumer(channel) {
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.err.println("消费端:" + new String(body));
                //channel.basicAck(envelope.getDeliveryTag(),false);
            }
        });
    }
}
