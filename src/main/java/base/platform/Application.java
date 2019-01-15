package base.platform;

import base.Main;
import base.data.*;
import base.exceptions.BalanceException;
import base.exceptions.DepositException;
import base.services.AccountService;
import base.services.TransactionService;
import base.services.UserService;
import base.util.ConnectionUtil;

import java.sql.SQLException;
import java.util.InputMismatchException;

public class Application {
    private UserService userService;
    private AccountService accountService;
    private TransactionService transactionService;

    public void run (String... args) {
        try {
            init();
            userService.login();

            boolean quit = false;
            do {
                System.out.println("\nWhat do you want to do? *****");
                System.out.println("1. Check account balances    5. View past transactions");
                System.out.println("2. Make a withdrawal         6. Request a line of credit");
                System.out.println("3. Make a deposit            7. Logout");
                System.out.println("4. Transfer funds            8. Quit the program");
                System.out.println("***** Make your selection: ");

                try {
                    int choice = Main.scan.nextInt();
                    switch (choice) {
                        case 1: // check balances
                            accountService.showAccounts();
                            break;
                        case 2: // withdrawal
                            try {
                                accountService.makeWithdrawal();
                            } catch (BalanceException e) {}
                            break;
                        case 3: // deposit
                            try {
                                accountService.makeDeposit();
                            } catch (DepositException e) {}
                            break;
                        case 4: // transfer
                            try {
                                accountService.transfer();
                            } catch (BalanceException e) {}
                            break;
                        case 5: // past transactions
                            transactionService.showTransactions();
                            break;
                        case 6: // request credit
                            accountService.requestCredit();
                            break;
                        case 7: // logout
                            userService.logout();
                            break;
                        case 8: // quit
                            quit = true;
                            System.out.println("Thank you for banking with us. Have a nice day!");
                            break;
                        default:
                            System.out.println("Invalid choice. Please choose again.");
                            break;
                    }
                } catch (InputMismatchException e) {
                    System.out.println("Invalid entry type. Please try again.");
                    Main.scan.next();
                }
            } while (quit == false) ;
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void init() throws SQLException {
        ConnectionUtil connectionUtil = new ConnectionUtil("jdbc:postgresql://mydb-inclass.cmpzxiqqdpxe.us-east-2.rds.amazonaws.com:5432/dbhello",
                "emily_vong", "mydb2018", new org.postgresql.Driver());

        IUserDao userDao = new UserDao(connectionUtil);
        userService = new UserService(userDao);

        ITransactionDao transactionDao = new TransactionDao(connectionUtil);
        transactionService = new TransactionService(transactionDao);

        IAccountDao accountDao = new AccountDao(connectionUtil);
        accountService = new AccountService(accountDao, transactionDao, userDao);

        System.out.println("Welcome to the Banking App! Press a key to login and begin");
        Main.scan.next();
    }

}
