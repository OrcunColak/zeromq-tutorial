package com.colak.pushpull;

import org.zeromq.SocketType;
import org.zeromq.ZContext;
import org.zeromq.ZMQ;

import java.util.concurrent.TimeUnit;

public class PullWorker {

    public static void main() {
        try (ZContext context = new ZContext(1);
             ZMQ.Socket receiver = context.createSocket(SocketType.PULL)) {

            receiver.connect("tcp://localhost:5555");

            while (true) {
                String task = receiver.recvStr();
                System.out.println(Thread.currentThread().getName() + " received: " + task);
                try {
                    // Simulate work
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException ignored) {
                }
            }
        }
    }


}
