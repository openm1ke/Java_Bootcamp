public class Program {
    public static void main(String[] args) {

        UserIdsGenerator generator = UserIdsGenerator.getInstance();
        System.out.println(generator.generateId());
        System.out.println(generator.generateId());
        UserIdsGenerator generator2 = UserIdsGenerator.getInstance();
        System.out.println(generator2.generateId());
        System.out.println(generator == generator2);

        User Mike = new User("Mike", 100);
        User Kate = new User("Kate", 100);
        System.out.println(Mike);
        System.out.println(Kate);
        User John = new User("John", 100);
        System.out.println(John);
        
    }
}
