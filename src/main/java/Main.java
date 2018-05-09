
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    private List<String> networkPeers = new ArrayList<String>();

    public static void main(String[] args) throws Exception {


        MyLocalPeer myLocalPeer = new MyLocalPeer();

        Thread generateTransactions = new GenerateTransactions(myLocalPeer.getBlockchain().getPendingTransaction());
        generateTransactions.start();

        Thread multicast = new Multicast(myLocalPeer);
        multicast.start();

        Thread my_peer= new MyLocalPeer();
        my_peer.start();


        Scanner input = new Scanner(System.in);
        label:
        while (true) {
            System.out.println("please choose from the following menu :) enter the number  ");
            System.out.println("1-Query the longest valid blockchain on the network.");
            System.out.println("2-Request another Peer’s copy of the blockchain.");
            System.out.println("3-Broadcast transactions to miners to add it on new Blocks.");
            System.out.println("4-Mine block.");
            System.out.println("5-exit");
            int choice = input.nextInt();
            switch (choice) {
                case 1:
                    myLocalPeer.sendLongestValidBCSizeRequest();
                    System.out.println(myLocalPeer.getLongestValidBlockChain());
                    break;
                case 2:
                    myLocalPeer.sendBlockChainRequest("");
                    break;
                case 3:
                    myLocalPeer.sendTransactions();
                    break;
                case 4:
                    myLocalPeer.getBlockchain().minePendingTransaction(myLocalPeer.getName());
                    myLocalPeer.sendBlock();
                    break;
                default:
                    break label;
            }
        }
    }
}
