package com.colak.requestresponse;

import org.zeromq.SocketType;
import org.zeromq.ZContext;
import org.zeromq.ZMQ;

public class HelloWorldClientPoll {

    public static void main() {
        try (ZContext context = new ZContext()) {

            // Create a request socket
            ZMQ.Socket socket = context.createSocket(SocketType.REQ);
            socket.connect("tcp://localhost:5555");

            // Send a request message
            String request = "Hello, Server!";
            System.out.println("Sending: " + request);
            socket.send(request.getBytes(ZMQ.CHARSET), 0);

            // Receive the response
            ZMQ.Poller poller = context.createPoller(1);
            poller.register(socket, ZMQ.Poller.POLLIN);

            while (true) {
                int events = poller.poll(1000); // Timeout in milliseconds
                if (events > 0 && poller.pollin(0)) {
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
