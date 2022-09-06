package ru.job4j.io;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class EchoServer {

    private static final Logger LOG = LoggerFactory.getLogger(EchoServer.class.getName());

    public static void main(String[] args) {
        try (ServerSocket server = new ServerSocket(9000)) {
            while (!server.isClosed()) {
                Socket socket = server.accept();
                try (OutputStream out = socket.getOutputStream();
                     BufferedReader in = new BufferedReader(
                             new InputStreamReader(socket.getInputStream()))) {
                    out.write("HTTP/1.1 200 OK\r\n\r\n".getBytes());
                    for (String str = in.readLine(); str != null && !str.isEmpty(); str = in.readLine()) {
                        final String pattern = "(\\w+)\\s(/\\?msg=)(.*)\\s(.*)";
                        String requestType = str.replaceAll(pattern, "$1");
                        String msg = str.replaceAll(pattern, "$3");
                        if ("GET".equals(requestType)) {
                            if ("Hello".equals(msg)) {
                                out.write("Hello".getBytes());
                            } else if ("Exit".equals(msg)) {
                                System.out.println("Closing the server...");
                                server.close();
                            } else if (!msg.isEmpty()) {
                                out.write("What?".getBytes());
                            }
                        }
                        System.out.println(str);
                    }
                    out.flush();
                }
            }
        } catch (IOException e) {
            LOG.error("Error", e);
        }
    }
}
