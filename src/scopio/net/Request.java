package scopio.net;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.nio.charset.Charset;

import scopio.security.CryptoHandler;
import scopio.security.HashingHandler;
import scopio.util.ByteString;

public enum Request {
    PING("!ping") {
        @Override
        public byte[] executeAction(ServerHandler server, byte[] data, Socket socket) throws Exception {
            return "a".getBytes(Charset.forName("ASCII"));
        }
    },
    GET_PUBLIC_KEY("!rsa_") {
        @Override
        public byte[] executeAction(ServerHandler server, byte[] data, Socket socket) throws Exception {
            return server.getPublicKey().getEncoded();
        }
    },
    EXCHANGE_AES("!aes_") {
        @Override
        public byte[] executeAction(ServerHandler server, byte[] data, Socket socket) throws Exception {
            byte[] sub = new ByteString(data).subBytes(6, data.length);
            server.getClientFromSocket(socket).setAES(CryptoHandler.byteToSecretKey(sub));
            return "approved".getBytes(Charset.forName("ASCII"));
        }
    },
    FILE_TRANSMIT("!file") {
        @Override
        public byte[] executeAction(ServerHandler server, byte[] data, Socket socket) throws Exception {
            String full = new String(data);
            String filename = full.substring(5, full.length());
            
        }
    };

    final String prefix;

    public abstract byte[] executeAction(ServerHandler server, byte[] data, Socket socket) throws Exception;

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