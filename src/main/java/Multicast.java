
import java.io.IOException;
import java.net.*;
import java.util.List;

public class Multicast extends Thread{
    private static final String MY_IP = "";

    protected MulticastSocket multicastSocket = null;
    protected InetAddress group;
    protected MyLocalPeer myLocalPeer;
    protected byte[] buffer = new byte[256];

    Multicast(MyLocalPeer myLocalPeer){
        this.myLocalPeer = myLocalPeer;
    }

    @Override
    public void run() {
        try {

            multicastSocket = new MulticastSocket(4446);
            group = InetAddress.getByName("230.0.0.0");
            multicastSocket.joinGroup(group);

            sendIP();
            receiveIP();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendIP() throws IOException {
        DatagramSocket datagramSocket = new DatagramSocket();
        buffer = MY_IP.getBytes();

        DatagramPacket packet
                = new DatagramPacket(buffer, buffer.length, group, 4446);
        datagramSocket.send(packet);

        datagramSocket.close();
    }

    public void receiveIP() throws IOException {
        while (true) {
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
            multicastSocket.receive(packet);
            String received = new String(packet.getData(), 0, packet.getLength());

            addIP(received);
            sendIP();
        }
    }

    public boolean checkIPExistence(String IP) {
        if (myLocalPeer.getNetworkPeers().indexOf(IP) == -1) {
            return false;
        }
        return true;
    }

    public void addIP(String IP) {
        if (checkIPExistence(IP)){
            myLocalPeer.addIP(IP);
        }

    }

    public void updateAddressesList(List<String> existingAddresses){
        existingAddresses = this.myLocalPeer.getNetworkPeers();
    }
}
