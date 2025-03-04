package com.colak.publishsubscribe.pubsub.proxy;

import org.zeromq.SocketType;
import org.zeromq.ZContext;
import org.zeromq.ZMQ;

import java.util.Random;

// This is frontend
public class Publisher {

    public static void main() throws Exception {

        try (ZContext context = new ZContext();
             ZMQ.Socket publisher = context.createSocket(SocketType.PUB)) {

            Random random = new Random(System.currentTimeMillis());
            int serverNo = random.nextInt(10000);

            publisher.connect("ipc://frontend.ipc");

            System.out.printf("Server : %s%n", serverNo);

            try {
                while (!Thread.currentThread().isInterrupted()) {
                    String topic = String.format("%s", random.nextInt(10));
                    publisher.sendMore(topic);

                    String payload = String.format("Server#%s", serverNo);
                    publisher.send(payload);

                    System.out.println("Sending: " + payload + " on Channel " + topic);
                    Thread.sleep(250);
                }
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }
    }
}
