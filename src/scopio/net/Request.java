package scopio.net;
import java.io.IOException;

public enum Request {
    PING("!p") {
        @Override
        public void executeAction(Transmittor transmittor, String data) {
            try {
                transmittor.getOut().write("!a\n");
                transmittor.getOut().flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    };

    final String prefix;

    public abstract void executeAction(Transmittor transmittor, String data);

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