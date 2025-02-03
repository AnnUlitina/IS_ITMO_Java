package ru.itmo.console;

import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import ru.itmo.accounts.*;
import ru.itmo.banks.Bank;
import ru.itmo.clients.ClientBuilderImpl;
import ru.itmo.clients.ClientImpl;
import ru.itmo.clients.Director;

import java.util.HashMap;
import java.util.Map;

/**
 * This class represents a command-line interface (CLI) for interacting with the bank.
 */
@Command(name = "bank-console", mixinStandardHelpOptions = true, version = "1.0",
        description = "CLI for interacting with the bank")

public class BankConsole implements Runnable{
    private Bank bank;
    private Map<String, ClientImpl> clients = new HashMap<>();
    private Map<String, Account> accounts = new HashMap<>();
    private Map<String, Account> accountToAccountMap = new HashMap<>();

    /**
     * Runs the Bank CLI.
     */
    @Override
    public void run() {
        System.out.println("Welcome to the Bank CLI!");
    }

    /**
     * Registers a new client.
     *
     * @param name     The name of the client.
     * @param surname  The surname of the client.
     * @param address  The address of the client.
     * @param passport The passport number of the client.
     */
    @Command(name = "register-client", description = "Register a new client")
    void registerClient(
            @Option(names = {"-n", "--name"}, required = true, description = "Register client name") String name,
            @Option(names = {"-s", "--surname"}, required = true, description = "Register client surname") String surname,
            @Option(names = {"-a", "--address"}, required = false, description = "Register client address") String address,
            @Option(names = {"-p", "--passport"}, required = false, description = "Register client passport") String passport) {
        ClientBuilderImpl clientBuilderImpl = new ClientBuilderImpl(name, surname, null)
                .setAddress(address)
                .setPassportNumber(passport);
        Director director = new Director(clientBuilderImpl);
        ClientImpl clientImpl = director.construct();
        clientImpl.setFirstName(name);
        clientImpl.setSurname(surname);
        clientImpl.setAddress(address);
        clientImpl.setPassportNumber(passport);
        clients.put(passport, clientImpl);
        System.out.printf(
                "Register client with name %s, surname %s, address %s and passport number %s%n",
                name,
                surname,
                address,
                passport);
    }

    /**
     * Creates a new bank account.
     *
     * @param number       The account number.
     * @param type         The type of account (debit, deposit, credit).
     * @param balance      The initial balance of the account.
     * @param interestRate The interest rate of the account.
     */
    @Command(name = "create-account", description = "Create a new bank account")
    void createAccount(
            @Option(names = {"-an", "--accountNumber"}, required = true, description = "Account number") String number,
            @Option(names = {"-t", "--type"}, description = "Type of account (debit, deposit, credit)") String type,
            @Option(names = {"-b", "--balance"}, required = true, description = "Initial balance") double balance,
            @Option(names = {"-r", "--interestRate"}, description = "Interest rate") double interestRate,
            @Option(names = {"-d", "--depositTerm"}, description = "Deposit term") int depositTerm) {

        Account account = null;
        if ("debit".equals(type)) {
            account = new DebitAccountImpl(balance, interestRate);
        } else if ("credit".equals(type)) {
            account = new CreditAccountImpl(balance);
        } else if ("deposit".equals(type)) {
            account = new DepositAccountImpl(balance, interestRate, depositTerm);
        }

        if (account != null) {
            accounts.put(number, account);
            accountToAccountMap.put(number, account);
            System.out.printf(
                    "Creating account with number %s, account type %s and initial balance %.2f%n",
                    number,
                    type,
                    balance);
        } else {
            System.out.println("Error: Invalid account type.");
        }
    }

    /**
     * Deposits an amount into an account.
     *
     * @param number The account number.
     * @param amount The amount to deposit.
     */
    @Command(name = "deposit", description = "Deposit amount into an account")
    void deposit(
            @Option(names = {"-an", "--accountNumber"}, required = true, description = "Account number") String number,
            @Option(names = {"-a", "--amount"}, required = true, description = "Amount to deposit") double amount) {
        Account account = accounts.get(number);
        if (account != null) {
            account.deposit(amount);
            System.out.printf("Depositing %.2f into account %s%n", amount, number);
        } else {
            System.out.println("Error: Account not found.");
        }
    }

    /**
     * Withdraws an amount from an account.
     *
     * @param number The account number.
     * @param amount The amount to withdraw.
     */
    @Command(name = "withdraw", description = "Withdraw amount from an account")
    void withdraw(
            @Option(names = {"-n", "--number"}, required = true, description = "Account number") String number,
            @Option(names = {"-a", "--amount"}, required = true, description = "Amount to withdraw") double amount) throws Exception {
        Account account = accounts.get(number);
        if (account != null) {
            account.withdraw(amount);
            System.out.printf("Withdrawing %.2f from account %s%n", amount, number);
        } else {
            System.out.println("Error: Account not found.");
        }
    }

    /**
     * Transfers an amount from one account to another.
     *
     * @param fromAccount The account number to transfer from.
     * @param toAccount   The account number to transfer to.
     * @param amount      The amount to transfer.
     */
    @Command(name = "transfer", description = "Transfer amount from one account to another")
    void transfer(
            @Option(names = {"-f", "--from"}, required = true, description = "From account number") String fromAccount,
            @Option(names = {"-t", "--to"}, required = true, description = "To account number") String toAccount,
            @Option(names = {"-a", "--amount"}, required = true, description = "Amount to transfer") double amount) throws Exception {
        Account from = accountToAccountMap.get(fromAccount);
        Account to = accountToAccountMap.get(toAccount);
        if (from != null && to != null) {
            from.transfer(to, amount);
            System.out.printf("Transferring %.2f from account %s to account %s%n", amount, fromAccount, toAccount);
        } else {
            System.out.println("Error: Account was not found.");
        }
    }

    /**
     * Gets the balance of an account.
     *
     * @param number The account number to get the balance for.
     */
    @Command(name = "balance", description = "Get account balance")
    void getBalance(
            @Option(names = {"-n", "--number"}, required = true, description = "Account number") String number) {
        Account account = accounts.get(number);
        if (account != null) {
            double balance = account.getBalance();
            System.out.printf("Balance: %.2f for account %s%n", balance, number);
        } else {
            System.out.println("Error: Account not found.");
        }
    }
}
