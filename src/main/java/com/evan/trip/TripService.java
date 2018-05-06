package com.evan.trip;

import com.evan.exception.UserNotLoggedInException;
import com.evan.user.User;
import com.evan.user.UserSession;

import java.util.ArrayList;
import java.util.List;

public class TripService {
    public List<Trip> getTripsByUser(User user) throws UserNotLoggedInException {
        List<Trip> tripList = new ArrayList<Trip>();
        User loggedUser = getLoggedInUser();
        boolean isFriend = false;
        if (loggedUser != null) {
            for (User friend : user.getFriends()) {
                if (friend.equals(loggedUser)) {
                    isFriend = true;
                    break;
                }
            }
            if (isFriend) {
                tripList = tripsBy(user);
            }
            return tripList;
        } else {
            throw new UserNotLoggedInException();
        }
    }

    protected List<Trip> tripsBy(User user) {
        return TripDAO.findTripsByUser(user);
    }

    protected User getLoggedInUser() {
        return UserSession.getInstance().getLoggedUser();
    }
}
