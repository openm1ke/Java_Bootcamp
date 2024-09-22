public class Program {
    public static void main(String[] args) {

        TransactionsService transactionsService = new TransactionsService();
        User Mike = new User("Mike", 100);
        User Kate = new User("Kate", 100);
        int MikeId = Mike.getIdentifier();
        int KateId = Kate.getIdentifier();

        transactionsService.addUser(Mike);
        transactionsService.addUser(Kate);

        transactionsService.addTransaction(KateId, MikeId, 50);

        System.out.println(Mike);
        System.out.println(Kate);

        System.out.println("List of Mike transactions: ");
        Transaction[] MikeTransactions = Mike.getTransactions().toArray();
        for (Transaction transaction : MikeTransactions) {
            System.out.println(transaction);
        }

        Transaction upaired = new Transaction(Kate, Mike, Transaction.TransferCategory.CREDIT, 30);
        Mike.getTransactions().addTransaction(upaired);

        System.out.println("List of Mike transactions: ");
        MikeTransactions = Mike.getTransactions().toArray();
        for (Transaction transaction : MikeTransactions) {
            System.out.println(transaction);
        }

        System.out.println("Show unpaired transactions: ");
        Transaction[] unpairedTransactions = transactionsService.checkValidityOfTransactions();
        for (Transaction transaction : unpairedTransactions) {
            System.out.println(transaction);
        }

        System.out.println("Try transfer more then balance: ");
        transactionsService.addTransaction(KateId, MikeId, 200);
    }
}
