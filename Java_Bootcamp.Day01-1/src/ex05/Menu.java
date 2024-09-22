import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.UUID;

public class Menu {

    private interface InnerMenu {
        public void show();
    }

    private interface InnerCommand {
        public void get();
    }

    private class InnerProdMenu implements InnerMenu {
        @Override
        public void show() {
            System.out.println("1. Add a user");
            System.out.println("2. Show user balance");
            System.out.println("3. Perform a transaction");
            System.out.println("4. Show all transactions for a user");
            System.out.println("5. Finish execution");
        }
    }

    private class InnerDevMenu implements InnerMenu {
        @Override
        public void show() {
            System.out.println("1. Add a user");
            System.out.println("2. Show user balance");
            System.out.println("3. Perform a transaction");
            System.out.println("4. Show all transactions for a user");
            System.out.println("5. DEV - remove a transaction by ID");
            System.out.println("6. DEV - check transaction validity");
            System.out.println("7. Finish execution");
        }
    }

    private class InnerProdCommand implements InnerCommand {
        @Override
        public void get() {
            try {
                int choice = scanner.nextInt();
                switch (choice) {
                    case 1:
                        addUser();
                        break;
                    case 2:
                        showUserBalance();
                        break;
                    case 3:
                        performTransfer();
                        break;
                    case 4:
                        viewAllTransactionByUserId();
                        break;
                    case 5:
                        System.out.println("Exit");
                        System.exit(0);
                        break;
                    default:
                        System.out.println("Not valid choice!");
                        break;
                }
            } catch (InputMismatchException e) {
                System.out.println("Not valid data!");
                scanner.next();
            }
        }
    }

    private class InnerDevCommand implements InnerCommand {
        @Override
        public void get() {
            try {
                int choice = scanner.nextInt();
                switch (choice) {
                    case 1:
                        addUser();
                        break;
                    case 2:
                        showUserBalance();
                        break;
                    case 3:
                        performTransfer();
                        break;
                    case 4:
                        viewAllTransactionByUserId();
                        break;
                    case 5:
                        removeTransferById();
                        break;
                    case 6:
                        checkTransferValidity();
                        break;
                    case 7:
                        System.out.println("Exit");
                        System.exit(0);
                        break;
                    default:
                        System.out.println("Not valid choice!");
                        break;
                }
            } catch (InputMismatchException e) {
                System.out.println("Not valid data!");
                scanner.next();
            }
        }
    }

    private void addUser() {
        System.out.println("Enter a user name and a balance");
        scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        String[] data = input.split(" ");
        if (data.length == 2) {
            ts.addUser(new User(data[0], Float.parseFloat(data[1])));
            User user = ts.getLastAddedUser();
            System.out.println("User with id = " + user.getIdentifier() + " is added");
        } else {
            System.out.println("Not valid data!");
        }
        System.out.println("---------------------------------------------------------");
    }

    private void showUserBalance() {
        System.out.println("Enter a user ID");
        scanner = new Scanner(System.in);
        try {
            int input = scanner.nextInt();
            User user = ts.retrieveUserById(input);
            System.out.println(user.getName() + " - " + user.getBalance());
        } catch (UserNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (InputMismatchException e) {
            System.out.println("Not valid data!");
            scanner.next();
        }
        System.out.println("---------------------------------------------------------");
    }

    private void performTransfer() {
        System.out.println("Enter a sender ID, a recipient ID, and a transfer amount");
        scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        String[] data = input.split(" ");
        if (data.length == 3) {
            ts.addTransaction(Integer.parseInt(data[1]), Integer.parseInt(data[0]), Float.parseFloat(data[2]));
            System.out.println("The transfer is completed");
        } else {
            System.out.println("Not valid data!");
        }
        System.out.println("---------------------------------------------------------");
    }

    private void viewAllTransactionByUserId() {
        System.out.println("Enter a user ID");
        scanner = new Scanner(System.in);
        try {
            int input = scanner.nextInt();
            User user = ts.retrieveUserById(input);
            Transaction[] transactions = ts.getTransactionsByUser(user);
            if (transactions.length == 0) {
                System.out.println("There are no transactions for this user");
            } else {
                for (Transaction transaction : transactions) {
                    System.out.println(transaction);
                }
            }
        } catch (UserNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (InputMismatchException e) {
            System.out.println("Not valid data!");
            scanner.next();
        }
        System.out.println("---------------------------------------------------------");
    }

    public void removeTransferById() throws UserNotFoundException, TransactionNotFoundException {
        System.out.println("Enter a user ID and a transfer ID");
        scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        String[] data = input.split(" ");
        if (data.length == 2) {
            try {
                int userId = Integer.parseInt(data[0]);
                UUID transferId = UUID.fromString(data[1]);
                boolean foundedTransaction = false;
                User user = ts.retrieveUserById(userId);
                Transaction[] transactions = ts.getTransactionsByUser(user);
                for (Transaction transaction : transactions) {
                    if (transaction.getIdentifier().equals(transferId)) {
                        foundedTransaction = true;
                        ts.removeTransaction(userId, transferId);
                        System.out.println("Transfer To " + transaction.getSender().getName() + "(id = "
                                + transaction.getSender().getIdentifier() + ") " +
                                transaction.getTransferAmount() + " removed");
                        break;
                    }
                }

                if (!foundedTransaction) {
                    throw new TransactionNotFoundException("Transfer with id = " + transferId + " not found");
                }
            } catch (UserNotFoundException | TransactionNotFoundException | IllegalArgumentException e) {
                System.out.println(e.getMessage());
            } catch (InputMismatchException e) {
                System.out.println("Not valid data!");
                scanner.next();
            }
        }
        System.out.println("---------------------------------------------------------");
    }

    public void checkTransferValidity() {
        System.out.println("Check results:");
        Transaction[] unpaired = ts.checkValidityOfTransactions();
        if (unpaired.length == 0) {
            System.out.println("No unpaired transactions");
        } else {
            for (Transaction transaction : unpaired) {
                String result = transaction.getRecipient().getName() + "(id = "
                        + transaction.getRecipient().getIdentifier() + ") " +
                        "has an unknownledged transfer id = " + transaction.getIdentifier() + " " +
                        "from " + transaction.getSender().getName() + "(id = " + transaction.getSender().getIdentifier()
                        + ") " +
                        "for " + transaction.getTransferAmount();
                System.out.println(result);
            }
        }
        System.out.println("---------------------------------------------------------");
    }

    private String mode;
    private InnerMenu menu = new InnerProdMenu();
    private InnerCommand command = new InnerProdCommand();
    private TransactionsService ts = new TransactionsService();

    private static Scanner scanner = new Scanner(System.in);

    public Menu(String mode) {
        this.mode = mode;

        if (mode.equals("dev")) {
            menu = new InnerDevMenu();
            command = new InnerDevCommand();
        }
    }

    public void run() {
        while (true) {
            menu.show();
            command.get();
        }
    }
}
