package base.pojos;

import java.math.BigDecimal;

public class Account {
    private int accountId;
    private int accountHolderId;
    private String accountType;
    private double balance;

    public Account() {}

    public Account(int accountId, int accountHolderId, String accountType, double balance) {
        this.accountId = accountId;
        this.accountHolderId = accountHolderId;
        this.accountType = accountType;
        this.balance = balance;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public int getAccountHolderId() {
        return accountHolderId;
    }

    public void setAccountHolderId(int accountHolderId) {
        this.accountHolderId = accountHolderId;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    @Override
    public String toString() {
        return "Account{" +
                "accountId=" + accountId +
                ", accountHolderId=" + accountHolderId +
                ", accountType='" + accountType + '\'' +
                ", balance=" + balance +
                '}';
    }
}
