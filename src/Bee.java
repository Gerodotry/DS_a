import java.util.concurrent.ThreadLocalRandom;

public class Bee implements Runnable {
    private final int id;
    private final Manager manager;

    public Bee(int id, Manager manager) {
        this.id = id;
        this.manager = manager;
    }

    @Override
    public void run() {
        while (true) {
            try {
                int blockNumber = manager.getNextBlock();
                if (blockNumber == -1) {
                    System.out.println("Потік " + id + " закінчив роботу.");
                    break;
                }

                System.out.println("Потік " + id + " перевіряє блок " + blockNumber);

                // Симулюємо пошук "1" в блоку (випадково).
                Thread.sleep(ThreadLocalRandom.current().nextInt(100, 1000));

                // Якщо знайдено "1", завершуємо роботу.
                if (ThreadLocalRandom.current().nextBoolean()) {
                    System.out.println("Потік " + id + " знайшов 1 в блоку " + blockNumber);
                    manager.taskCompleted();
                    break;
                } else {
                    System.out.println("Потік " + id + " не знайшов 1 в блоку " + blockNumber);
                    manager.returnBlock();
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
