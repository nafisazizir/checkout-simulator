/**
 * Models an Arrive Class that extends from Event,
 * used to simulate the ARRIVE event.
 */
public class Arrive extends Event {

    /**
     * Build Arrive constructor.
     * @param time the time of the event.
     * @param eventNum the number of the event.
     * @param customer the customer of particular event.
     */
    public Arrive(double time, int eventNum, Customer customer) {
        super(time, eventNum, customer);
    }

    @Override
    Pair<Event, ImList<Server>> updateStatus(ImList<Server> servers, int numOfServers) {
        boolean isServed = false;
        Event nextEvent = this;

        for (int j = 0; j < servers.size(); j++) {
            if (servers.get(j).isAvailable(getCustomer())) {

                if (j >= numOfServers) {
                    servers = addCheckoutQueue(servers, numOfServers);
                } else {
                    servers = servers.set(j, servers.get(j).addQueue(getCustomer()));
                }

                isServed = true;

                nextEvent = new Serve(getCustomer().getArrivalTime(),
                        getCustomer().getCustomerNum(), getCustomer(), j + 1,
                        numOfServers);

                break;
            }
        }

        if (!isServed) {
            for (int j = 0; j < servers.size(); j++) {
                if (!servers.get(j).isFull()) {
                    if (j >= numOfServers) {
                        servers = updateCheckoutQueue(servers, numOfServers);

                        if (numOfServers == 0) {
                            nextEvent = new Wait(getCustomer().getArrivalTime(),
                                    getCustomer().getCustomerNum(), getCustomer(), 1,
                                    numOfServers);
                        } else {
                            nextEvent = new Wait(getCustomer().getArrivalTime(),
                                    getCustomer().getCustomerNum(), getCustomer(), numOfServers + 1,
                                    numOfServers);
                        }

                    } else {
                        servers = servers.set(j, servers.get(j).updateQueue(1));
                        servers = servers.set(j, servers.get(j).addQueue(getCustomer()));
                        nextEvent = new Wait(getCustomer().getArrivalTime(),
                                getCustomer().getCustomerNum(), getCustomer(), j + 1,
                                numOfServers);
                    }

                    isServed = true;

                    break;
                }
            }
        }

        if (!isServed) {
            nextEvent = new Leave(getCustomer().getArrivalTime(), getCustomer().getCustomerNum(),
                    getCustomer());
        }

        return new Pair<>(nextEvent, servers);
    }

    ImList<Server> updateCheckoutQueue(ImList<Server> servers, int numOfServers) {
        for (int i = numOfServers; i < servers.size(); i++) {
            servers = servers.set(i, servers.get(i).updateQueue(1));
            servers = servers.set(i, servers.get(i).addQueue(getCustomer()));
        }

        return servers;
    }

    ImList<Server> addCheckoutQueue(ImList<Server> servers, int numOfServers) {
        for (int i = numOfServers; i < servers.size(); i++) {
            servers = servers.set(i, servers.get(i).addQueue(getCustomer()));
        }

        return servers;
    }

    /**
     * Method to override toString().
     * @return String representation.
     */
    @Override
    public String toString() {
        return String.format("%.3f " + getCustomer().getCustomerNum() + " arrives", getTime());
    }
}
