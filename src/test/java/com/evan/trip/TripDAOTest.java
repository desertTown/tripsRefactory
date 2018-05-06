package com.evan.trip;

import com.evan.exception.CollaboratorCallException;
import com.evan.user.User;
import org.junit.Test;

public class TripDAOTest {
    @Test(expected = CollaboratorCallException.class) public void
    should_throw_exception_when_retrieving_trips() {
        new TripDAO().tripsBy(new User());
    }
}
