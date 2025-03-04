package com.colak.publishsubscribe.xpubxsub.proxy;

import org.zeromq.*;

public class Proxy {

    public static void main() {

        try (ZContext context = new ZContext();
             ZMQ.Socket xpub = context.createSocket(SocketType.XPUB);
             ZMQ.Socket xsub = context.createSocket(SocketType.XSUB)) {

            xpub.bind("tcp://*:5556"); // Subscribers connect here
            xsub.bind("tcp://*:5555"); // Publishers connect here

            System.out.println("Proxy running...");
            ZMQ.proxy(xsub, xpub, null); // Forward messages

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

