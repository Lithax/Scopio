package scopio.net;

import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import scopio.log.LogLevel;
import scopio.log.Logger;

public class NetworkManager {
    private int timeout;
    
    public NetworkManager(int timeout) {
        this.timeout = timeout;
    }

    public List<Network> searchInterfaces() {
        List<Network> networks = new ArrayList<>();
        try {
            Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
            while(interfaces.hasMoreElements()) {
                NetworkInterface networkInterface = interfaces.nextElement();
                if (!networkInterface.isUp() || networkInterface.isLoopback()) {
                    continue;
                }
                System.out.println(networkInterface.getDisplayName());
                networks.add(new Network(networkInterface, timeout));
            }
            return networks;
        } catch (SocketException e) {
            new Logger().writeNewLogEntry(e.getMessage(), LogLevel.ERROR);
            return null;
        }
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    public int getTimeout() {
        return timeout;
    }

    public static void main(String[] args) {
        for(Network network : new NetworkManager(2000).searchInterfaces()) {
            System.out.println(network.getInterfacename()+" "+ network.getMask()+ " "+network.getNetworkadr());
            for(Device dev : network.getDevices()) {
                System.out.println("DEVICE: "+dev.getAverageResponseTime()+" "+dev.getHostName()+" "+dev.getIpv4Address()+ " ");
            }
        }
    }
}