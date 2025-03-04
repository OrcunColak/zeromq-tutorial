package com.colak.publishsubscribe.xpubxsub.proxy;

import org.zeromq.*;

public class Proxy {

    public static void main() {

        try (ZContext context = new ZContext();
             ZMQ.Socket frontend = context.createSocket(SocketType.XSUB);
             ZMQ.Socket backend = context.createSocket(SocketType.XPUB)) {

            // Publishers connect here
            frontend.bind("tcp://*:5555");

            // Subscribers connect here
            backend.bind("tcp://*:5556");

            System.out.println("Proxy running...");

            // Forward messages
            ZMQ.proxy(frontend, backend, null);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

