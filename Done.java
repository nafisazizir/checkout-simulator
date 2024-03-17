/**
 * Models a Done Class that extends from Event,
 * used to simulate the DONE event.
 */
public class Done extends Event {
    private final int serverNum;
    private final int numOfServers;

    /**
     * Build constructor for Done Class.
     * @param time the time of the event.
     * @param eventNum the number of the event.
     * @param customer the customer of the event.
     * @param serverNum the number of the server.
     */
    public Done(double time, int eventNum, Customer customer, int serverNum, int numOfServers) {
        super(time, eventNum, customer);
        this.serverNum = serverNum;
        this.numOfServers = numOfServers;
    }

    @Override
    int getServerNum() {
        return serverNum;
    }

    @Override
    Pair<Event, ImList<Server>> updateStatus(ImList<Server> servers, int numOfServers) {
        if (serverNum <= numOfServers) {
            servers = servers.set(serverNum - 1, servers.get(serverNum - 1).removeQueue());
        }

        servers = servers.set(serverNum - 1, servers.get(serverNum - 1).setRestTime(getTime()));

        return new Pair<>(this, servers);
    }

    @Override
    public String toString() {
        if (serverNum > numOfServers) {
            return String.format("%.3f " + getCustomer().getCustomerNum() +
                    " done serving by self-check " + getServerNum(), getTime());
        }

        return String.format("%.3f " + getCustomer().getCustomerNum() + " done serving by " +
                getServerNum(), getTime());
    }
}
