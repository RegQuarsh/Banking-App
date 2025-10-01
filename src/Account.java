public abstract class Account {
    private String name;
    private long number;
    private String creationDate;
    protected int balance;

    public Account(String name, long number, String creationDate, int balance) {
        this.name = name;
        this.number = number;
        this.creationDate = creationDate;
        this.balance = balance;
    }

    public String getName() {
        return name;
    }

    public int getBalance() {
        return balance;
    }


    public Account() {

    }

    public long getNumber() {

        return number;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void display() {
        System.out.println("Account - Name: " + name + ", Account Number: " + number +
                ", Creation Date: " + creationDate + ", Balance: " + balance);
    }
    public void displayBasicInfo() {
        System.out.println("Name: " + name + ", Account Number: " + number);
    }


    public void updateBalance(int amount) {
        balance += amount;
    }

    public abstract boolean withdraw(int amount);

    public abstract void displayInfo();

}
