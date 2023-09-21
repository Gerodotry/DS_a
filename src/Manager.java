import java.util.concurrent.Semaphore;

public class Manager {
    private final Semaphore blockSemaphore;
    private final Semaphore taskSemaphore;
    private int[] blocks;
    private int currentBlockIndex;

    public Manager(int totalBlocks, int totalTasks) {
        blockSemaphore = new Semaphore(totalBlocks, true);
        taskSemaphore = new Semaphore(totalTasks, true);
        blocks = new int[totalBlocks];
        currentBlockIndex = 0;

        // Заповнимо блоки деякими значеннями (для прикладу).
        for (int i = 0; i < totalBlocks; i++) {
            blocks[i] = i + 1;
        }
    }

    public int getNextBlock() throws InterruptedException {
        blockSemaphore.acquire();
        if (currentBlockIndex >= blocks.length) {
            blockSemaphore.release();
            return -1; // Повертаємо -1, якщо блоків більше немає.
        }
        int nextBlock = blocks[currentBlockIndex];
        currentBlockIndex++;
        return nextBlock;
    }

    public void returnBlock() {
        blockSemaphore.release();
    }

    public void taskCompleted() {
        taskSemaphore.release();
    }

    public int getTotalTasks() {
        return taskSemaphore.availablePermits();
    }
}
