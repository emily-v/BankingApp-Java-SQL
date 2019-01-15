package base.exceptions;

public class BalanceException extends Exception {

    public BalanceException() {
        System.out.println("Insufficient funds to withdraw from chosen account.");
    }
}
