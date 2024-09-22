public class UserIdsGenerator {
    private int nextId = 1;
    private static final UserIdsGenerator instance = new UserIdsGenerator();

    private UserIdsGenerator() {
    }

    public static UserIdsGenerator getInstance() {
        return instance;
    }

    public int generateId() {
        return nextId++;
    }
}
