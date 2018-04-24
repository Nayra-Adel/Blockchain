import java.util.ArrayList;
import java.util.List;

public class Blockchain {

    private List<Block> blockChain = new ArrayList<Block>();

    public Blockchain() {

        blockChain.add(this.createGenesisBlock());
    }

    public List<Block> getBlockChain() { return blockChain; }

    public void setBlockChain(List<Block> blockChain) { this.blockChain = blockChain; }

    public int getBlockChainSize() { return blockChain.size(); }

    private Block createGenesisBlock(){
        return new Block("Genesis Block", 24);
    }

    private Block getLatestBlock(){

        return getBlockChain().get(getBlockChainSize() - 1);
    }

    public void addBlock(Block newBlock){

        newBlock.setPrevHash(this.getLatestBlock().getHash());
        newBlock.setHash(newBlock.generateHash());
        this.blockChain.add(newBlock);

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
}