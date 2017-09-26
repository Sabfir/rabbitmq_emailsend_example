package com.opinta;

import com.opinta.model.MessageDto;
import com.opinta.service.MessagePublisher;

import java.io.IOException;

public class App {

    public static void main( String[] args ) {
        MessagePublisher messagePublisher = new MessagePublisher();

        try {
            messagePublisher.publish(new MessageDto(1, "your@gmail.com", "Plz take your parcel"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.exit(0);
    }
}
