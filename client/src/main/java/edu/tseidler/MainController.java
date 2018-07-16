package edu.tseidler;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class MainController {
    @FXML
    private Label messageReceived;
    @FXML
    private TextField messageToSend;
    @FXML
    private Button send;

    private Socket socket;
    private PrintWriter socketWriter;
    private Scanner socketReader;

    public void initialize() throws IOException {
        socket = new Socket("localhost", 50000);
        socketWriter = new PrintWriter(socket.getOutputStream());
        socketReader = new Scanner(socket.getInputStream());
        final Thread socketListeningThread = new Thread(() -> {
            while (socketReader.hasNext()) {
                String message = socketReader.nextLine();
                if (send.isDisable() &&
                        (message.startsWith(Messages.START.name()) || message.startsWith(Messages.CHANGE.name()))) {
                    send.setDisable(false);
                }
                Platform.runLater(() ->
                        messageReceived.setText(message));
            }
        });
        socketListeningThread.setDaemon(true);
        socketListeningThread.start();
    }

    public void sendMessage(ActionEvent actionEvent) {
        String message = messageToSend.getText();
        if (message.startsWith(Messages.CHANGE.name())) {
            send.setDisable(true);
        }
        socketWriter.println(message);
        socketWriter.flush();
    }
}
