import java.util.UUID;
public class Transaction {

    public enum TransferCategory {
        DEBIT,
        CREDIT
    };

    private final UUID identifier;
    private User recipient;
    private User sender;
    private TransferCategory transferCategory;
    private float transferAmount;

    public Transaction(User recipient, User sender, TransferCategory transferCategory, float transferAmount) {
        this.identifier = UUID.randomUUID();
        this.recipient = recipient;
        this.sender = sender;
        this.transferCategory = transferCategory;
        this.transferAmount = transferAmount;
    }

    public UUID getIdentifier() {
        return identifier;
    }

    public User getRecipient() {
        return recipient;
    }

    public User getSender() {
        return sender;
    }

    public TransferCategory getTransferCategory() {
        return transferCategory;
    }

    public float getTransferAmount() {
        return transferAmount;
    }

    public void setRecipient(User recipient) {
        this.recipient = recipient;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public void setTransferCategory(TransferCategory transferCategory) {
        this.transferCategory = transferCategory;
    }

    public void setTransferAmount(float transferAmount) {
        if(transferCategory == TransferCategory.DEBIT && transferAmount > 0) {
            this.transferAmount = 0;
        } else if(transferCategory == TransferCategory.CREDIT && transferAmount < 0) {
            this.transferAmount = 0;
        } else {
            this.transferAmount = transferAmount;
        }
    }

    @Override
    public String toString() {
        return "Transaction\nidentifier = " + identifier + "\n" +
                "recipient = " + recipient + "\n" +
                "sender = " + sender + "\n" +
                "transferCategory = " + transferCategory +
                ", transferAmount = " + transferAmount + "\n";
    }
}
