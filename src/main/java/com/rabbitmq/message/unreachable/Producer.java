package com.rabbitmq.message.unreachable;

import com.rabbitmq.client.*;

import java.io.IOException;

public class Producer {

    public static void main(String[] args) throws Exception {

        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("localhost");
        connectionFactory.setPort(5672);
        connectionFactory.setVirtualHost("/");

        Connection connection = connectionFactory.newConnection();
        Channel channel = connection.createChannel();
        String exchangeName = "test_return_exchange";
        String routingKey = "test.return.return";
        String msg = "Hello World RabbitMQ Send Return Message";

        channel.confirmSelect();

        //添加一个确认监听
        channel.addConfirmListener(new ConfirmListener() {
            public void handleNack(long deliveryTag, boolean multiple) throws IOException {
                System.out.println("-------no ack!---------");
            }
            public void handleAck(long deliveryTag, boolean multiple) throws IOException {
                System.out.println("--------ack!----------");
            }
        });

        //添加一个返回监听
        channel.addReturnListener(new ReturnListener() {
            public void handleReturn(int replyCode, String replyText, String exchange, String routingKey, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("replyCode:"+replyCode);
                System.out.println("replyText:"+replyText);
                System.out.println("exchange:"+exchange);
                System.out.println("routingKey:"+routingKey);
                System.out.println("body:"+new String(body));
            }
        });

        channel.basicPublish(exchangeName, routingKey, true,null, msg.getBytes());
    }
}
