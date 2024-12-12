package scopio.net;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.Socket;

import scopio.security.CryptoHandler;

public enum Request {
    PING("!ping") {
        @Override
        public void executeAction(ServerHandler server, byte[] data, Socket socket) {
            try {
                server.write("!a".getBytes(), socket);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    },
    GET_PUBLIC_KEY("!kpub") {
        @Override
        public void executeAction(ServerHandler server, byte[] data, Socket socket) {
            try {
                server.write(server.getPublicKey().getEncoded(), socket);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    },
    FILE_TRANSMIT("!file") {
        @Override
        public void executeAction(ServerHandler server, byte[] data, Socket socket) {
            String full = new String(data);
            String filename = full.substring(5, full.length());
            
        }
    };

    final String prefix;

    public abstract void executeAction(ServerHandler server, byte[] data, Socket socket);

    Request(String prefix) {
        this.prefix = prefix;
    }

    public String getPrefix() {
        return prefix;
    }

    public static Request findRequest(String prefix) {
        for(Request req : Request.values()) {
            if(req.getPrefix().equals(prefix)) {
                return req;
            }
        }
        return null;
    }

    public static byte[] makeRequest(Request request, byte[] data) {
        if(data != null) {
            try {
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                outputStream.write(request.getPrefix().getBytes());
                outputStream.write(data);
                byte[] combined = outputStream.toByteArray();
                return combined;
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }
        return request.getPrefix().getBytes();
    }
}