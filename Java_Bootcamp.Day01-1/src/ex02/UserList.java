public interface UserList {
    public void addUser(User user);
    public User retrieveUserById(int id);
    public User retrieveUserByIndex(int index);
    public int retrieveNumberOfUsers();   
}