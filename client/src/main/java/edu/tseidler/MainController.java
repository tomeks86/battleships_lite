package edu.tseidler;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class MainController {
    private Socket socket;
    private PrintWriter socketWriter;
    private Scanner socketReader;

    public void initialize() throws IOException {
        socket = new Socket("localhost", 50000);
        socketWriter = new PrintWriter(socket.getOutputStream());
        socketReader = new Scanner(socket.getInputStream());
        new Thread(() -> {
            while (true) {
                socketWriter.println("hello");
                socketWriter.flush();
                String back = socketReader.nextLine();
                System.out.println(back);
            }
        }).start();
    }
}
