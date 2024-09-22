public class UsersArrayList implements UserList {
    private int usersCount = 0;
    private int capacity = 10;
    private User[] users = new User[capacity];
    @Override
    public void addUser(User user) {
        if (usersCount == users.length) {
            capacity *= 1.5;
            User[] newUsers = new User[capacity];
            for(int i = 0; i < users.length; i++) {
                newUsers[i] = users[i];
            }
            users = newUsers;
        }
        users[usersCount++] = user;
    }

    @Override
    public User retrieveUserById(int id) throws UserNotFoundException {
        for (int i = 0; i < usersCount; i++) {
            if (users[i].getIdentifier() == id) {
                return users[i];
            }
        }
        throw new UserNotFoundException("User with id " + id + " not found");
    }

    @Override
    public User retrieveUserByIndex(int index) throws IndexOutOfBoundsException {
        if (index < 0 || index >= usersCount) {
            throw new IndexOutOfBoundsException("Index out of bounds: " + index);
        }
        return users[index];
    }

    @Override
    public int retrieveNumberOfUsers() {
        return usersCount;
    }
}
