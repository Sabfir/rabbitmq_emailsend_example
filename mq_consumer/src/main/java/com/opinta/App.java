package com.opinta;

import com.opinta.service.MessageConsumer;

import java.io.IOException;

public class App {

    public static void main( String[] args ) {
        MessageConsumer messageConsumer = new MessageConsumer();

        try {
            messageConsumer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
