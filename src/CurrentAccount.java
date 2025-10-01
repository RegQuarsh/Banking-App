public class CurrentAccount extends Account {
    public CurrentAccount(String name, long number, String creationDate, int balance) {
        super(name, number, creationDate, balance);
    }

    @Override
    public boolean withdraw(int amount) {
        double serviceCharge = 0.005; // 0.5% service charge for Current
        int totalDeduction = (int) (amount + (amount * serviceCharge));
        if (balance - totalDeduction >= BankApp.minBalanceRemainAfterWithdraw) {
            updateBalance(-totalDeduction);
            return true;
        }
        return false;
    }

    public void chargeFee() {
        int fee = 50; // flat fee
        if (balance - fee >= BankApp.minBalanceRemainAfterWithdraw) {
            updateBalance(-fee);
            System.out.println("Fee charged: " + fee);
        } else {
            System.out.println("Cannot charge fee, minimum balance requirement not met.");
        }
    }


    public void displayInfo() {
        System.out.println("Account Type: Current");
        display();
    }


}
