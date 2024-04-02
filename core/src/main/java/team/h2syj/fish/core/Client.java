package team.h2syj.fish.core;

import java.io.IOException;
import java.net.Socket;

public class Client {

    private final Socket socket;

    public Client(String host, int port) {
        try {
            this.socket = new Socket(host, port);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void run() {

    }
}
