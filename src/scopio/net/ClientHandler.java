package scopio.net;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.security.PublicKey;
import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import javax.crypto.SecretKey;

import scopio.log.LogLevel;
import scopio.log.Logger;
import scopio.security.CryptoHandler;

class ClientHandler extends Thread {
    private Socket socket;
    private InputStream in;
    private OutputStream out;
    private byte[] buffer;
    private SecretKey aes;

    public ClientHandler(int buffer, String ip, int port) throws Exception {
        this.buffer = new byte[buffer];
        join(ip, port);
    }

    @Override
    public void run() {
       
    }

    public boolean handshake(int aes_keysize) {
        try {
            byte[] pkey = writeAndRead(Request.makeRequest(Request.GET_PUBLIC_KEY, buffer));
            this.aes = CryptoHandler.generateSyncKey(aes_keysize);
            PublicKey pub = CryptoHandler.byteToKey(pkey);
            byte[] enc_aes = CryptoHandler.encrypt(aes.getEncoded(), pub);
            if(new String(writeAndRead(Request.makeRequest(Request.EXCHANGE_AES, enc_aes)), "ASCII").equals("approved")) {
                new Logger().writeNewLogEntry("Server "+getServerString()+" approved aes exchange", LogLevel.SUCESS);
                return true;
            } else {
                new Logger().writeNewLogEntry("Server "+getServerString()+" declined aes exchange", LogLevel.FAILED);
                this.aes = null;
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean join(String ip, int port) throws Exception {
        socket = new Socket(ip, port);
        new Logger().writeNewLogEntry("Connected to Server "+ip+":"+port, LogLevel.INFO);
        in = socket.getInputStream();
        out = socket.getOutputStream();
        return true;
    }

    public boolean write(byte[] bytes) {
        try {
            out.write(bytes);
            out.flush();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public byte[] writeAndRead(byte[] data) {
        try {
            ExecutorService executor = Executors.newSingleThreadExecutor();
            Future<byte[]> future = executor.submit(() -> {
                write(data);
                return read();
            });
            byte[] readData = future.get();
            executor.shutdown();
            return readData;
        } catch (Exception e) {
            return null;
        }
    }

    public byte[] read() {
        try {
            int bytesRead = in.read(buffer);
            if (bytesRead == -1) {
                return null;
            }
            return Arrays.copyOf(buffer, bytesRead);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void close() {
        try {
            socket.close();
            socket = null;
            in = null;
            out = null;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getServerString() {
        return socket.getInetAddress().getHostAddress()+":"+socket.getPort();
    }
}