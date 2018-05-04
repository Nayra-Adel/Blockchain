import hash.HashText;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Block implements Serializable{

    private String hash;
    private String prevHash;

    private List <Transaction> transaction = new ArrayList<>();

    private long timeStamp;

    private int nonce = 0;

    private HashText hashText;


    public Block(List<Transaction> transaction, long timeStamp) {

        setTransaction(transaction);
        setTimeStamp(timeStamp);

        setHash(generateHash());
        setPrevHash("");

    }

    public String generateHash(){

        String hashData = getPrevHash() + getTimeStamp() + getTransaction() + getNonce();

        try { return hashText.sha256(hashData); }
        catch (Exception e) {}

        return "null";
    }

    public void mineBlock(int difficulty){

        String mine = "";

        for(int i = 0; i < difficulty; ++i) mine += "0";

        while (!this.getHash().substring(0, difficulty).equals(mine)) {

            nonce++;
            setHash(this.generateHash());
        }
    }

    public String getHash() { return hash; }

    public void setHash(String hash) { this.hash = hash; }

    public String getPrevHash() { return prevHash; }

    public void setPrevHash(String prevHash) { this.prevHash = prevHash; }

    public List<Transaction> getTransaction() { return transaction; }

    public void setTransaction(List<Transaction> transaction) { this.transaction = transaction; }

    public long getTimeStamp() { return timeStamp; }

    public void setTimeStamp(long timeStamp) { this.timeStamp = timeStamp; }

    public int getNonce() { return nonce; }

    public void setNonce(int nonce) { this.nonce = nonce; }

}