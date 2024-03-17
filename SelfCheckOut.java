import java.util.function.Supplier;

public class SelfCheckOut extends Server {
    private final ImList<Customer> queueList;

    public SelfCheckOut(double availableTime, int qmax, int queue, int serverNum,
                        Supplier<Double> restTimes) {
        super(availableTime, qmax, queue, serverNum, restTimes);
        this.queueList = new ImList<>();
    }

    public SelfCheckOut(double availableTime, int qmax, int queue, int serverNum,
                       ImList<Customer> queueList, Supplier<Double> restTimes) {
        super(availableTime, qmax, queue, serverNum, restTimes);
        this.queueList = queueList;
    }

    @Override
    public Server updateAvailableTime(double arrivalTime, double serviceTime) {
        return new SelfCheckOut(arrivalTime + serviceTime, getQmax(), getQueue(),
                getServerNum(), queueList, getRestTimes());
    }

    @Override
    public Server updateQueue(int num) {
        return new SelfCheckOut(getAvailableTime(), getQmax(), getQueue() + num,
                getServerNum(), queueList, getRestTimes());
    }

    @Override
    public Server addQueue(Customer customer) {
        return new SelfCheckOut(getAvailableTime(), getQmax(), getQueue(), getServerNum(),
                queueList.add(customer), getRestTimes());
    }

    @Override
    public Server removeQueue() {
        ImList<Customer> newQueueList = queueList.remove(0);
        return new SelfCheckOut(getAvailableTime(), getQmax(), getQueue(), getServerNum(),
                newQueueList, getRestTimes());
    }

    @Override
    public boolean isAvailable(Customer customer) {
        return customer.getArrivalTime() >= getAvailableTime() && getQueue() == 0;
    }

    @Override
    public boolean checkQueueAndAvailability(Customer customer, double currentTime) {
        if (currentTime >= getAvailableTime() && queueList.get(0).equals(customer)) {
            return true;
        }
        return false;
    }

    @Override
    public Server setRestTime(double currentTime) {
        return updateAvailableTime(currentTime, 0);
    }

    @Override
    public boolean isFull() {
        return getQueue() == getQmax();
    }

    @Override
    public String toString() {
        return "self-check " + getServerNum();
    }
}
