package com.colak.publishsubscribe.xpubxsub.proxy;

import org.zeromq.SocketType;
import org.zeromq.ZContext;
import org.zeromq.ZMQ;

public class Publisher {

    public static void main() {

        try (ZContext context = new ZContext();
             ZMQ.Socket publisher = context.createSocket(SocketType.PUB)) {

            publisher.connect("tcp://localhost:5555"); // Connect to XSUB

            int i = 1;
            while (true) {
                String message = "TopicA Hello " + i;
                publisher.send(message);
                System.out.println("Published: " + message);
                i++;
                Thread.sleep(1000);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
