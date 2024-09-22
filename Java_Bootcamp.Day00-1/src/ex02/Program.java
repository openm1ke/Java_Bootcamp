import java.util.Scanner;

public class Program {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int sum = 0;
        while(scanner.hasNextInt()) {
            int x = scanner.nextInt();
            if(x == 42) {
                break;
            } else {
                if(isPrime(x)) sum++;
            }
        }
        scanner.close();
        System.out.println("Count of coffee-request – "+sum);
    }

    public static boolean isPrime(int x) {
        int sum = getSum(x);
        for(int i = 2; i <= Math.sqrt(sum); i++) {
            if(sum % i == 0) {
                return false;
            }
        }
        return true;
    }

    public static int getSum(int x) {
        int sum = 0;
        while(x > 0) {
            sum += x % 10;
            x /= 10;
        }
        return sum;
    }
}



