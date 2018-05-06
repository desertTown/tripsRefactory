package com.evan.trip;

import com.evan.exception.UserNotLoggedInException;
import com.evan.user.User;
import com.evan.user.UserSession;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TripService {
    public List<Trip> getTripsByUser(User user) throws UserNotLoggedInException {
        if (getLoggedInUser() == null) {
            throw new UserNotLoggedInException();
        }
        return user.isFriendsWith(getLoggedInUser())
                ? tripsBy(user)
                : noTrips();
    }

    private List<Trip> noTrips() {
        return Collections.emptyList();
    }

    protected List<Trip> tripsBy(User user) {
        return TripDAO.findTripsByUser(user);
    }

    protected User getLoggedInUser() {
        return UserSession.getInstance().getLoggedUser();
    }
}
