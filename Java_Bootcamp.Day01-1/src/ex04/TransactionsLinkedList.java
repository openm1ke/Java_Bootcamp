import java.util.UUID;

public class TransactionsLinkedList implements TransactionsList {
    private Node first = new Node(null);
    private Node last = new Node(null);
    private int size = 0;

    public TransactionsLinkedList() {
        first.next = last;
        last.prev = first;
    }

    @Override
    public void addTransaction(Transaction transaction) {
        Node node = new Node(transaction);
        Node lastNode = last.prev;

        lastNode.next = node;
        node.prev = lastNode;
        node.next = last;
        last.prev = node;

        size++;
    }

    @Override
    public void removeTransaction(UUID identifier) throws TransactionNotFoundException {
        if (identifier == null) {
            throw new IllegalArgumentException("Identifier cannot be null");
        }
        Node curNode = first.next;
        while (curNode != last) {
            if (curNode.transaction.getIdentifier().equals(identifier)) {
                curNode.prev.next = curNode.next;
                curNode.next.prev = curNode.prev;
                size--;
                return; // выйти если получилось удалить
            }
            curNode = curNode.next;
        }
        // выбросить исключение, если не удалось удалить
        throw new TransactionNotFoundException("Transaction with id " + identifier + " not found");
    }

    @Override
    public Transaction[] toArray() {
        Transaction[] transactions = new Transaction[size];
        Node curNode = first.next;
        for (int i = 0; i < size; i++) {
            transactions[i] = curNode.transaction;
            curNode = curNode.next;
        }
        return transactions;
    }

    @Override
    public boolean findTransaction(UUID identifier) {
        Node curNode = first.next;
        while (curNode != last) {
            if (curNode.transaction.getIdentifier().equals(identifier)) {
                return true;
            }
            curNode = curNode.next;
        }
        return false;
    }

    public static class Node {
        private Node prev;
        private Node next;
        private Transaction transaction;

        public Node(Transaction transaction) {
            this.transaction = transaction;
            this.next = null;
            this.prev = null;
        }
    }
}
