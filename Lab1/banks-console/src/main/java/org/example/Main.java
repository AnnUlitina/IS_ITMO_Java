package org.example;

import ru.itmo.console.BankConsole;
import picocli.CommandLine;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        System.out.println("Welcome to the Bank!");

        BankConsole bankConsole = new BankConsole();

        while (true) {
            System.out.println("\nSelect an option:");
            System.out.println("\nEnter a command or type 'q' to quit");
            System.out.println("Enter --help to see commands");
            System.out.print("Your choice: ");

            Scanner in = new Scanner(System.in);
            String input = in.nextLine();
            if (input.equalsIgnoreCase("q")) {
                System.out.println("Exiting the Bank. Goodbye!");
                break;
            }
            String[] arguments = input.split(" ");

            CommandLine commandLine = new CommandLine(bankConsole);
            commandLine.execute(arguments);
        }
    }
}