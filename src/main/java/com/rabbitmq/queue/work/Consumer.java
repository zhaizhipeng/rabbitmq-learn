package com.rabbitmq.queue.work;

import com.rabbitmq.client.*;
import com.rabbitmq.utils.ConnectionUtils;

import java.io.IOException;

/**
 * 工作队列模式-消费者
 */
public class Consumer {

    public static void main(String[] args) throws Exception{

        Connection connection = ConnectionUtils.newConnection();

        Channel channel = connection.createChannel();

        String exchangeName = "test_work_queue_exchange";

        String queueName = "test_work_queue";

        channel.exchangeDeclare(exchangeName,"direct", true, false, null);

        channel.queueDeclare(queueName,true,false,false,null);

        channel.queueBind(queueName,exchangeName,"test_work_queue");

        channel.basicQos(1);

        channel.basicConsume(queueName, true, new DefaultConsumer(channel) {
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                try {
                    // 模拟耗时场景
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.err.println("消费端:" + new String(body));
            }
        });
    }
}
