package com.raabbitmq.quickstart;

import com.rabbitmq.client.*;

import java.io.IOException;

public class Consumer {

    public static void main(String[] args) throws Exception{

        //1.创建一个ConnectionFactory 并进行配置
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("localhost");
        connectionFactory.setPort(5672);
        connectionFactory.setVirtualHost("/");
        connectionFactory.setHandshakeTimeout(20000);

        //2.通过连接工厂创建连接
        Connection connection = connectionFactory.newConnection();

        //3.通过Connection 创建一个 Channel
        Channel channel = connection.createChannel();

        String queueName = "test";

        /**
         * durable 是否持久化
         * exclusive 独占的相当于加了一把锁
         */
        //4.声明创建一个队列
        channel.queueDeclare("test",true,false,false,null);

        //6.设置channel
        /**
         * ACK: 当一条消息从生产端发到消费端，消费端接收到消息后会马上回送一个ACK信息给broker,告诉它这条消息收到了
         * autoack:
         *      true 自动签收 当消费者收到消息就表示消费者收到了消息，消费者收到了消息就会立即从队列中删除。
         *      false 手动签收 当消费者收到消息在合适的时候来显示的进行确认，确认已经接收到了该消息了，RabbitMQ可以从队列中删除该消息了。
         */
        channel.basicConsume(queueName, true, new DefaultConsumer(channel) {
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.err.println("消费端:" + new String(body));
            }
        });
    }

}
