public class User {
    private static int nextId = 1;
    private final int identifier;
    private String name;
    private float balance;

    public User(String name, float balance) {
        this.identifier = nextId++;
        setName(name);
        setBalance(balance);
    }

    public float getBalance() {
        return balance;
    }

    public void setBalance(float balance) {
        this.balance = balance < 0 ? 0 : balance;
    }

    public int getIdentifier() {
        return identifier;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "User{" +
                "identifier=" + identifier +
                ", name='" + name + '\'' +
                ", balance=" + balance +
                '}';
    }
}

