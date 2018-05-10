import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GenerateTransactions extends Thread{
    List<String> names;
    List<Transaction> transactions;

    GenerateTransactions(List<Transaction> transactions){

        this.transactions = transactions;
        List<String> names = new ArrayList<String>();

        names.add("Sandra");
        names.add("Nayra");
        names.add("Reham");
        names.add("Aya");
        names.add("Nada");
        names.add("Reem");
        names.add("Mona");
    }

    public Transaction getRandomTransaction(){
        Random rand = new Random();
        int sourceIndex = rand.nextInt(6);
        int destinationIndex = rand.nextInt(6);

        int value =  rand.nextInt(5000) + 500;

        String source = names.get(sourceIndex);
        String destination = names.get(destinationIndex);

        Transaction transaction = new Transaction(source, destination, value);

        return transaction;
    }

    @Override
    public void run() {
        while (true){
            this.transactions.add(getRandomTransaction());
            try {
                sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
