package ru.otus.l161;

import ru.otus.l161.channel.SocketMsgWorker;

import java.io.IOException;
import java.net.Socket;

/**
 * Created by tully.
 */
class ClientSocketMsgWorker extends SocketMsgWorker {

    private final Socket socket;

    ClientSocketMsgWorker(String host, int port) throws IOException {
        this(new Socket(host, port));
    }

    private ClientSocketMsgWorker(Socket socket) {
        super(socket);
        this.socket = socket;
        String clientString = "Remoting client: " + socket.getRemoteSocketAddress().toString().substring(1);
        System.out.println(clientString);
    }

    @Override
    public void close() {
        try {
            super.close();
            socket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
