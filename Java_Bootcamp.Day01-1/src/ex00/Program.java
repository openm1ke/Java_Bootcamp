public class Program {
    public static void main(String[] args) {
        User Mike = new User("Mike", 100);
        User Kate = new User("Kate", 100);

        System.out.println(Mike);
        System.out.println(Kate);
        
        // Transaction From Kate -> Mike, +10, INCOME
        Transaction income = new Transaction(Kate, Mike, Transaction.TransferCategory.CREDIT, 10);
        // Transaction From Mike -> Kate, -10, OUTCOME
        Transaction outcome = new Transaction(Mike, Kate, Transaction.TransferCategory.DEBIT, -10);
        
        System.out.println(income);
        System.out.println(outcome);
        
        Kate.setBalance(Kate.getBalance() + income.getTransferAmount());
        Mike.setBalance(Mike.getBalance() + outcome.getTransferAmount());
        
        System.out.println(Mike);
        System.out.println(Kate);
        
    }
}
