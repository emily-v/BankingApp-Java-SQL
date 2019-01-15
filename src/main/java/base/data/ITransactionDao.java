package base.data;

import base.pojos.Transaction;

import java.util.List;

public interface ITransactionDao {
    List<Transaction> getAllTransactions (int accountId);
    void newTransaction(Transaction t);
}
