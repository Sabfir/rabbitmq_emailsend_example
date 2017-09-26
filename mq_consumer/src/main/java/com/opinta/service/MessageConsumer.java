package com.opinta.service;

import com.opinta.model.MessageDto;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import static com.opinta.util.MessageUtils.fromBytes;

public class MessageConsumer {
    private final static String QUEUE_NAME = "message_queue";

    private EmailSender emailSender;
    private Connection connection;
    private Channel channel;

    public MessageConsumer() {
        emailSender = new EmailSender();

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        try {
            connection = factory.newConnection();
            channel = connection.createChannel();
            channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        } catch (IOException | TimeoutException e) {
            e.printStackTrace();
        }
    }

    public void start() throws IOException {
        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope,
                                       AMQP.BasicProperties properties, byte[] body)
                    throws IOException {
                MessageDto messageDto = (MessageDto) fromBytes(body);
                System.out.println(" [x] Received '" + messageDto + "'");

                emailSender.send(messageDto);
            }
        };
        channel.basicConsume(QUEUE_NAME, true, consumer);
    }

    @Override
    public void finalize() {
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
