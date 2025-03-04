package com.colak.requestresponse.synchronous.reqrep;

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
            socket.send(request.getBytes(ZMQ.CHARSET));

            // Receive the response
            ZMQ.Poller poller = context.createPoller(1);
            poller.register(socket, ZMQ.Poller.POLLIN);

            while (true) {
                // Timeout in milliseconds
                int events = poller.poll(1000);

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
