import java.util.UUID;

interface TransactionsList {
    void addTransaction(Transaction transaction);
    void removeTransaction(UUID identifier);
    Transaction[] toArray();
}
