package com.colak.pushpull;

import org.zeromq.SocketType;
import org.zeromq.ZContext;
import org.zeromq.ZMQ;

import java.util.Random;

public class PushProducer {

    public static void main() {
        try (ZContext context = new ZContext();
             ZMQ.Socket sender = context.createSocket(SocketType.PUSH)) {

            sender.bind("tcp://*:5555");

            Random random = new Random();
            for (int i = 1; i <= 10; i++) {
                String task = "Task-" + i;
                sender.send(task);
                System.out.println("Sent: " + task);
                try {
                    // Simulate work
                    Thread.sleep(random.nextInt(500));
                } catch (InterruptedException ignored) {
                }
            }
            sender.close();
        }
    }
}
