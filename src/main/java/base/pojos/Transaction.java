package base.pojos;

public class Transaction {
    private int transId;
    private int accountId;
    private double amount;

    public Transaction() {}

    public Transaction(int accountId, double amount) {
//        this.transId = transId;
        this.accountId = accountId;
        this.amount = amount;
    }

    public int getTransId() {
        return transId;
    }

    public void setTransId(int transId) {
        this.transId = transId;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }


}




