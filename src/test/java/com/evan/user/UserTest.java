package com.evan.user;

import com.evan.trip.UserBuilder;
import org.junit.Assert;
import org.junit.Test;

public class UserTest {
    private static final User EVAN = new User();
    private static final User BOB = new User();

    @Test public void
    should_inform_when_user_are_not_friend() {
        User user = UserBuilder.aUser()
                        .friendsWith(EVAN)
                        .build();
        Assert.assertFalse(user.isFriendsWith(BOB));
    }

    @Test public void
    should_inform_when_user_are_friend() {
        User user = UserBuilder.aUser()
                .friendsWith(EVAN)
                .friendsWith(BOB)
                .build();
        Assert.assertTrue(user.isFriendsWith(BOB));
    }
}
