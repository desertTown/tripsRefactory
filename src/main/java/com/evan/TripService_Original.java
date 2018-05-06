package com.evan;

import com.evan.exception.UserNotLoggedInException;
import com.evan.trip.Trip;
import com.evan.trip.TripDAO;
import com.evan.user.User;
import com.evan.user.UserSession;

import java.util.ArrayList;
import java.util.List;

public class TripService_Original {
    public List<Trip> getTripsByUser(User user) throws UserNotLoggedInException {
        List<Trip> tripList = new ArrayList<Trip>();
        User loggedUser = UserSession.getInstance().getLoggedUser();
        boolean isFriend = false;
        if (loggedUser != null) {
            System.out.println("xxxx");
            for (User friend : user.getFriends()) {
                if (friend.equals(loggedUser)) {
                    isFriend = true;
                    break;
                }
            }
            if (isFriend) {
                tripList = TripDAO.findTripsByUser(user);
            }
            return tripList;
        } else {
            throw new UserNotLoggedInException();
        }
    }
}
