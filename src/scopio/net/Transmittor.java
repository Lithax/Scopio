package scopio.net;

import scopio.log.*;

class Transmittor {
    private byte[] buffer;
    public static final int SERVER = 1;
    public static final int CLIENT = 2;

    public Transmittor(int type, int buffer) {
        this.buffer = new byte[buffer];
        if(type == SERVER) {

        } else { //CLIENT

        }
    }

    //TESTING METHOD
    public static void main(String[] args) {
        try {
            new Logger().writeNewLogEntry("Default Entry", LogLevel.INFO);
            ServerHandler server = new ServerHandler(4096, "0.0.0.0", 0, 50);
            server.start();
            //System.out.println(new String(server.getPublicKey().getEncoded()));
            System.out.println(server.getPort());
            ClientHandler client = new ClientHandler(4096, "192.168.56.1", server.getPort());
            client.start();
            System.out.println(new String(client.writeAndRead("!pub".getBytes()))+"r");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}