package edu.tseidler;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

class Clients {
    private final List<Client> clientList;
    Status status = Status.values()[ThreadLocalRandom.current().nextInt(0, 2)];

    Clients() {
        clientList = new ArrayList<>();
    }

    boolean notComplete() {
        return clientList.size() < 2;
    }

    boolean connected() {
        return clientList.size() == 2;
    }

    void addClientForSocket(Socket socket) throws IOException {
        final Client client = new Client(socket, status);
        clientList.add(client);
        client.sendMessage("hello");
        status = status.other();
    }

    void sendToActive(String message) {
        Client active = getActive();
        active.sendMessage(message);
    }

    private Client getActive() {
        if (clientList.get(0).isActive())
            return clientList.get(0);
        else
            return clientList.get(1);
    }

    public void sendToInactive(String message) {
        Client inactive = getInactive();
        inactive.sendMessage(message);
    }

    private Client getInactive() {
        if (clientList.get(0).isInactive())
            return clientList.get(0);
        else
            return clientList.get(1);
    }

    public String readFromActive() {
        Client active = getActive();
        String message = "";
        if (active.hasMessage()) {
            message = active.getMessage();
        }
        return message;
    }

    public void switchActive() {
        for (Client client : clientList) {
            client.switchActive();
        }
    }
}
