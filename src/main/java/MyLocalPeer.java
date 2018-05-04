import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import jdk.internal.util.xml.impl.Input;
import org.apache.commons.lang3.SerializationUtils;

import static org.bouncycastle.crypto.tls.CipherType.block;

public class MyLocalPeer extends Thread implements Serializable {

    private MulticastSocket multicastSocket = null;
    private DatagramSocket datagramSocket = null;
    private InetAddress group;

    private List<String> networkPeers = new ArrayList<>();
    private Blockchain blockchain;

    private byte[] buffer;

    private Socket socket = null;
    private ServerSocket serverSocket = null;

    private OutputStream outputStream;


    /*	@Override
        public void run() {
            try {
                recieveBlock();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }*/
    @Override
    public void run() {
        try {
           int x= recieve_valid_blockchain_size();
            System.out.println("int "+x);

        } catch (Exception e) {
            e.printStackTrace();
        }}

    public void joinNetwork(String myIP_Address) throws IOException { // myIP_Address = "10.1.11.41"
        multicastSocket = new MulticastSocket(4446);
        group = InetAddress.getByName("230.0.0.0");
        multicastSocket.joinGroup(group);

        DatagramSocket datagramSocket = new DatagramSocket();
        buffer = myIP_Address.getBytes();

        DatagramPacket packet = new DatagramPacket(buffer, buffer.length, group, 4446);
        datagramSocket.send(packet);

        datagramSocket.close();
    }

    public void recieveIP_Address() throws IOException {

        while (true) {
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
            multicastSocket.receive(packet);

            String rcvdMessage = new String(packet.getData(), 0, packet.getLength());

            networkPeers.add(rcvdMessage);
        }
    }

    public void recieveBlock() throws IOException {
        serverSocket = new ServerSocket(4445);
        while (true) {
            //System.out.println("from the first thread vytcrtcytcyrtyccyrcyrhvtyctuxcryxcr ycr");
            Socket rcvdSocket = serverSocket.accept();
            //new PeerThread(rcvdSocket, this.blockchain).start();
            DataInputStream inputStream = new DataInputStream(rcvdSocket.getInputStream());
            String s = inputStream.readUTF();
            //Block block = SerializationUtils.deserialize(buffer);
            //blockchain.getBlocks().add(block);
            System.out.println("bravo 3lik " + s);
        }
    }

    public void sendBlock(Block block) throws UnknownHostException, IOException {

        for (int i = 0; i < networkPeers.size(); i++) {

            socket = new Socket(networkPeers.get(i), 4445);
            outputStream = socket.getOutputStream();

            buffer = SerializationUtils.serialize(block);
            outputStream.write(buffer);
        }

    }

    public void sendHistory(String IP_Address) throws UnknownHostException, IOException {
        socket = new Socket(IP_Address, 4445);
        outputStream = socket.getOutputStream();

        buffer = SerializationUtils.serialize(blockchain);
        outputStream.write(buffer);

        ObjectOutputStream objectOutputStream;
        objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
        objectOutputStream.writeObject(networkPeers);
    }

    public void recieveHistory() {

    }

    void saveMyLocalPeer() throws IOException {

        FileOutputStream fileOutputStream = new FileOutputStream(new File("MyLocalPeer.txt"));
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);

        objectOutputStream.writeObject(this);

        objectOutputStream.close();
        fileOutputStream.close();
    }


    void loadMyLocalPeer() throws IOException, ClassNotFoundException {
        FileInputStream fileInputStream = new FileInputStream(new File("MyLocalPeer.txt"));
        ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);

        MyLocalPeer myLocalPeer = new MyLocalPeer();
        myLocalPeer = (MyLocalPeer) objectInputStream.readObject();

        this.multicastSocket = myLocalPeer.multicastSocket;
        this.datagramSocket = myLocalPeer.datagramSocket;
        this.group = myLocalPeer.group;

        this.networkPeers = myLocalPeer.networkPeers;
        this.blockchain = myLocalPeer.blockchain;

        this.buffer = myLocalPeer.buffer;

        this.socket = myLocalPeer.socket;
        this.serverSocket = myLocalPeer.serverSocket;

        this.outputStream = myLocalPeer.outputStream;

        objectInputStream.close();
        fileInputStream.close();
    }

    public void recieveMessage() throws IOException {
        System.out.println("this is receive mesaage1");
        serverSocket = new ServerSocket(4445);
        socket = serverSocket.accept();

        DataInputStream inputStream = new DataInputStream(socket.getInputStream());

        String s = inputStream.readUTF();
        System.out.println(s);
    }


    public void sendMessage(String s) throws Exception {
        socket = new Socket("192.168.43.213", 4445);

        networkPeers.add("192.168.43.213");
        for (int i = 0; i < networkPeers.size(); i++) {

            DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());
            outputStream.writeUTF(s);
        }
    }

    public int recieve_valid_blockchain_size() throws Exception {
        ServerSocket serverSocket = new ServerSocket(4448);
        networkPeers.add("192.168.43.213");
        int blockchain_size = 0;
        for (int i = 0; i < networkPeers.size(); i++) {
            Socket rcvdSocket = serverSocket.accept();
            DataInputStream inputStream = new DataInputStream(socket.getInputStream());
            int recieved = inputStream.readInt();
            if (blockchain_size < recieved) blockchain_size = recieved;
        }
        System.out.println("the longest valid block chain is " + blockchain_size);
        return blockchain_size;
    }
}