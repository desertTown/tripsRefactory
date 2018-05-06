package com.evan.trip;

import com.evan.exception.UserNotLoggedInException;
import com.evan.user.User;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static com.evan.trip.UserBuilder.aUser;

public class TripServiceTest {
    private static final User GUEST = null;
    private static final User UNUSED_USER = null;
    private static final User REGISTERED_USER = new User();
    private static final User ANOTHER_USER = new User();
    private static final Trip TO_BRAZIL = new Trip();
    private static final Trip TO_BEIJING = new Trip();
    private User loggedInUser;
    TripService tripService;
    @Before
    public void initial() {
        tripService = new TestableTripService();
        loggedInUser = REGISTERED_USER;
    }

    @Test(expected = UserNotLoggedInException.class) public void
     should_throw_an_exception_when_user_is_not_logged_in() {
         loggedInUser = GUEST;
         tripService.getTripsByUser(UNUSED_USER);
     }

    @Test public void
    should_not_return_any_trips_when_user_not_friends() {
        User friend = aUser()
                .friendsWith(ANOTHER_USER)
                .withTrips(TO_BRAZIL)
                .build();
         List<Trip> friendsTrip = tripService.getTripsByUser(friend);
         Assert.assertEquals(friendsTrip.size(), 0);
     }

    @Test public void
    should_return_friend_trips_when_user_are_friends() {
        User friend = aUser()
                        .friendsWith(ANOTHER_USER, loggedInUser)
                        .withTrips(TO_BRAZIL,TO_BEIJING)
                        .build();
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
