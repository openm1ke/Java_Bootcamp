public class Program {
    public static void main(String[] args) {
        String mode = new String("prod");

        if (args.length == 1) {
            if (args[0].equals("--profile=dev")) {
                mode = new String("dev");
            }
        }

        Menu menu = new Menu(mode);
        menu.run();
    }
}
