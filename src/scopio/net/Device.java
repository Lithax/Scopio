package scopio.net;
import java.util.ArrayList;
import java.util.List;

public class Device {
    private String ipv4Address;
    private List<Integer> ports;
    private String hostName;
    private List<Double> responseTimeouts;
    private double average = 0;
    private DeviceType type;

    public Device(String ipv4Address, String hostName, DeviceType type) {
        this.ipv4Address = ipv4Address;
        this.hostName = hostName;
        this.ports = new ArrayList<>();
        this.type = type;
    }

    public double getAverageResponseTime() {
        responseTimeouts.forEach((timeout) -> {this.average += timeout;});
        return average / responseTimeouts.size();
    }

    public void addResponseTimeout(double timeout) {
        responseTimeouts.add(timeout);
    }

    public void addPort(int port) {
        this.ports.add(port);
    }

    public String getHostName() {
        return hostName;
    }
    
    public String getIpv4Address() {
        return ipv4Address;
    }

    public List<Integer> getPorts() {
        return ports;
    }

    public DeviceType getType() {
        return type;
    }
}
