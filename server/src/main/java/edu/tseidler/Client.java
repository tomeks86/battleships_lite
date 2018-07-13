package edu.tseidler;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    private final Socket socket;
    private final PrintWriter clientWriter;
    private final Scanner clientInput;

    Client(Socket socket) throws IOException {
        this.socket = socket;
        clientWriter = new PrintWriter(socket.getOutputStream());
        clientInput = new Scanner(socket.getInputStream());
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
}
