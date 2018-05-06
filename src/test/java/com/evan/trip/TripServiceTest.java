package com.evan.trip;

import com.evan.exception.UserNotLoggedInException;
import com.evan.user.User;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;


public class TripServiceTest {
    private static final User GUEST = null;
    private static final User UNUSED_USER = null;
    private static final User REGISTED_USER = new User();
    private static final User ANOTHER_USER = new User();
    private static final Trip TO_BRAZIL = new Trip();
    private static final Trip TO_BEIJING = new Trip();
    private User loggedInUser;
    TripService tripService;
    @Before
    public void initial() {
        tripService = new TestableTripService();
        loggedInUser = REGISTED_USER;
    }

    @Test(expected = UserNotLoggedInException.class) public void
     should_throw_an_exception_when_user_is_not_logged_in() {
         loggedInUser = GUEST;
         tripService.getTripsByUser(UNUSED_USER);
     }

    @Test public void
    should_not_return_any_trips_when_user_not_friends() {
         User friend = new User();
         friend.addFriend(ANOTHER_USER);
         friend.addTrip(TO_BRAZIL);
         List<Trip> friendsTrip = tripService.getTripsByUser(friend);
         Assert.assertEquals(friendsTrip.size(), 0);
     }

    @Test public void
    should_return_friend_trips_when_user_are_friends() {
        User friend = new User();
        friend.addFriend(ANOTHER_USER);
        friend.addFriend(loggedInUser);
        friend.addTrip(TO_BRAZIL);
        friend.addTrip(TO_BEIJING);
        List<Trip> friendsTrip = tripService.getTripsByUser(friend);
        Assert.assertEquals(friendsTrip.size(), 2);
    }

     private class TestableTripService extends TripService{
         @Override
         protected User getLoggedInUser() {
             return loggedInUser;
         }

         @Override
         protected List<Trip> tripsBy(User user) {
             return user.trips();
         }
     }
}
