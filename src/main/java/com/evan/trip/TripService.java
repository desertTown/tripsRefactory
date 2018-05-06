package com.evan.trip;
import com.evan.exception.UserNotLoggedInException;
import com.evan.user.User;
import java.util.Collections;
import java.util.List;

public class TripService {
    List<Trip> getTripsByUser(User user, User loggedInUser) {
        if (loggedInUser == null) {
            throw new UserNotLoggedInException();
        }
        return user.isFriendsWith(loggedInUser)
                ? tripsBy(user)
                : noTrips();
    }

    private List<Trip> noTrips() {
        return Collections.emptyList();
    }

    protected List<Trip> tripsBy(User user) {
        return TripDAO.findTripsByUser(user);
    }
}
