package com.colak.publishsubscribe.pubsub.proxy;

import org.zeromq.SocketType;
import org.zeromq.ZContext;
import org.zeromq.ZMQ;

// This is backend
public class Subscriber {

    public static void main() {

        try (ZContext context = new ZContext();
             ZMQ.Socket subscriber = context.createSocket(SocketType.SUB)) {

            subscriber.connect("tcp://127.0.0.1:8888");
            subscriber.subscribe("1");

            try {
                while (!Thread.currentThread().isInterrupted()) {
                    String topic = subscriber.recvStr();
                    String payload = subscriber.recvStr();
                    System.out.println(topic + " : " + payload);
                }
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }
    }
}
