package com.revanth.twitter.thousandeyes.service;

import com.revanth.twitter.thousandeyes.entity.PersonWithPopularFollower;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

/**
 * Test class for the person service.
 * Created by Revanth on 6/1/2017.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class PersonServiceTest {

    @Autowired
    private PersonService personService;

    /**
     * Test to get the followers of a person success scenario.
     *
     * @throws Exception
     */
    @Test
    public void getFollowersOfPerson() throws Exception {
        int followersOfPerson = personService.getFollowersOfPerson(9).size();
        assertThat(followersOfPerson, is(4));
    }

    /**
     * Test to get the followees of the person.
     *
     * @throws Exception
     */
    @Test
    public void getFollowingOfPerson() throws Exception {
        int followingAPerson = personService.getFollowingOfPerson(4).size();
        assertThat(followingAPerson, is(6));
    }

    /**
     * Test to follow a person. success scenario
     *
     * @throws Exception
     */
    @Test
    public void followAPerson() throws Exception {
        assertTrue(personService.followAPerson(3, 10));
    }

    /**
     * Test to unfollow a person success scenario
     *
     * @throws Exception
     */
    @Test
    public void unFollowAPerson() throws Exception {
        assertTrue(personService.unFollowAPerson(3, 5));
    }

    /**
     * Test to get the most popular follower per user.
     *
     * @throws Exception
     */
    @Test
    public void mostPopularFollowerPerUser() throws Exception {
        List<PersonWithPopularFollower> result = personService.mostPopularFollowerPerUser();
        assertThat(result.get(0).getCnt(), is(7));
    }

    /**
     * Test to find the shortest distance between two persons.
     *
     * @throws Exception
     */
    @Test
    public void shortestDistanceBetweenTwoPersons() throws Exception {
        int distance = personService.shortestDistanceBetweenTwoPersons("catwoman", "zod");
        assertThat(distance, is(1));
    }

}