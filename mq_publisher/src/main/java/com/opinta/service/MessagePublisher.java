package com.opinta.service;

import com.opinta.model.MessageDto;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import static com.opinta.util.MessageUtils.toBytes;

public class MessagePublisher {
    private final static String QUEUE_NAME = "message_queue";
    private ConnectionFactory factory;
    private Connection connection;
    private Channel channel;

    public MessagePublisher() {
        factory = new ConnectionFactory();
        factory.setHost("localhost");
        try {
            connection = factory.newConnection();
            channel = connection.createChannel();
            channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        } catch (IOException | TimeoutException e) {
            e.printStackTrace();
        }
    }

    public void publish(MessageDto messageDto) throws IOException {
        channel.basicPublish("", QUEUE_NAME, null, toBytes(messageDto));

        System.out.println(" [x] Sent '" + messageDto + "'");
    }

    @Override
    public void finalize() {
        System.out.println("Destroy!!!");
        try {
            if (channel != null) {
                channel.close();
            }
        } catch (IOException | TimeoutException e) {
            e.printStackTrace();
        }
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
