public class Main {

    public static void main(String[] args) {

        Blockchain blockchain = new Blockchain();

        blockchain.addBlock(new Block("Block 1", 2017));
        blockchain.addBlock(new Block("Block 2", 2018));
        blockchain.addBlock(new Block("Block 3", 2019));

        blockchain.displayBlockChain();

        System.out.println("BlockChain is valid? " + blockchain.isValid());

        blockchain.getBlockChain().get(1).setData("Nayra");

        System.out.println("BlockChain is valid? " + blockchain.isValid());

    }
}