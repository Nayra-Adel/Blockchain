import org.apache.commons.lang3.SerializationUtils;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;


public class MulticastReceiver extends Thread {
/*    protected MulticastSocket socket = null;
    protected byte[] buf = new byte[256];*/

    protected MulticastSocket multicastSocket = null;
    protected Socket socket;
    protected ServerSocket serverSocket;
    protected InetAddress group;
    protected List<InetAddress> addresses = new ArrayList<InetAddress>();
    protected DataInputStream dataInputStream;
    protected DataOutputStream dataOutputStream;
    protected byte[] buf = new byte[256];

    /*public void run() {
        MulticastReceiver multicastReceiver = new MulticastReceiver();
        try {
            //multicastReceiver.join_Group();
            join_Group();
            receive();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/

    public void run2() {
        MulticastReceiver multicastReceiver = new MulticastReceiver();
        try {
            multicastReceiver.join_Group();

            multicastReceiver.receive();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void join_Group() throws IOException {
        multicastSocket = new MulticastSocket(4446);
        group = InetAddress.getByName("230.0.0.0");
        multicastSocket.joinGroup(group);
    }


    public boolean checkIPExistence(InetAddress ip) {
        if (addresses.indexOf(ip) == -1) {
            return false;
        }
        return true;
    }

    public void leaveGroup() throws IOException {
        multicastSocket.leaveGroup(group);
        dataInputStream.close();
        socket.close();
    }

    public void sendIP() throws IOException {
        DatagramSocket socket = new DatagramSocket();
        String message = "";
        buf = message.getBytes();

        DatagramPacket packet
                = new DatagramPacket(buf, buf.length, group, 4446);
        socket.send(packet);

        socket.close();
    }

    public void recieveIP() throws IOException {
        while (true) {
            String rcv = "nothing";
            Socket recievedSocket = serverSocket.accept();
            DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
            rcv = dataInputStream.readUTF();

            System.out.println("data recvd: " + rcv);

            if (buf.toString().equals("end")) break;
        }
    }

    public void registerIP(InetAddress ip) {
        if (checkIPExistence(ip))
            addresses.add(ip);
    }

    public void send(String b) throws IOException {

        socket = new Socket("10.1.22.31", 4445);
        dataOutputStream = new DataOutputStream(socket.getOutputStream());

        dataOutputStream.writeUTF(b);

    }

    public void receive() throws IOException {

        serverSocket = new ServerSocket(4445);

        Socket recievedSocket = serverSocket.accept();
        DataInputStream dataInputStream = new DataInputStream(recievedSocket.getInputStream());

        Block block =SerializationUtils.deserialize(buf);
        System.out.println("bravo 3lik "+ block.getHash());
    }

    public void run() {
        try {

            MulticastSocket socket1 = new MulticastSocket(4446);
            InetAddress group = InetAddress.getByName("230.0.0.0");
            socket1.joinGroup(group);

            while (true) {
                DatagramPacket packet = new DatagramPacket(buf, buf.length);
                socket1.receive(packet);
                String received = new String(
                        packet.getData(), 0, packet.getLength());
                if ("end".equals(received)) {
                    break;
                }

                System.out.println(received);
            }

            socket1.leaveGroup(group);
            socket1.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
