import java.util.Scanner;

public class BankingApplication {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        BankingApplication bank = new BankingApplication();
        int choice;

        do {
            System.out.println("\n--- Banking Menu ---");
            System.out.println("1. Create Account");
            System.out.println("2. Deposit");
            System.out.println("3. Withdraw");
            System.out.println("4. View Account");
            System.out.println("5. Change Account Type");
            System.out.println("6. Close Account");
            System.out.println("0. Exit");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();
            scanner.nextLine();  // Consume newline

            switch (choice) {
                case 1:
                    System.out.print("Enter name: ");
                    String name = scanner.nextLine();
                    System.out.print("Enter account type (Savings/Current): ");
                    String type = scanner.nextLine();
                    bank.createAccount(name, type);
                    break;

                case 2:
                    System.out.print("Enter account number: ");
                    String accNumD = scanner.nextLine();
                    BankAccount accD = bank.findAccount(accNumD);
                    if (accD != null) {
                        System.out.print("Enter deposit amount: ");
                        double amt = scanner.nextDouble();
                        accD.deposit(amt);
                    } else {
                        System.out.println("Account not found.");
                    }
                    break;

                case 3:
                    System.out.print("Enter account number: ");
                    String accNumW = scanner.nextLine();
                    BankAccount accW = bank.findAccount(accNumW);
                    if (accW != null) {
                        System.out.print("Enter withdrawal amount: ");
                        double amtW = scanner.nextDouble();
                        accW.withdraw(amtW);
                    } else {
                        System.out.println("Account not found.");
                    }
                    break;

                case 4:
                    System.out.print("Enter account number: ");
                    String accNumV = scanner.nextLine();
                    BankAccount accV = bank.findAccount(accNumV);
                    if (accV != null) {
                        accV.displayInfo();
                    } else {
                        System.out.println("Account not found.");
                    }
                    break;

                case 5:
                    System.out.print("Enter account number: ");
                    String accNumC = scanner.nextLine();
                    System.out.print("Enter new account type (Savings/Current): ");
                    String newType = scanner.nextLine();
                    bank.changeAccountType(accNumC, newType);
                    break;

                case 6:
                    System.out.print("Enter account number: ");
                    String accNumX = scanner.nextLine();
                    bank.closeAccount(accNumX);
                    break;

                case 0:
                    System.out.println("Thank you for using the bank app!");
                    break;

                default:
                    System.out.println("Invalid choice.");
            }

        } while (choice != 0);
    }

    private void createAccount(String name, String type) {
    }
}
