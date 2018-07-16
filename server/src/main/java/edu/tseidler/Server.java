package edu.tseidler;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private final ServerSocket serverSocket;
    private final Clients clients;

    public Server() throws IOException {
        serverSocket = new ServerSocket(50000);
        clients = new Clients();
    }

    public void start() throws IOException {
        while (clients.notComplete())
            acceptClients();
        new Thread(() -> {
            clients.sendToActive(Messages.START.name());
            clients.sendToInactive(Messages.WAIT.name());
            while (clients.connected()) {
                String message = clients.readFromActive();
                clients.sendToInactive(message);
                if (message.startsWith(Messages.CHANGE.name()))
                    clients.switchActive();
            }
        }).start();
    }

    private void acceptClients() throws IOException {
        Socket socket = serverSocket.accept();
        clients.addClientForSocket(socket);
    }
}
