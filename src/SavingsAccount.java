public class SavingsAccount extends Account {
    public SavingsAccount(String name, long number, String creationDate, int balance) {
        super(name, number, creationDate, balance);
    }

    @Override
    public boolean withdraw(int amount) {
        double serviceCharge = 0.015; // 1.5% service charge for Savings
        int totalDeduction = (int) (amount + (amount * serviceCharge));
        if (balance - totalDeduction >= BankApp.minBalanceRemainAfterWithdraw) {
            updateBalance(-totalDeduction);
            return true;
        }
        return false;
    }

    public void addInterest() {
        double interestRate = 0.02; // 2% interest
        int interest = (int) (balance * interestRate);
        updateBalance(interest);
        System.out.println("Interest of " + interest + " has been added to your balance.");
    }


    public void deposit(int amount) {
        updateBalance(amount);
    }


    public void displayInfo() {
        System.out.println("Account Type: Savings");
        display(); // This calls the method in the Account superclass to show name, number, balance, etc.
    }

}
