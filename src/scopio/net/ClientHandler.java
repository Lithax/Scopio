package scopio.net;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import scopio.gui.Alerts;
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
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public byte[] read() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        int bytesRead;
        try {
            while ((bytesRead = in.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
            return outputStream.toByteArray();
        } catch (Exception e) {
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