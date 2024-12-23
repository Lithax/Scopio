package scopio.net;

import javax.crypto.SecretKey;
import scopio.security.CryptoHandler;
import scopio.util.CompressionHandler;

import java.net.Socket;

public class Client extends Thread {
    private ServerHandler server;
    private Socket socket;
    private byte[] buffer;
    private SecretKey aes;

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
                System.out.println(new String(buffer));
                buffer = CompressionHandler.decompress(buffer);
                if(aes != null) {
                    String str = new String(buffer);
                    String sub = str.substring(0, str.length() >= 5 ? 5 : str.length());
                    System.out.println("RAW: "+str);
                    System.out.println("CUT: "+sub);
                    buffer = CompressionHandler.compress(Request.findRequest(sub).executeAction(server, buffer, socket));
                } else {
                    byte[] decrypted = CryptoHandler.decrypt(buffer, aes);
                    String str = new String(decrypted);
                    String sub = str.substring(0, str.length() >= 5 ? 5 : str.length());
                    System.out.println("RAW: "+str);
                    System.out.println("CUT: "+sub);
                    buffer = CryptoHandler.encrypt(Request.findRequest(sub).executeAction(server, decrypted, socket), aes);
                }
                server.write(CompressionHandler.compress(buffer), socket);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void setAES(SecretKey key) {
        this.aes = key;
    }

    public Socket getSocket() {
        return socket;
    }
}