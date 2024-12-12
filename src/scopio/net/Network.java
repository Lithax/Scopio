package scopio.net;

import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.util.ArrayList;
import java.util.List;

public class Network {
    private String interfacename;
    private String networkAdr;
    private String mask;
    private List<Device> devices;
    private NetworkInterface networkInterface;
    
    public Network(NetworkInterface networkInterface, int timeout) {
        if (networkInterface.getInterfaceAddresses().isEmpty()) {
            this.mask = "0.0.0.0";
            this.networkAdr = "0.0.0.0";
        } else {
            InterfaceAddress interfaceAddress = networkInterface.getInterfaceAddresses().get(0);
            this.mask = getSubnetMask(networkInterface);
            this.networkAdr = getNetworkAddress(interfaceAddress);
        }
        this.networkInterface = networkInterface;
        this.interfacename = networkInterface.getDisplayName();
        this.devices = searchDevices(timeout);
    }
    

    public static String getSubnetMask(NetworkInterface ni) {
        for (InterfaceAddress ia : ni.getInterfaceAddresses()) {
            short prefixLength = ia.getNetworkPrefixLength();
            int mask = ~0 << (32 - prefixLength);
            return String.format("%d.%d.%d.%d",
                    (mask >> 24) & 0xff,
                    (mask >> 16) & 0xff,
                    (mask >> 8) & 0xff,
                    mask & 0xff);
        }
        return null;
    }

    public static String getNetworkAddress(InterfaceAddress ia) {
        byte[] ip = ia.getAddress().getAddress();
        byte[] mask = ia.getNetworkPrefixLength() == 0 ? new byte[4] : getMaskBytes(ia.getNetworkPrefixLength());
        byte[] network = new byte[ip.length];
        for (int i = 0; i < ip.length; i++) {
            network[i] = (byte) (ip[i] & mask[i]);
        }
        try {
            return InetAddress.getByAddress(network).getHostAddress();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static byte[] getMaskBytes(short prefixLength) {
        int mask = ~0 << (32 - prefixLength);
        return new byte[]{
                (byte) ((mask >> 24) & 0xff),
                (byte) ((mask >> 16) & 0xff),
                (byte) ((mask >> 8) & 0xff),
                (byte) (mask & 0xff)
        };
    }

    public List<Device> searchDevices(int timeout) {
        List<Device> devices = new ArrayList<>();
        try {
            String[] parts = networkAdr.split("\\.");
            if (parts.length != 4) {
                return devices;
            }
            String base = String.join(".", parts[0], parts[1], parts[2]);
            for (int i = 1; i < 255; i++) {
                String host = base + "." + i;
                try {
                    InetAddress address = InetAddress.getByName(host);
                    if (address.isReachable(timeout)) {
                        devices.add(new Device(host, address.getHostName(), DeviceType.UNKNOWN));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return devices;
    }
    

    public NetworkInterface getNetworkInterface() {
        return networkInterface;
    }

    public String getNetworkadr() {
        return networkAdr;
    }

    public List<Device> getDevices() {
        return devices;
    }
    
    public String getMask() {
        return mask;
    }

    public String getInterfacename() {
        return interfacename;
    }
}
