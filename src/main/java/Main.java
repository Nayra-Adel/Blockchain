public class Main {

    public static void main(String[] args) {

        Blockchain blockchain = new Blockchain();

        blockchain.addBlock(new Block("Block 1", 2017));
        blockchain.addBlock(new Block("Block 2", 2018));

        blockchain.displayBlockChain();

    }
}
