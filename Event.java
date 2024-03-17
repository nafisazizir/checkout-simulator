/**
 * Models an abstract Event class to store all the data
 * that need to be processed.
 */
abstract class Event {
    private final double time;
    private final int eventNum;
    private final Customer customer;

    /**
     * Build rhe event class constuctor.
     * @param time the time of the event.
     * @param eventNum the number of the event.
     * @param customer the customer of the event.
     */
    Event(double time, int eventNum, Customer customer) {
        this.time = time;
        this.eventNum = eventNum;
        this.customer = customer;
    }

    double getTime() {
        return time;
    }

    int getEventNum() {
        return eventNum;
    }

    Customer getCustomer() {
        return customer;
    }

    int getServerNum() {
        return 0;
    }

    int updateServed(int servedCustomer) {
        return servedCustomer;
    }

    int updateLeft(int leftCustomer) {
        return leftCustomer;
    }

    double updateWaitTime(double waitTime, ImList<Server> servers) {
        return waitTime;
    }

    Pair<Event, ImList<Server>> updateStatus(ImList<Server> servers, int numOfServers) {
        return new Pair<>(this, servers);
    }
}