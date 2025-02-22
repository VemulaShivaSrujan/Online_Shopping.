import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;

class Account {
    private String accountNumber;
    private double balance;

    public Account(String accountNumber, double balance) {
        this.accountNumber = accountNumber;
        this.balance = balance;
    }

    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            System.out.println("Deposited $" + amount + ". New Balance: $" + balance);
        } else {
            System.out.println("Deposit amount must be positive.");
        }
    }

    public void withdraw(double amount) {
        if (amount > 0 && amount <= balance) {
            balance -= amount;
            System.out.println("Withdrawn $" + amount + ". New Balance: $" + balance);
        } else {
            System.out.println("Invalid withdrawal amount or insufficient funds.");
        }
    }

    public double getBalance() {
        return balance;
    }
}

class SavingsAccount extends Account {
    private double interestRate;

    public SavingsAccount(String accountNumber, double balance, double interestRate) {
        super(accountNumber, balance);
        this.interestRate = interestRate;
    }

    public void addInterest() {
        double interest = getBalance() * interestRate;
        deposit(interest);
        System.out.println("Interest added: $" + interest + ". New Balance: $" + getBalance());
    }
}

class CheckingAccount extends Account {
    private double overdraftLimit;

    public CheckingAccount(String accountNumber, double balance, double overdraftLimit) {
        super(accountNumber, balance);
        this.overdraftLimit = overdraftLimit;
    }

    @Override
    public void withdraw(double amount) {
        if (amount > 0 && amount <= (getBalance() + overdraftLimit)) {
            deposit(-amount);
            System.out.println("Withdrawn $" + amount + ". New Balance: $" + getBalance());
        } else {
            System.out.println("Withdrawal exceeds overdraft limit.");
        }
    }
}

class Customer {
    private String name;
    private int customerId;
    private List<Account> accounts;

    public Customer(String name, int customerId) {
        this.name = name;
        this.customerId = customerId;
        this.accounts = new ArrayList<>();
    }

    public void addAccount(Account account) {
        accounts.add(account);
    }

    public List<Account> getAccounts() {
        return accounts;
    }
}

public class OnlineShoppingSystem {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter customer name: ");
        String name = scanner.nextLine();
        System.out.print("Enter customer ID: ");
        int customerId = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        Customer customer1 = new Customer(name, customerId);

        System.out.print("Enter savings account number: ");
        String savingsAccNum = scanner.nextLine();
        System.out.print("Enter initial savings balance: ");
        double savingsBalance = scanner.nextDouble();
        System.out.print("Enter interest rate for savings: ");
        double interestRate = scanner.nextDouble();

        SavingsAccount savings = new SavingsAccount(savingsAccNum, savingsBalance, interestRate);
        customer1.addAccount(savings);

        System.out.print("Enter checking account number: ");
        scanner.nextLine(); // Consume newline
        String checkingAccNum = scanner.nextLine();
        System.out.print("Enter initial checking balance: ");
        double checkingBalance = scanner.nextDouble();
        System.out.print("Enter overdraft limit for checking: ");
        double overdraftLimit = scanner.nextDouble();

        CheckingAccount checking = new CheckingAccount(checkingAccNum, checkingBalance, overdraftLimit);
        customer1.addAccount(checking);

        System.out.print("Enter deposit amount for savings: ");
        double depositAmount = scanner.nextDouble();
        savings.deposit(depositAmount);
        savings.addInterest();

        System.out.print("Enter withdrawal amount for checking: ");
        double withdrawAmount = scanner.nextDouble();
        checking.withdraw(withdrawAmount);

        for (Account account : customer1.getAccounts()) {
            System.out.println("Account Balance: $" + account.getBalance());
        }

        scanner.close();
    }
}