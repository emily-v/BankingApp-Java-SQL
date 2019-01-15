package base.services;

import base.Main;
import base.data.ITransactionDao;
import base.pojos.Transaction;

import java.util.List;

public class TransactionService {

    public ITransactionDao transactionDao;

    public TransactionService() {}

    public TransactionService(ITransactionDao transactionDao) { this.transactionDao = transactionDao; }

    public List<Transaction> showTransactions() {
        System.out.println("Enter Account ID of the account transactions you'd like to view");
        int accountId = Main.scan.nextInt();
        List<Transaction> transactions = this.transactionDao.getAllTransactions(accountId);
        if (transactions == null || transactions.size() == 0) {
            System.out.println("There are no transactions to show");
        } else {
            for (final Transaction transaction : transactions) {
                System.out.println(" Account ID: " + transaction.getAccountId() +
                        " Transaction Amount: $" + transaction.getAmount());
            }
        }
        return transactions;
    }
}
