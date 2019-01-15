package base.services;

import base.Main;
import base.data.IUserDao;
import base.pojos.User;

public class UserService {

    private IUserDao userDao;
    public boolean loggedIn = false;
    public static User currentUser = null;

    public UserService() { }

    public UserService(IUserDao userDao) {
        this.userDao = userDao;
    }

    private boolean authenticate(String username, String password) {
        User attemptedUser = this.userDao.getUserByUsername(username);
        if(attemptedUser == null) {
            return false;
        } else {
            if(attemptedUser.getPassword().equals(password)) {
                loggedIn = true;
                return true;
            }
        }
        return false;
    }

    public void login() {
        loggedIn = false;
        while (!loggedIn) {
            System.out.println("Enter username: ");
            String un = Main.scan.next();
            System.out.println("Enter password: ");
            String pw = Main.scan.next();
            if (authenticate(un, pw)) {
                currentUser = this.userDao.getUserByUsername(un);
                System.out.println("\nLogin successful!");
                System.out.println("Welcome " + un + "!");
                loggedIn = true;
            } else {
                System.out.println("Email or password incorrect. Please try again");
            }
        }
    }

    public void logout() {
        boolean logIn = false;
        currentUser = null;
        System.out.println("You have logged out.");
        System.out.println("Enter 1 to login again.");
        String choice = Main.scan.next();
        while (logIn == false) {
            if (choice.equals("1")) {
                login();
                logIn = true;
            } else {
                System.out.println("Please enter 1 to login and view available actions.");
                choice = Main.scan.next();
                logIn = false;
            }
        }

    }
}
