package com.colak.publishsubscribe.pubsub.slow;

import org.zeromq.SocketType;
import org.zeromq.ZContext;
import org.zeromq.ZMQ;

import java.util.concurrent.TimeUnit;

public class SlowSubscriber {

    public static void main() throws InterruptedException {
        try (ZContext context = new ZContext()) {
            ZMQ.Socket subscriber = context.createSocket(SocketType.SUB);

            int receiveBufferSize = subscriber.getReceiveBufferSize();
            System.out.println("receiveBufferSize = " + receiveBufferSize);

            int rcvHWM = subscriber.getRcvHWM();
            System.out.println("rcvHWM = " + rcvHWM);

            boolean result = subscriber.setReceiveBufferSize(1000);
            if (!result) {
                System.out.println("setReceiveBufferSize failed");
            }

            // Controls how many messages it can buffer before dropping messages.
            // Only store 1 message in the subscriber queue
            result = subscriber.setRcvHWM(1);
            if (!result) {
                System.out.println("setRcvHWM failed");
            }

            subscriber.connect("tcp://localhost:5556");

            // Subscribe to all messages
            subscriber.subscribe("");

            int expectedMessageId = 0;

            while (true) {
                String message = subscriber.recvStr();
                System.out.println("SLOW Subscriber Received: " + message);

                // Extract message ID
                int receivedMessageId = Integer.parseInt(message.split(" ")[1]);

                // Check for missed messages
                if (receivedMessageId != expectedMessageId) {
                    System.out.println("Missed messages from " + expectedMessageId + " to " + (receivedMessageId - 1));
                }

                expectedMessageId = receivedMessageId + 1;

                // Slow processing
                TimeUnit.SECONDS.sleep(2);
            }
        }
    }
}
