import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class Server implements Runnable {
    private BlockingQueue<Task> q;
    private AtomicInteger waitingPeriod;
    public Task working;
    public int currentTaskIndex = 0;
    public int clientsQueued = 0;
    public int clientsProcessed = 0;

    public Server(BlockingQueue<Task> q) {
        this.q = q;
        waitingPeriod = new AtomicInteger(0);
    }

    public void addTask(Task newTask) {
        q.add(newTask);
        clientsQueued++;
        waitingPeriod.addAndGet(newTask.serviceTime);
    }

    public void run() {
        try {
            while (true) {
                if (q.size() > 0) {
                    Task task = q.take();
                    working = task;
                    clientsProcessed++;
                    Thread.sleep(task.serviceTime * 1000L);
                    working = null;
                    waitingPeriod.addAndGet(-task.serviceTime);
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public int getWaitingPeriod() {
        return waitingPeriod.get();
    }

    public List<Task> getTasks() {
        return new ArrayList<>(q);
    }
}
