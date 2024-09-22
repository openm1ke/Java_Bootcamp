import java.util.Arrays;
import java.util.Scanner;

public class Program {
    public static void main(String[] args) {
        char[] arrOfChars = new char[999];
        int[] arrOfCountChars = new int[65535];

        Scanner scanner = new Scanner(System.in);
        String str = scanner.nextLine();
        int strLen = str.length();
        arrOfChars = str.toCharArray();
        int uniqueChars = countUniqueChars(arrOfCountChars, arrOfChars, strLen);

        char[] arrChar = new char[uniqueChars];
        int[] arrCount = new int[uniqueChars];

        for(int i = 0, j = 0; i < arrOfCountChars.length; i++) {
            if(arrOfCountChars[i] == 0) continue;
            arrChar[j] = (char)i;
            arrCount[j] = arrOfCountChars[i];
            j++;
        }

        sortTwoArrays(arrChar, arrCount);
        printChart(arrChar, arrCount);
    }

    private static int countUniqueChars(int[] arrOfCountChars, char[] arrOfChars, int strLen) {
        int uniqueChars = 0;
        for(int i = 0; i < strLen; i++) {
            int c = (int)arrOfChars[i];
            arrOfCountChars[c]++;
        }
        for(int i = 0; i < arrOfCountChars.length; i++) {
            if(arrOfCountChars[i] == 0) continue;
            uniqueChars++;
        }
        return uniqueChars;
    }

    private static void sortTwoArrays(char[] a, int[] b) {
        for (int i = 0; i < b.length - 1; i++) {
            for (int j = 0; j < b.length - i - 1; j++) {
                if (b[j] < b[j + 1]) {
                    // Меняем элементы местами в int
                    int temp = b[j];
                    b[j] = b[j + 1];
                    b[j + 1] = temp;

                    // Меняем элементы местами в char
                    char c = a[j];
                    a[j] = a[j + 1];
                    a[j + 1] = c;
                }
            }
        }
    }

    private static void printChart(char[] arrChar, int[] arrCount) {
        int chartMax = arrCount.length > 10 ? 10 : arrCount.length;
        int[] sizeBars = new int[chartMax];
        String[][] arr = new String[chartMax + 2][chartMax * 2];
        fillArrayEmpty(arr, chartMax);
        countChartBars(sizeBars, chartMax, arrCount);

        for (int i = 0; i < chartMax * 2; ++i) {
            if (i % 2 == 0) {
                int temp = chartMax;
                arr[chartMax + 1][i] += arrChar[i / 2];
                for (int j = sizeBars[i / 2]; j > 0; j--) {
                    arr[temp--][i] = "#";
                }
                arr[temp][i] = ""+arrCount[i / 2];
            } else {
                for (int j = 0; j < chartMax + 2; j++) {
                    if (arrCount[i / 2] > 10 && j == chartMax - sizeBars[i / 2]) {
                        arr[j][i] = "  ";
                    } else {
                        arr[j][i] = "   ";
                    }
                }
            }
        }

        for (int i = 0; i < chartMax + 2; i++) {
            for (int j = 0; j < chartMax * 2; j++) {
                System.out.print(arr[i][j]);
            }
            System.out.println();
        }
    }

    private static void fillArrayEmpty(String[][] arr, int chartMax) {
        for (int i = 0; i < chartMax + 2; i++) {
            for (int j = 0; j < chartMax * 2; j++) {
                arr[i][j] = "";
            }
        }
    }

    private static void countChartBars(int[] sizeBars, int chartMax, int[] arrCount) {
        for (int i = 0; i < chartMax; ++i) {
            sizeBars[i] = chartMax * arrCount[i] / arrCount[0];
        }
    }
}

