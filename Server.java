import java.util.function.Supplier;

/**
 * Models a Server class to store all the information
 * of each server.
 */
public abstract class Server {
    private final double availableTime;
    private final int qmax;
    private final int queue;
    private final int serverNum;
    private final Supplier<Double> restTimes;

    /**
     * Build constructor for Server Class.
     * @param availableTime the available time of the server.
     * @param qmax the maximum queue of the server.
     * @param queue the current queue of the server.
     * @param serverNum the number of the server.
     */
    public Server(double availableTime, int qmax, int queue, int serverNum,
                  Supplier<Double> restTimes) {
        this.availableTime = availableTime;
        this.qmax = qmax;
        this.queue = queue;
        this.serverNum = serverNum;
        this.restTimes = restTimes;
    }

    public Supplier<Double> getRestTimes() {
        return restTimes;
    }

    public int getQmax() {
        return qmax;
    }

    public int getQueue() {
        return queue;
    }

    public double getAvailableTime() {
        return this.availableTime;
    }

    public int getServerNum() {
        return serverNum;
    }

    public Server updateAvailableTime(double arrivalTime, double serviceTime) {
        return this;
    }

    public Server updateQueue(int num) {
        return this;
    }

    public Server addQueue(Customer customer) {
        return this;
    }

    public Server removeQueue() {
        return this;
    }

    public boolean isAvailable(Customer customer) {
        return false;
    }

    public boolean checkQueueAndAvailability(Customer customer, double currentTime) {
        return false;
    }

    public Server setRestTime(double currentTime) {
        return this;
    }

    public boolean isFull() {
        return false;
    }
}
