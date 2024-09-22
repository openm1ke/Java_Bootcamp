public class Program {
    public static void main(String[] args) {
        UsersArrayList users = new UsersArrayList();

        System.out.println(users.retrieveNumberOfUsers());

        users.addUser(new User("Mike", 100));
        users.addUser(new User("Kate", 100));
        users.addUser(new User("John", 100));
        users.addUser(new User("Jane", 100));

        System.out.println(users.retrieveNumberOfUsers());

        System.out.println(users.retrieveUserByIndex(0));

        System.out.println(users.retrieveUserById(1).getName());

        System.out.println(users.retrieveUserById(5));
    }
}
