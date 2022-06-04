import java.util.concurrent.ThreadLocalRandom;

public class Task implements Comparable<Task> {
    public int ID;
    public int arrivalTime;
    public int serviceTime;

    public Task(int ID, int arrivalTime, int serviceTime) {
        this.ID = ID;
        this.arrivalTime = arrivalTime;
        this.serviceTime = serviceTime;
    }

    public Task(int ID, int minArrivalTime, int maxArrivalTime, int minProcessingTime, int maxProcessingTime, int timeLimit) {
        this.ID = ID;
        this.arrivalTime = ThreadLocalRandom.current().nextInt(minArrivalTime, maxArrivalTime);
        this.serviceTime = ThreadLocalRandom.current().nextInt(minProcessingTime, maxProcessingTime);
    }

    @Override
    public int compareTo(Task o) {
        if (this.arrivalTime < o.arrivalTime)
            return -1;
        else if (this.arrivalTime > o.arrivalTime)
            return 1;
        return 0;
    }

    @Override
    public String toString() {
        return "(" + ID + " " + arrivalTime + " " + serviceTime + ")";
    }
}
