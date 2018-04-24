import java.util.ArrayList;
import java.util.List;

public class Blockchain {

    private List<Block> blockChain = new ArrayList<Block>();
    private int difficulty;

    public Blockchain() {

        blockChain.add(this.createGenesisBlock());
        setDifficulty(3);
    }

    private Block createGenesisBlock(){
        return new Block("Genesis Block", 20180424);
    }

    private Block getLatestBlock(){

        return getBlockChain().get(getBlockChainSize() - 1);
    }

    public void addBlock(Block newBlock){

        newBlock.setPrevHash(this.getLatestBlock().getHash());
        newBlock.mineBlock(getDifficulty());
        this.blockChain.add(newBlock);

    }

    public Boolean isValid(){

        Block currentBlock, prevBlock;

        for(int i=1; i < blockChain.size() -1 ; ++i){

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
                    "Data : " + block.getData() + "\n"+
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

}