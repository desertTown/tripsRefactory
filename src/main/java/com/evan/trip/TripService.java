package com.evan.trip;
import com.evan.exception.UserNotLoggedInException;
import com.evan.user.User;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collections;
import java.util.List;

public class TripService {
    @Autowired
    private TripDAO tripDAO;

    List<Trip> getFriendTrips(User friend, User loggedInUser) {
        validate(loggedInUser);
        return friend.isFriendsWith(loggedInUser)
                ? tripsBy(friend)
                : noTrips();
    }

    private void validate(User loggedInUser) {
        if (loggedInUser == null) {
            throw new UserNotLoggedInException();
        }
    }

    private List<Trip> noTrips() {
        return Collections.emptyList();
    }

    private List<Trip> tripsBy(User user) {
        return tripDAO.tripsBy(user);
    }
}
