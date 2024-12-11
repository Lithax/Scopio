package scopio.net;

import java.net.Socket;

public class Client extends Thread {
    private ServerHandler server;
    private Socket socket;
    private byte[] buffer;

    public Client(Socket socket, byte[] buffer, ServerHandler server) {
        this.socket = socket;
        this.buffer = buffer;
        this.server = server;
    }

    @Override
    public void run() {
        while(true) {
            try {
                socket.getInputStream().read(buffer);
                String str = new String(buffer);
                Request.findRequest(str).executeAction(server, buffer, socket);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}