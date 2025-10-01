import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class BankApp {
    private static final int minBalanceToOpen = 1000;
    public static final int minBalanceRemainAfterWithdraw = 1000;
    private final List<Account> accounts;

    public BankApp() {
        accounts = new ArrayList<>();
    }

    public void createAccount() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Select account type:\n1. Savings Account\n2. Current Account");
        int choice = scanner.nextInt();
        scanner.nextLine();  // Consume newline

        System.out.print("Enter full name: ");
        String name = scanner.nextLine().trim();

        if (!name.matches("[a-zA-Z '\\-]+")) {
            System.out.println("Name can only contain letters, spaces, apostrophes ('), or hyphens (-).");
            return;
        }

        if (name.isEmpty()) {
            System.out.println("Name cannot be empty.");
            return;
        }

        if (nameExists(name)) {
            System.out.println("An account with this name already exists. Please use a different name.");
            return;
        }

        // Auto-generate a unique numeric account number
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        int randomSuffix = (int) (Math.random() * 90 + 10); // Random 2-digit number
        long generatedAccountNumber = Long.parseLong(timestamp + randomSuffix);

        if (findAccount(generatedAccountNumber) != null) {
            System.out.println("Error: Duplicate account number generated. Please try again.");
            return;
        }

        String creationDate = LocalDate.now().toString();

        System.out.print("Enter initial deposit amount: ");
        int balance = scanner.nextInt();
        scanner.nextLine();  // Consume newline

        if (balance < BankApp.minBalanceToOpen) {
            System.out.println("Initial deposit must be at least " + minBalanceToOpen);
            return;
        }

        switch (choice) {
            case 1:
                accounts.add(new SavingsAccount(name, generatedAccountNumber, creationDate, balance));
                System.out.println("🎉 Savings account created successfully for " + name + "!");
                System.out.println("📄 Your account number is: " + generatedAccountNumber);
                break;
            case 2:
                accounts.add(new CurrentAccount(name, generatedAccountNumber, creationDate, balance));
                System.out.println("🎉 Current account created successfully for " + name + "!");
                System.out.println("📄 Your account number is: " + generatedAccountNumber);
                break;
            default:
                System.out.println("Invalid account type!");
        }
    }

    public void displayAllAccounts() {
        if (accounts.isEmpty()) {
            System.out.println("No accounts available.");
        } else {
            for (Account account : accounts) {
                account.displayBasicInfo();
            }
        }
    }

    public void deleteAccount() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter account number to delete: ");
        String input = scanner.nextLine().trim();

        if (!input.matches("\\d+")) {
            System.out.println("Invalid account number. Only digits are allowed.");
            return;
        }

        long number = Long.parseLong(input);
        Account account = findAccount(number);

        if (account == null) {
            System.out.println("No account found with number: " + number);
            return;
        }

        System.out.print("Are you sure you want to delete the account for " + account.getName() + "? (yes/no): ");
        String confirmation = scanner.nextLine().trim().toLowerCase();

        if (confirmation.equals("yes")) {
            accounts.remove(account);
            System.out.println("✅ Account for " + account.getName() + " deleted successfully.");
        } else {
            System.out.println("❎ Deletion cancelled.");
        }
    }

    public void depositAmount() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter account number: ");
        long accountNumber = scanner.nextLong();

        Account account = findAccount(accountNumber);
        if (account != null) {
            System.out.print("Enter amount to deposit: ");
            int amount = scanner.nextInt();
            if (amount <= 0) {
                System.out.println("Deposit amount must be greater than 100.");
                return;
            }
            account.updateBalance(amount);
            System.out.println("Amount deposited successfully.");
        } else {
            System.out.println("Account not found.");
        }
    }

    public void withdrawAmount() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter account number: ");
        long accountNumber = scanner.nextLong();

        Account account = findAccount(accountNumber);
        if (account != null) {
            System.out.print("Enter amount to withdraw: ");
            int amount = scanner.nextInt();
            if (amount <= 0) {
                System.out.println("Withdrawal amount must be greater than 50.");
                return;
            }
            if (account.withdraw(amount)) {
                System.out.println("Amount withdrawn successfully.");
            } else {
                System.out.println("Insufficient funds or minimum balance requirement not met.");
            }
        } else {
            System.out.println("Account not found.");
        }
    }

    public void changeAccountType() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter account number: ");
        String input = scanner.nextLine().trim();

        if (!input.matches("\\d+")) {
            System.out.println("❌ Account number must contain only digits.");
            return;
        }

        long number = Long.parseLong(input);
        Account account = findAccount(number);

        if (account == null) {
            System.out.println("❌ Account not found.");
            return;
        }

        String currentType = account instanceof SavingsAccount ? "Savings" : "Current";
        String newType = account instanceof SavingsAccount ? "Current" : "Savings";

        System.out.println("This is a " + currentType + " Account. Do you want to change it to a " + newType + " Account? (yes/no)");
        String response = scanner.nextLine().trim().toLowerCase();

        if (!response.equals("yes")) {
            System.out.println("ℹ️ Account type change cancelled.");
            return;
        }

        String name = account.getName();
        long accNumber = account.getNumber();
        int balance = account.getBalance();
        String creationDate = account.getCreationDate();

        accounts.remove(account);

        if (currentType.equals("Savings")) {
            accounts.add(new CurrentAccount(name, accNumber, creationDate, balance));
            System.out.println("✅ Account type changed to Current Account successfully.");
        } else {
            accounts.add(new SavingsAccount(name, accNumber, creationDate, balance));
            System.out.println("✅ Account type changed to Savings Account successfully.");
        }
    }

    public void viewAccountInfo() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter account number to view details: ");
        String input = scanner.nextLine().trim();

        if (!input.matches("\\d+")) {
            System.out.println("Invalid input! Account number must contain digits only.");
            return;
        }

        long number = Long.parseLong(input);
        Account account = findAccount(number);

        if (account == null) {
            System.out.println("No account found with number: " + number);
        } else {
            account.displayInfo();
        }
    }

    private Account findAccount(long accountNumber) {
        for (Account account : accounts) {
            if (account.getNumber() == accountNumber) {
                return account;
            }
        }
        return null;
    }

    private boolean nameExists(String name) {
        for (Account account : accounts) {
            if (account.getName().equalsIgnoreCase(name)) {
                return true;
            }
        }
        return false;
    }
}
