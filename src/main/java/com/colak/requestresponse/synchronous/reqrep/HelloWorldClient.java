package com.colak.requestresponse.synchronous.reqrep;

import org.zeromq.SocketType;
import org.zeromq.ZContext;
import org.zeromq.ZMQ;

public class HelloWorldClient {

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
            byte[] reply = socket.recv(0);
            System.out.println("Received: " + new String(reply, ZMQ.CHARSET));
        }
    }
}
