package com.colak.publishsubscribe.pubsub.proxy;

import org.zeromq.SocketType;
import org.zeromq.ZContext;
import org.zeromq.ZMQ;

public class Proxy {

    public static void main() {

        try (ZContext context = new ZContext();
             ZMQ.Socket frontend = context.createSocket(SocketType.SUB);
             ZMQ.Socket backend = context.createSocket(SocketType.PUB)) {

            frontend.bind("ipc://frontend.ipc");
            frontend.subscribe("".getBytes());

            backend.bind("tcp://*:8888");

            try {
                System.out.println("Starting forwarder");
                ZMQ.proxy(frontend, backend, null);
            } catch (Exception e) {
                System.err.println(e.getMessage());
            } finally {
                frontend.close();
                backend.close();
            }

        }
    }
}
