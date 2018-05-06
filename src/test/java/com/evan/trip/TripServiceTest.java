package com.evan.trip;

import com.evan.exception.UserNotLoggedInException;
import com.evan.user.User;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.List;

import static com.evan.trip.UserBuilder.aUser;
import static org.powermock.api.mockito.PowerMockito.when;

@RunWith(PowerMockRunner.class)
public class TripServiceTest {
    private static final User GUEST = null;
    private static final User UNUSED_USER = null;
    private static final User REGISTERED_USER = new User();
    private static final User ANOTHER_USER = new User();
    private static final Trip TO_BRAZIL = new Trip();
    private static final Trip TO_BEIJING = new Trip();
    @Mock
    private TripDAO tripDAO;
    @InjectMocks @Spy
    private TripService tripService = new TripService();

    @Test(expected = UserNotLoggedInException.class) public void
     should_throw_an_exception_when_user_is_not_logged_in() {
        tripService.getFriendTrips(UNUSED_USER, GUEST);
     }

    @Test public void
    should_not_return_any_trips_when_user_not_friends() {
        User friend = aUser()
                .friendsWith(ANOTHER_USER)
                .withTrips(TO_BRAZIL)
                .build();
         List<Trip> friendsTrip = tripService.getFriendTrips(friend, REGISTERED_USER);
         Assert.assertEquals(0, friendsTrip.size());
     }

    @Test public void
    should_return_friend_trips_when_user_are_friends() {
        User friend = aUser()
                        .friendsWith(ANOTHER_USER, REGISTERED_USER)
                        .withTrips(TO_BRAZIL,TO_BEIJING)
                        .build();
        when(tripDAO.tripsBy(friend)).thenReturn(friend.trips());
        List<Trip> friendsTrip = tripService.getFriendTrips(friend, REGISTERED_USER);
        Assert.assertEquals(2, friendsTrip.size());
    }

}
