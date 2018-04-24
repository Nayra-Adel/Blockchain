import java.util.ArrayList;
import java.util.List;

public class Blockchain {

    private List<Block> blockChain;

    private List <Transaction> pendingTransaction ;

    private int difficulty;
    private int reward;


    public Blockchain() {

        blockChain = new ArrayList<Block>();
        pendingTransaction = new ArrayList<Transaction>();

        List <Transaction> transactions = new ArrayList<Transaction>();
        blockChain.add(this.createGenesisBlock(transactions));

        setDifficulty(3);
        setReward(500);

    }

    private Block createGenesisBlock(List<Transaction> transactions){
        return new Block(transactions, 24042018);
    }

    private Block getLatestBlock(){

        return getBlockChain().get(getBlockChainSize() - 1);
    }

    public void minePendingTransaction(String miningRewardAddress){

        Block block = new Block(getPendingTransaction(),2005);

        block.setPrevHash(getLatestBlock().getHash());

        block.mineBlock(difficulty);

        this.blockChain.add(block);

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

            for (int j = 0; j < blockChain.get(i).getTransaction().size(); ++j) {

                if(blockChain.get(i).getTransaction().get(j).getTransactionFrom().equals(address)) {

                    balance -= blockChain.get(i).getTransaction().get(j).getAmount();
                }

                else if(blockChain.get(i).getTransaction().get(j).getTransactionTo().equals(address)) {

                    balance += blockChain.get(i).getTransaction().get(j).getAmount();
                }
            }
        }
        return balance;

    }

    public Boolean isValid(){

        Block currentBlock, prevBlock;

        for(int i = 1; i < blockChain.size() -1 ; ++i){

            currentBlock = blockChain.get(i);
            prevBlock = blockChain.get(i-1);

            if(!currentBlock.getHash().equals(currentBlock.generateHash())){ return false; }
            if(!currentBlock.getPrevHash().equals(prevBlock.getHash())){ return false; }

        }

        return true;
    }

    public void displayBlockChain(){

        for(Block block : blockChain){

            System.out.println(
                    "Data : " + block.getTransaction() + "\n"+
                    "Hash : " + block.getHash() + "\n" +
                    "TimeStamp : " + block.getTimeStamp() + "\n" +
                    "PrevHash : " + block.getPrevHash() + "\n"
            );

        }
    }

    public List<Block> getBlockChain() { return blockChain; }

    public void setBlockChain(List<Block> blockChain) { this.blockChain = blockChain; }

    public int getBlockChainSize() { return blockChain.size(); }

    public int getDifficulty() { return difficulty; }

    public void setDifficulty(int difficulty) { this.difficulty = difficulty; }

    public List<Transaction> getPendingTransaction() { return pendingTransaction; }

    public void setPendingTransaction(List<Transaction> pendingTransaction) { this.pendingTransaction = pendingTransaction; }

    public int getReward() { return reward; }

    public void setReward(int reward) { this.reward = reward; }

}