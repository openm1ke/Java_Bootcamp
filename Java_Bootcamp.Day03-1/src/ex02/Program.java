import java.util.Random;

public class Program {
    private static int totalSum = 0;

    public static void main(String[] args) {
        int arraySize = 10;
        int threadsCount = 5;
        if (args.length == 2) {
            if (args[0].startsWith("--arraySize=")) {
                String[] split = args[0].split("=");
                try {
                    arraySize = Integer.parseInt(split[1]);
                } catch (NumberFormatException e) {
                    System.out.println("Введено некорректное значение");
                }
            }
            if (args[1].startsWith("--threadsCount=")) {
                String[] split = args[1].split("=");
                try {
                    threadsCount = Integer.parseInt(split[1]);
                } catch (NumberFormatException e) {
                    System.out.println("Введено некорректное значение");
                }
            }
        }

        int[] array = generateArray(arraySize);

        System.out.println("Sum: " + sumArray(array));

        Thread threads[] = new Thread[threadsCount];
        int step = arraySize / threadsCount;
        int lastIndex = 0;

        for (int i = 0; i < threadsCount; i++) {
            int startIndex = lastIndex;
            int endIndex = i == threadsCount - 1 ? arraySize : startIndex + step;
            lastIndex = endIndex;

            threads[i] = new Thread(new MyThread(array, startIndex, endIndex, i));
            threads[i].start();
        }

        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                System.out.println("Exception: " + e);
            }
        }

        System.out.println("Total sum: " + totalSum);

    }

    public static void addToTotalSum(int sum) {
        synchronized (Program.class) {
            Program.totalSum += sum;
        }
    }

    private static int[] generateArray(int size) {
        int[] array = new int[size];
        for (int i = 0; i < size; i++) {
            array[i] = new Random().nextInt(100);
        }
        return array;
    }

    private static int sumArray(int[] array) {
        int sum = 0;
        for (int i = 0; i < array.length; i++) {
            sum += array[i];
        }
        return sum;
    }
}

class MyThread implements Runnable {
    private int[] array;
    private int threadId;
    private int startIndex;
    private int endIndex;

    public MyThread(int[] array, int startIndex, int endIndex, int threadId) {
        this.array = array;
        this.threadId = threadId;
        this.startIndex = startIndex;
        this.endIndex = endIndex;
    }

    @Override
    public void run() {
        int sum = 0;
        for (int i = startIndex; i < endIndex; i++) {
            sum += array[i];
        }
        System.out
                .println("Thread " + threadId + ": from " + (startIndex + 1) + " to " + (endIndex) + " sum is " + sum);
        Program.addToTotalSum(sum);
    }
}
