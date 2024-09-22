
public class Program {

    public static void main(String[] args) {
        int count = 10;
        if (args.length == 1) {
            if (args[0].startsWith("--count=")) {
                String[] split = args[0].split("=");
                try {
                    count = Integer.parseInt(split[1]);
                } catch (NumberFormatException e) {
                    System.out.println("Введено некорректное значение");
                }
            }
        }

        Egg Egg = new Egg(count, "Egg");
        Hen Hen = new Hen(count, "Hen");
        Egg.thread.start();
        Hen.thread.start();
        try {
            Egg.thread.join();
            Hen.thread.join();

            for (int i = 1; i <= count; i++) {
                System.out.println("Human");
            }
        } catch (InterruptedException e) {
            System.out.println("Exception: " + e);
        }
    }
}

class Egg implements Runnable {
    Thread thread;
    String name;
    int count;

    Egg(int count, String name) {
        this.count = count;
        this.name = name;
        thread = new Thread(this, name);
    }

    @Override
    public void run() {
        for (int i = 1; i <= count; i++) {
            System.out.println(Thread.currentThread().getName());
        }
    }
}

class Hen implements Runnable {
    Thread thread;
    String name;
    int count;

    Hen(int count, String name) {
        this.count = count;
        this.name = name;
        thread = new Thread(this, name);
    }

    @Override
    public void run() {
        for (int i = 1; i <= count; i++) {
            System.out.println(Thread.currentThread().getName());
        }
    }
}