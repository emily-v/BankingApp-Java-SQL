package base.services;

import base.Main;
import base.data.IAccountDao;
import base.data.ITransactionDao;
import base.data.IUserDao;
import base.exceptions.BalanceException;
import base.exceptions.DepositException;
import base.pojos.Account;
import base.pojos.Transaction;

import java.util.List;

public class AccountService {

    private IAccountDao accountDao;
    private ITransactionDao transactionDao;
    private IUserDao userDao;

    public AccountService() { }

    public AccountService(IAccountDao accountDao) {
        this.accountDao = accountDao;
    }

    public AccountService(IAccountDao accountDao, ITransactionDao transactionDao, IUserDao userDao) {
        this.accountDao = accountDao;
        this.transactionDao = transactionDao;
        this.userDao = userDao;
    }

    public void showAccounts() {

        int holderId = UserService.currentUser.getUserId();
        List<Account> accounts = this.accountDao.getAllAccounts(holderId);
        if (accounts == null || accounts.size() == 0) {
            System.out.println("There are no accounts to show");
        } else {
            for (final Account account : accounts) {
                System.out.println("Account ID: " + account.getAccountId() + "    Account Holder ID: " + account.getAccountHolderId() +
                        "  Account Type: " + account.getAccountType() + "    Balance: $" + account.getBalance());
            }
        }
    }

    public void makeWithdrawal() throws BalanceException {
        System.out.println("How much would you like to withdraw?");
        double amount = Main.scan.nextDouble();
        System.out.println("Which account would you like to withdraw from?");
        showAccounts();
        System.out.println("Enter account ID of the account you would like to withdraw from.");
        int accountId = Main.scan.nextInt();
        try {
            Account a = this.accountDao.getById(accountId);
            int holderId = UserService.currentUser.getUserId();
            if (a.getAccountHolderId() != holderId) {
                System.out.println("Invalid account for current user. Please try again.");
            } else {
                double attemptedBalance = a.getBalance() - amount;
                if (attemptedBalance < 0) {
                    throw new BalanceException();
                } else {
                    a.setBalance(attemptedBalance);
                    this.accountDao.updateBalance(a);
                    Transaction t = new Transaction(accountId, -amount);
                    this.transactionDao.newTransaction(t);
                    System.out.println("\nWithdrawal successful!");
                }
                System.out.println("Account ID: " + a.getAccountId() + "    Account Type: " + a.getAccountType()
                        + "    Balance: $" + a.getBalance());
            }
        } catch (NullPointerException ne) {
            System.out.println("\nAccount not found. Please try again.");
        }
    }

    public void makeDeposit() throws DepositException {
        System.out.println("How much would you like to deposit?");
        double amount = Main.scan.nextDouble();
        System.out.println("Which account would you like to deposit to?");
        showAccounts();
        System.out.println("Enter account ID of the account you would like to deposit to.");
        int accountId = Main.scan.nextInt();
        try {
            if (amount > 10000) {
                throw new DepositException();
            } else {
                Account a = this.accountDao.getById(accountId);
                int holderId = UserService.currentUser.getUserId();
                if (a.getAccountHolderId() != holderId) {
                    System.out.println("Invalid account for current user. Please try again.");
                } else {
                    a.setBalance(a.getBalance() + amount);
                    this.accountDao.updateBalance(a);
                    Transaction t = new Transaction(accountId, amount);
                    this.transactionDao.newTransaction(t);
                    System.out.println("\nDeposit successful!");
                    System.out.println("Account ID: " + a.getAccountId() + "    Account Type: " + a.getAccountType() + "    Balance: $" + a.getBalance());
                }
            }
        } catch (NullPointerException ne) {
            System.out.println("\nAccount not found. Please try again.");
        }
    }

    public void transfer() throws BalanceException {
        System.out.println("How much would you like to transfer?");
        double amount = Main.scan.nextDouble();
        showAccounts();
        System.out.println("Which account would you like to transfer from?");
        System.out.println("Enter account ID of the account you would like to transfer from.");
        int from = Main.scan.nextInt();
        System.out.println("Which account would you like to transfer to?");
        System.out.println("Enter account ID of the account you would like to transfer to.");
        int to = Main.scan.nextInt();
        if (from == to) {
            System.out.println("\nYou must choose 2 different accounts. Please try again.");
        } else {
            try {
                Account w = this.accountDao.getById(from);
                Account d = this.accountDao.getById(to);
                int holderId = UserService.currentUser.getUserId();
                if (w.getAccountHolderId() != holderId || d.getAccountHolderId() != holderId) {
                    System.out.println("One or more account entries are invalid. Please try again.");
                } else {
                    double attemptedBalance = w.getBalance() - amount;
                    if (attemptedBalance < 0) {
                        throw new BalanceException();
                    } else {
                        w.setBalance(attemptedBalance);
                        this.accountDao.updateBalance(w);
                        Transaction tw = new Transaction(from, -amount);
                        this.transactionDao.newTransaction(tw);
                        d.setBalance(d.getBalance() + amount);
                        this.accountDao.updateBalance(d);
                        Transaction td = new Transaction(to, amount);
                        this.transactionDao.newTransaction(td);
                        System.out.println("\nTransfer successful!");
                        System.out.println("Account ID: " + w.getAccountId() + "    Account Type: " + w.getAccountType() + "    Balance: $" + w.getBalance());
                        System.out.println("Account ID: " + d.getAccountId() + "    Account Type: " + d.getAccountType() + "    Balance: $" + d.getBalance());
                    }
                }
            } catch (NullPointerException ne) {
                System.out.println("\nOne or more account(s) not found. Please try again.");
            }
        }
    }

    public void requestCredit() {
        double random = (Math.random() * ((8.5 - 3)) + 3) * 100;
        int score = (int) Math.floor(random);
        System.out.println("\nYour credit score is " + score + ".");
        if (score < 580) {
            System.out.println("Your score is too low. Credit request denied.");
        } else if (score > 669) {
            System.out.println("Your credit request is approved.");
        } else { // between 581-669
            System.out.println("Your request is being reviewed by a banker.");
        }
    }

}
