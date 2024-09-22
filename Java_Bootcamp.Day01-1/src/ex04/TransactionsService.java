import java.util.UUID;

public class TransactionsService {
    private UsersList userlist = new UsersArrayList();

    public void addUser(User user) {
        userlist.addUser(user);
    }

    public float getBalance(User user) {
        return userlist.retrieveUserById(user.getIdentifier()).getBalance();
    }

    public void addTransaction(int recipientId, int senderId, float amount) throws IllegalTransactionException {
        User recipient = userlist.retrieveUserById(recipientId);
        User sender = userlist.retrieveUserById(senderId);

        if (sender.getBalance() < amount) {
            throw new IllegalTransactionException("Insufficient funds of user with id " + senderId + ", balance: "
                    + sender.getBalance() + " and amount = " + amount);
        }

        UUID newTransactionId = UUID.randomUUID();

        Transaction income = new Transaction(recipient, sender, Transaction.TransferCategory.CREDIT, amount,
                newTransactionId);
        Transaction outcome = new Transaction(sender, recipient, Transaction.TransferCategory.DEBIT, -amount,
                newTransactionId);

        recipient.getTransactions().addTransaction(income);
        sender.getTransactions().addTransaction(outcome);

        recipient.setBalance(amount + recipient.getBalance());
        sender.setBalance(-amount + sender.getBalance());
    }

    public Transaction[] getTransactionsByUser(User user) {
        return user.getTransactions().toArray();
    }

    public void removeTransaction(int userId, UUID transactionId) {
        User user = userlist.retrieveUserById(userId);
        user.getTransactions().removeTransaction(transactionId);
    }

    public Transaction[] checkValidityOfTransactions() {
        // Check validity of transactions (returns an ARRAY of unpaired transactions).
        TransactionsList unpairedTransactions = new TransactionsLinkedList();

        for (int i = 0; i < userlist.usersCount(); i++) {
            Transaction[] currentUserTransactions = getTransactionsByUser(userlist.retrieveUserByIndex(i));
            for (Transaction transaction : currentUserTransactions) {
                if (unpairedTransactions.findTransaction(transaction.getIdentifier())) {
                    unpairedTransactions.removeTransaction(transaction.getIdentifier());
                } else {
                    unpairedTransactions.addTransaction(transaction);
                }
            }
        }
        return unpairedTransactions.toArray();
    }
}
