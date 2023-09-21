public class Main {
    public static void main(String[] args) {
        int totalBlocks = 10;
        int totalTasks = 100;
        Manager manager = new Manager(totalBlocks, totalTasks);

        for (int i = 1; i <= 5; i++) {
            Thread workerThread = new Thread(new Bee(i, manager));
            workerThread.start();
        }

        // Очікуємо завершення всіх потоків.
        try {
            for (int i = 1; i <= 5; i++) {
                Thread.currentThread().join();
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        // Виводимо кількість залишилися завдань у менеджера.
        System.out.println("Залишилися завдань у менеджера: " + manager.getTotalTasks());
    }
}
