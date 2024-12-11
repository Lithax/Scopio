package scopio.net;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

import scopio.log.LogLevel;
import scopio.log.Logger;

class ServerHandler extends Thread {
    private ServerSocket serverSocket;
    private List<Socket> clientSockets;
    private byte[] buffer;
    private int port;

    public ServerHandler(int buffer, String ip, int port, int maxConnections) throws Exception {
        this.port = port;
        this.buffer = new byte[buffer];
        join(ip, port, maxConnections);
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
}