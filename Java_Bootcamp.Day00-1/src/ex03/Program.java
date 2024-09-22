import java.util.Scanner;

public class Program {

    static int week_num = 1;
    static long res = 0, num = 1;
    static Scanner scanner = new Scanner(System.in);
    public static void main(String[] args) {
        int min_grade = 0;
        while(true) {
            min_grade = parseGrade();
            if(min_grade == -1) {
                break;
            }
            res += min_grade * num;
            num *= 10;
        }
        showResult();
        scanner.close();
    }

    public static int parseGrade() {
        String week_line = scanner.next();
        if(week_line.equals("42"))return -1;
        if(!week_line.equals("Week")) viewError();
        if(scanner.nextInt() != week_num) viewError();
        else week_num++;

        int min_grade = 10;
        int count = 5;
        while(count > 0) {
            int x = scanner.nextInt();
            if(x < min_grade) min_grade = x;
            count--;
        }
        return min_grade;
    }

    public static void showResult() {
        int weeks = 1;
        for(long i = res; i > 0; i /= 10) {
            System.out.print("Week "+weeks+++" ");
            for(int j = 0; j < i % 10; j++) {
                System.out.print("=");
            }
            System.out.println(">");
        }
    }
    public static void viewError() {
        System.out.println("IllegalArgument");
        System.exit(-1);
    }
}