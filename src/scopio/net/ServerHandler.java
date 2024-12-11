package scopio.net;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.List;
import java.util.ArrayList;

import scopio.log.LogLevel;
import scopio.log.Logger;
import scopio.security.CryptoHandler;

class ServerHandler extends Thread {
    private ServerSocket serverSocket;
    private List<Socket> clientSockets = new ArrayList<>();
    private byte[] buffer;
    private int port;
    private PublicKey pub;
    private PrivateKey priv;

    public ServerHandler(int buffer, String ip, int port, int maxConnections) throws Exception {
        this.buffer = new byte[buffer];
        join(ip, port, maxConnections);
        KeyPair pair = CryptoHandler.generateAsyncKey(512);
        this.pub = pair.getPublic();
        this.priv = pair.getPrivate();
    }

    @Override
    public void run() {
        while(true) {
            try {
                Socket socket = serverSocket.accept();
                synchronized(clientSockets) {
                    new Client(socket, buffer, this).start();
                    clientSockets.add(socket);
                }
            } catch (Exception e) {
                new Logger().writeNewLogEntry(e.getMessage(), LogLevel.ERROR);
                e.printStackTrace();
            }
        }
    }

    public boolean join(String ip, int port, int maxConnections) throws Exception {
        serverSocket = new ServerSocket(port, maxConnections, InetAddress.getByName(ip));
        this.port = serverSocket.getLocalPort();
        new Logger().writeNewLogEntry("Started Server "+ip+":"+port, LogLevel.INFO);
        return true;
    }

    public boolean write(byte[] bytes, int index) {
        try {
            clientSockets.get(index).getOutputStream().write(bytes);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean write(byte[] bytes, String ip) {
        try {
            InetAddress addr = InetAddress.getByName(ip);
            for(Socket clientSocket : clientSockets) {
                if(clientSocket.getInetAddress().equals(addr)) {
                    clientSocket.getOutputStream().write(bytes);
                }
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean write(byte[] bytes, Socket socket) {
        try {
            for(Socket clientSocket : clientSockets) {
                if(clientSocket.equals(socket)) {
                    clientSocket.getOutputStream().write(bytes);
                    clientSocket.getOutputStream().flush();
                    //System.out.println("WROTE: "+new String(bytes)+" END WROTE");
                }
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean writeAll(byte[] bytes) {
        try {
            for(Socket clientSocket : clientSockets) {
                clientSocket.getOutputStream().write(bytes);
                clientSocket.getOutputStream().flush();
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public void close() {
        try {
            serverSocket.close();
            synchronized (clientSockets) {
                for (Socket clientSocket : clientSockets) {
                    clientSocket.close();
                }
            }
            clientSockets.clear();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int getPort() {
        return port;
    }

    public PublicKey getPublicKey() {
        return pub;
    }

    public PrivateKey getPrivateKey() {
        return priv;
    }
}