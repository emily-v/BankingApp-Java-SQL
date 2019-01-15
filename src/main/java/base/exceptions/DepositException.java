package base.exceptions;

public class DepositException extends Exception {

    public DepositException() {
        System.out.println("Deposit amount exceeds maximum for online banking. Please visit a branch to complete this transaction.");
    }
}
