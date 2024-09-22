
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

        Q q = new Q();
        Egg Egg = new Egg(count, q, "Egg");
        Hen Hen = new Hen(count, q, "Hen");
        Egg.thread.start();
        Hen.thread.start();
    }
}

class Q {
    private boolean valueSet = true;

    void printEgg() {
        while (!valueSet) {
            try {
                wait();
            } catch (InterruptedException e) {
                System.out.println("Exception: " + e);
            }
        }
        System.out.println("Egg");
        valueSet = false;
        notifyAll();
    }

    void printHen() {
        while (valueSet) {
            try {
                wait();
            } catch (InterruptedException e) {
                System.out.println("Exception: " + e);
            }
        }
        System.out.println("Hen");
        valueSet = true;
        notifyAll();
    }
}

class Egg implements Runnable {
    Thread thread;
    String name;
    int count;
    Q q;

    Egg(int count, Q q, String name) {
        this.count = count;
        this.name = name;
        this.q = q;
        thread = new Thread(this, name);
    }

    @Override
    public void run() {
        for (int i = 1; i <= count; i++) {
            synchronized (q) {
                q.printEgg();
            }
        }
    }
}

class Hen implements Runnable {
    Thread thread;
    String name;
    int count;
    Q q;

    Hen(int count, Q q, String name) {
        this.count = count;
        this.name = name;
        this.q = q;
        thread = new Thread(this, name);
    }

    @Override
    public void run() {
        for (int i = 1; i <= count; i++) {
            synchronized (q) {
                q.printHen();
            }
        }
    }
}