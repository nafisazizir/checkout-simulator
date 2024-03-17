/**
 * Models a Leave Class that extends from Event,
 * used to simulate the LEAVE event.
 */
public class Leave extends Event {

    /**
     * Build constructor for Leave class.
     * @param time the time of the event.
     * @param eventNum the number of the event.
     * @param customer the customer of the event.
     */
    public Leave(double time, int eventNum, Customer customer) {
        super(time, eventNum, customer);
    }

    @Override
    Pair<Event, ImList<Server>> updateStatus(ImList<Server> servers, int numOfServers) {
        return new Pair<>(this, servers);
    }

    @Override
    int updateLeft(int leftCustomer) {
        return leftCustomer + 1;
    }

    @Override
    public String toString() {
        return String.format("%.3f " + getCustomer().getCustomerNum() + " leaves", getTime());
    }
}
