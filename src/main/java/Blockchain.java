import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Blockchain implements Serializable {

    private List<Block> blocks;

    private List <Transaction> pendingTransaction ;

    private int difficulty;
    private int reward;


    public Blockchain() {

        blocks = new ArrayList<Block>();
        pendingTransaction = new ArrayList<Transaction>();

        List <Transaction> transactions = new ArrayList<Transaction>();
        blocks.add(this.createGenesisBlock(transactions));

        setDifficulty(3);
        setReward(500);

    }

    private Block createGenesisBlock(List<Transaction> transactions){
        return new Block(transactions, 24042018);
    }

    private Block getLatestBlock(){

        return getBlocks().get(getBlockChainSize() - 1);
    }

    public void minePendingTransaction(String miningRewardAddress){

        Block block = new Block(getPendingTransaction(),2005);

        block.setPrevHash(getLatestBlock().getHash());

        block.mineBlock(difficulty);

        this.blocks.add(block);

        pendingTransaction = new ArrayList<Transaction>();
        pendingTransaction.add(new Transaction("system",miningRewardAddress,reward));

    }

    public void createTransaction(Transaction transaction)
    {
        getPendingTransaction().add(transaction);
    }

    public int checkBalance(String address) {

        int balance = 0;
        for (int i = 0; i < getBlockChainSize(); ++i) {

            for (int j = 0; j < blocks.get(i).getTransaction().size(); ++j) {

                if(blocks.get(i).getTransaction().get(j).getTransactionFrom().equals(address)) {

                    balance -= blocks.get(i).getTransaction().get(j).getAmount();
                }

                else if(blocks.get(i).getTransaction().get(j).getTransactionTo().equals(address)) {

                    balance += blocks.get(i).getTransaction().get(j).getAmount();
                }
            }
        }
        return balance;

    }

    public int isValid(){

        Block currentBlock, prevBlock;

        for(int i = 1; i < blocks.size() -1 ; ++i){

            currentBlock = blocks.get(i);
            prevBlock = blocks.get(i-1);

            if(!currentBlock.getHash().equals(currentBlock.generateHash())){ return i; }
            if(!currentBlock.getPrevHash().equals(prevBlock.getHash())){ return i; }

        }

        return getBlockChainSize();
    }

    public void displayBlockChain(){

        for(Block block : blocks){

            System.out.println(
                    "Data : " + block.getTransaction() + "\n"+
                    "Hash : " + block.getHash() + "\n" +
                    "TimeStamp : " + block.getTimeStamp() + "\n" +
                    "PrevHash : " + block.getPrevHash() + "\n"
            );

        }
    }

    public List<Block> getBlocks() { return blocks; }

    public void setBlocks(List<Block> blocks) { this.blocks = blocks; }

    public int getBlockChainSize() { return blocks.size(); }

    public int getDifficulty() { return difficulty; }

    public void setDifficulty(int difficulty) { this.difficulty = difficulty; }

    public List<Transaction> getPendingTransaction() { return pendingTransaction; }

    public void setPendingTransaction(List<Transaction> pendingTransaction) { this.pendingTransaction = pendingTransaction; }

    public int getReward() { return reward; }

    public void setReward(int reward) { this.reward = reward; }

}