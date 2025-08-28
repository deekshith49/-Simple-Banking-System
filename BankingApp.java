import java.io.*;
import java.util.Scanner;

class Account {
    String name;
    String accountNumber;
    double balance;

    public Account(String name, String accountNumber, double initialDeposit) {
        this.name = name;
        this.accountNumber = accountNumber;
        this.balance = initialDeposit;
    }

    void displayAccount() {
        System.out.println("Name: " + name);
        System.out.println("Account Number: " + accountNumber);
        System.out.println("Balance: $" + balance);
    }

    void deposit(double amount) {
        balance += amount;
        System.out.println("Successfully deposited $" + amount);
    }

    void withdraw(double amount) {
        if (amount > balance) {
            System.out.println("Insufficient funds!");
        } else {
            balance -= amount;
            System.out.println("Successfully withdrew $" + amount);
        }
    }

    void saveToFile() {
        try (PrintWriter writer = new PrintWriter("account.txt")) {
            writer.println(name);
            writer.println(accountNumber);
            writer.println(balance);
        } catch (IOException e) {
            System.out.println("Error saving account data.");
        }
    }

    static Account loadFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader("account.txt"))) {
            String name = reader.readLine();
            String accountNumber = reader.readLine();
            double balance = Double.parseDouble(reader.readLine());
            return new Account(name, accountNumber, balance);
        } catch (IOException | NumberFormatException e) {
            return null;
        }
    }
}

public class BankingApp {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Account account = Account.loadFromFile();

        if (account != null) {
            System.out.println("Account loaded from file successfully.");
        }

        while (true) {
            System.out.println("\n====== Banking System ======");
            System.out.println("1. Create Account");
            System.out.println("2. View Account");
            System.out.println("3. Deposit");
            System.out.println("4. Withdraw");
            System.out.println("5. Exit");
            System.out.print("Choose an option: ");
            
            int option = scanner.nextInt();
            scanner.nextLine();  // consume newline

            switch (option) {
                case 1:
                    if (account != null) {
                        System.out.println("Account already exists.");
                        break;
                    }
                    System.out.print("Enter name: ");
                    String name = scanner.nextLine();
                    System.out.print("Enter account number: ");
                    String accNo = scanner.nextLine();
                    System.out.print("Enter initial deposit: ");
                    double deposit = scanner.nextDouble();
                    account = new Account(name, accNo, deposit);
                    account.saveToFile();
                    System.out.println("Account created and saved successfully!");
                    break;

                case 2:
                    if (account == null) {
                        System.out.println("No account found. Please create one first.");
                    } else {
                        account.displayAccount();
                    }
                    break;

                case 3:
                    if (account == null) {
                        System.out.println("No account found.");
                    } else {
                        System.out.print("Enter amount to deposit: ");
                        double amount = scanner.nextDouble();
                        account.deposit(amount);
                        account.saveToFile();
                    }
                    break;

                case 4:
                    if (account == null) {
                        System.out.println("No account found.");
                    } else {
                        System.out.print("Enter amount to withdraw: ");
                        double amount = scanner.nextDouble();
                        account.withdraw(amount);
                        account.saveToFile();
                    }
                    break;

                case 5:
                    System.out.println("Thank you for using our banking system!");
                    scanner.close();
                    System.exit(0);

                default:
                    System.out.println("Invalid option. Try again.");
            }
        }
    }
}
