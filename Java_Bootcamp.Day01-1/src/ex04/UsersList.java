public interface UsersList {
    public void addUser(User user);

    public User retrieveUserById(int id);

    public User retrieveUserByIndex(int index);

    public int retrieveNumberOfUsers();

    User[] toArray();

    public int usersCount();
}