package scopio.net;
import java.net.NetworkInterface;
import java.util.List;

public class NetworkManager {
    private int timeout;
    
    public NetworkManager(int timeout) {
        this.timeout = timeout;
    }

    public List<Network> searchInterfaces() {

    }

    public Network searchNetwork(NetworkInterface networkinterface) {
        networkinterface.   
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    public int getTimeout() {
        return timeout;
    }
}