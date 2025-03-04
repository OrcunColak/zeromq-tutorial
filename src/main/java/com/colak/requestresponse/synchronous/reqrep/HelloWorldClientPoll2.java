package com.colak.requestresponse.synchronous.reqrep;

import org.zeromq.SocketType;
import org.zeromq.ZContext;
import org.zeromq.ZMQ;
import org.zeromq.ZPoller;

public class HelloWorldClientPoll2 {

    public static void main() {
        try (ZContext context = new ZContext()) {

            // Create a request socket
            ZMQ.Socket socket = context.createSocket(SocketType.REQ);
            socket.connect("tcp://localhost:5555");

            // Send a request message
            String request = "Hello, Server!";
            System.out.println("Sending: " + request);
            socket.send(request.getBytes(ZMQ.CHARSET));

            // Receive the response
            // Create ZPoller
            ZPoller poller = new ZPoller(context);
            poller.register(socket, ZMQ.Poller.POLLIN);

            while (true) {
                // Poll indefinitely (-1 for blocking behavior) or set a timeout in milliseconds
                int events = poller.poll(1000);

                if (poller.isReadable(socket)) {
                    byte[] responseBytes = socket.recv();
                    if (responseBytes != null) {
                        String response = new String(responseBytes, ZMQ.CHARSET);
                        System.out.println("Received: " + response);
                        break;
                    }
                } else {
                    System.out.println("Still waiting for response...");
                }
            }
        }
    }
}
