package scopio.net;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import scopio.gui.Alerts;

class Transmittor {
    private Socket socket;
    private BufferedReader in;
    private BufferedWriter out;
    private byte[] buffer;
    public static final int SERVER = 1;
    public static final int CLIENT = 2;

    public Transmittor(int type, int buffer) {
        if(type == SERVER) {

        } else {

        }
        this.buffer = new byte[buffer];
        try {
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void run() {
        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        new Thread(() -> {
            try {
                String msg = in.readLine();
                Request.findRequest(msg.substring(0,2));
            } catch (Exception e) {
                Alerts.showConnectionCut();
            }
        }).start();

        new Thread(() -> {
            try {
                
            } catch (Exception e) {
                Alerts.showConnectionCut();
            }
        }).start();
    }

    public void join(String ip, int port) {

    }

    public void host() {
        try {
            ServerSocket serversocket = new ServerSocket();
            serversocket.accept();
            serversocket.close();
        } catch (Exception e) {
            
        }
    }

    public void close() {
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public BufferedReader getIn() {
        return in;
    }

    public BufferedWriter getOut() {
        return out;
    }
}