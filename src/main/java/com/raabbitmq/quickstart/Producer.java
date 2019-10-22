package com.raabbitmq.quickstart;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class Producer {

    public static void main(String[] args) throws Exception {

        //1.创建一个ConnectionFactory并进行配置
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("localhost");
        connectionFactory.setPort(5672);
        connectionFactory.setVirtualHost("/");
        connectionFactory.setHandshakeTimeout(20000);

        //2.通过连接工厂创建连接
        Connection connection = connectionFactory.newConnection();

        //3.通过Connection创建一个Channel
        Channel channel = connection.createChannel();

        /**
         * basicPublish(String exchange, String routingKey, BasicProperties props, byte[] body)
         * exchange:指定交换机 不指定 则默认（AMQP default交换机） 通过routingkey进行匹配
         * props 消息属性
         * body 消息体
         */
        for(int i = 0; i < 5; i++){
            String msg = "Hello RabbitMQ" + i;
            //4.通过Channel发送数据
            channel.basicPublish("", "test", null, msg.getBytes());
        }

        //5.记得关闭相关的连接
        channel.close();
        connection.close();
    }

}
