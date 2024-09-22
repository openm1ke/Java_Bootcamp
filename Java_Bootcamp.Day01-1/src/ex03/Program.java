import java.util.UUID;
public class Program {
    public static void main(String[] args) {
        TransactionsLinkedList transactions = new TransactionsLinkedList();
        Transaction[] transactionsArray;

        User John = new User("John", 100);
        User Mike = new User("Mike", 100);
        User Kate = new User("Kate", 100);
        User Jane = new User("Jane", 100);

        Transaction income1 = new Transaction(John, Mike, Transaction.TransferCategory.CREDIT, 10);
        John.getTransactions().addTransaction(income1);
        Transaction outcome1 = new Transaction(Mike, John, Transaction.TransferCategory.DEBIT, -10);
        Mike.getTransactions().addTransaction(outcome1);

        Transaction income2 = new Transaction(John, Jane, Transaction.TransferCategory.CREDIT, 10);
        John.getTransactions().addTransaction(income2);
        Transaction outcome2 = new Transaction(Jane, John, Transaction.TransferCategory.DEBIT, -10);
        Jane.getTransactions().addTransaction(outcome2);
        
        Transaction income3 = new Transaction(John, Kate, Transaction.TransferCategory.CREDIT, 10);
        John.getTransactions().addTransaction(income3);
        Transaction outcome3 = new Transaction(Kate, John, Transaction.TransferCategory.DEBIT, -10);
        Kate.getTransactions().addTransaction(outcome3);

        transactions.addTransaction(income1);
        transactions.addTransaction(outcome1);
        transactions.addTransaction(income2);
        transactions.addTransaction(outcome2);
        transactions.addTransaction(income3);
        transactions.addTransaction(outcome3);

        System.out.println("List of all transactions: ");
        transactionsArray = transactions.toArray();
        for (Transaction transaction : transactionsArray) {
            System.out.println(transaction);
        }
        
        System.out.println("List of John transactions: ");
        Transaction[] JohnTransactions = John.getTransactions().toArray();
        for (Transaction transaction : JohnTransactions) {
            System.out.println(transaction);
        }

        UUID idToRemove = JohnTransactions[1].getIdentifier();
        System.out.println("UUID to remove: " + idToRemove);
        try {
            transactions.removeTransaction(idToRemove);
        } catch (TransactionNotFoundException e) {
            System.out.println(e.getMessage());
        }

        System.out.println("List of all transactions after removing " + idToRemove + ": ");
        transactionsArray = transactions.toArray();
        for (Transaction transaction : transactionsArray) {
            System.out.println(transaction);
        }

        System.out.println("Remove transaction with id " + idToRemove + " from John's list:");
        try {
            John.getTransactions().removeTransaction(idToRemove);
        } catch (TransactionNotFoundException e) {
            System.out.println(e.getMessage());
        }

        System.out.println("List of John transactions after removing " + idToRemove + ": ");
        JohnTransactions = John.getTransactions().toArray();
        for (Transaction transaction : JohnTransactions) {
            System.out.println(transaction);
        }

        UUID fakeId = UUID.randomUUID();
        System.out.println("Try to delete fake transaction with id " + fakeId + ": ");
        
        try {
            transactions.removeTransaction(fakeId);
        } catch (TransactionNotFoundException e) {
            System.out.println(e.getMessage());
        }

        System.out.println("Try delete null transaction: ");
        try {
            transactions.removeTransaction(null);
        } catch (TransactionNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }
}
