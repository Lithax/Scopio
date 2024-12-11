package scopio.net;
import java.net.Socket;

public enum Request {
    PING("!p") {
        @Override
        public void executeAction(ServerHandler server, byte[] data, Socket socket) {
            try {
                server.write("!a\n".getBytes(), socket);
            } catch (Exception e) {
                e.printStackTrace();
            }
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
}