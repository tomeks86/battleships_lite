package edu.tseidler;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    private final Socket socket;
    private final PrintWriter clientWriter;
    private final Scanner clientInput;
    private Status status;

    Client(Socket socket, Status status) throws IOException {
        this.socket = socket;
        this.clientWriter = new PrintWriter(socket.getOutputStream());
        this.clientInput = new Scanner(socket.getInputStream());
        this.status = status;
    }

    public void sendMessage(String message) {
        clientWriter.println(message);
        clientWriter.flush();
    }

    public String getMessage() {
        return clientInput.nextLine();
    }

    public boolean hasMessage() {
        return clientInput.hasNextLine();
    }

    public boolean isActive() {
        return status == Status.ACTIVE;
    }

    public boolean isInactive() {
        return status == Status.INACTIVE;
    }

    public void switchActive() {
        this.status = status.other();
    }
}
