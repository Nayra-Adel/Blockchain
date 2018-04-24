public class Transaction {

    private String transactionFrom;
    private String transactionTo;
    private int amount;

    public Transaction(String transactionFrom, String transactionTo, int amount) {

        this.transactionFrom = transactionFrom;
        this.transactionTo = transactionTo;
        this.amount = amount;

    }

    public String getTransactionFrom() { return transactionFrom; }

    public void setTransactionFrom(String transactionFrom) { this.transactionFrom = transactionFrom; }

    public String getTransactionTo() { return transactionTo; }

    public void setTransactionTo(String transactionTo) { this.transactionTo = transactionTo; }

    public int getAmount() { return amount; }

    public void setAmount(int amount) { this.amount = amount; }

}
