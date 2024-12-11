package scopio.net;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import scopio.log.LogLevel;
import scopio.log.Logger;

class ClientHandler extends Thread {
    private Socket socket;
    private InputStream in;
    private OutputStream out;
    private byte[] buffer;

    public ClientHandler(int buffer, String ip, int port) throws Exception {
        this.buffer = new byte[buffer];
        join(ip, port);
    }

    @Override
    public void run() {
       
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
}