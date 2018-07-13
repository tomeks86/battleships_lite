package edu.tseidler;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server {
    private final ServerSocket serverSocket;
    private final List<Client> clients;

    public Server() throws IOException {
        serverSocket = new ServerSocket(50000);
        clients = new ArrayList<>();
    }

    public void start() throws IOException {
        while (clients.size() < 2)
            acceptClients();
        new Thread(() -> {
            while (clients.size() == 2) {
                
            }
        }).start();
    }

    private void acceptClients() throws IOException {
        if (clients.size() < 2) {
            Socket socket = serverSocket.accept();
            Client client = new Client(socket);
            clients.add(client);
            client.sendMessage("hi");
        }
    }
}
