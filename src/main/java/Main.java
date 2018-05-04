
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    private List<String> networkPeers = new ArrayList<>();

    public static void main(String[] args) throws Exception {


        Blockchain blockchain = new Blockchain();

        Thread thread = new MulticastReceiver();
        thread.start();

       // Thread my_peer= new MyLocalPeer();
       // my_peer.start();



       /* blockchain.createTransaction(new Transaction("Nayra","Reham",500));
        blockchain.createTransaction(new Transaction("Sandra","Nayra",1000));

        System.out.println("balance "+ blockchain.checkBalance("Reham"));

        blockchain.minePendingTransaction("Reham");

        System.out.println("Rehame's balance (before) : "+ blockchain.checkBalance("Reham"));

        blockchain.minePendingTransaction("Reham");
        System.out.println("Rehame's balance (after) : "+ blockchain.checkBalance("Reham"));
        System.out.println("Nayra's balance "+ blockchain.checkBalance("Nayra"));
        System.out.println("Sandra's balance "+ blockchain.checkBalance("Sandra"));*/

    }
}