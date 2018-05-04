import org.apache.commons.lang3.SerializationUtils;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static org.bouncycastle.crypto.tls.CipherType.block;

public class newThread extends Thread {
    @Override
    public void run() {
        Scanner input = new Scanner(System.in);
        Blockchain blockchain = new Blockchain();
        MyLocalPeer myLocalPeer = new MyLocalPeer();
        label:
        while (true) {
            System.out.println("please choose from the following menu :) enter the number  ");
            System.out.println("1-Query the longest valid blockchain on the network.");
            System.out.println("2-Request another Peer’s copy of the blockchain.");
            System.out.println("3-Broadcast transactions to miners to add it on new Blocks.");
            System.out.println("4-exit");
            int choice = input.nextInt();
            switch (choice) {
                case 1:
                    int valid_size= 0;
                   /* try {
                        myLocalPeer.sendMessage("Send the valid block chain size ");
                        valid_size= myLocalPeer.recieve_valid_blockchain_size();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                     int valid = blockchain.isValid();
                    if(valid_size>valid){valid=valid_size;}
                    System.out.println("the longest valid block chain is "+valid);*/
                    try {
                        myLocalPeer.sendMessage("hi rehaaaam");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    // hna function el query ely hn3mlha isa
                    break;
                case 2:

                    break;
                case 3:
                    //
                    break;
                default:
                    break label;
            }
        }
    }
    /*private List<String> networkPeers = new ArrayList<>();

    public void Query() throws Exception {
        for(int i = 0 ; i < networkPeers.size() ; i++){

            Socket socket = new Socket(networkPeers.get(i), 4445);
           OutputStream outputStream = socket.getOutputStream();

            byte[] buffer = SerializationUtils.serialize(block);
            outputStream.write(buffer);

            Socket rcvdSocket = serverSocket.accept();
            DataInputStream inputStream =new DataInputStream(socket.getInputStream());
            int recieved = inputStream.readInt();
        }
    }*/


}
