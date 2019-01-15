package base;

import base.platform.Application;

import java.util.Scanner;

public class Main {

    public static Scanner scan = new Scanner(System.in);

    public static void main (String [] args) {

        new Application().run(args);

    }
}



