import java.util.Scanner;

public class Program {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int x = scanner.nextInt();
        scanner.close();
        
        if(x <= 0 || x == 1) {
            System.out.println("Illegal argument");
            System.exit(-1);
        } else {
            int i;
            for(i = 2; i <= Math.sqrt(x); i++) {
                if(x % i == 0) {
                    System.out.println("false " + (i-1));
                    System.exit(0);
                }
            }
            System.out.println("true " + (i-1));
        }
    }
}