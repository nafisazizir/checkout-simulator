import java.util.Comparator;

public class EventComp implements Comparator<Event> {
    @Override
    public int compare(Event event1, Event event2) {
        if (event1.getTime() != event2.getTime()) {
            if (event1.getTime() - event2.getTime() < 0) {
                return -1;
            } else {
                return 1;
            }
        } else {
            return event1.getEventNum() - event2.getEventNum();
        }
    }
}
