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

    public void start() {
        new Thread(() -> {
            while (true) {
                try {
                    acceptClients();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void acceptClients() throws IOException {
        Socket socket = serverSocket.accept();
        Client client = new Client(socket);
        clients.add(client);
        new Thread(() -> {
            while (client.hasMessage()) {
                String fromClient = client.getMessage();
                client.sendMessage(fromClient);
            }
        }).start();
    }
}
