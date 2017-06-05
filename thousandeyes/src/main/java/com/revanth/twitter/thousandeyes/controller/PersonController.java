package com.revanth.twitter.thousandeyes.controller;

import com.revanth.twitter.thousandeyes.entity.Person;
import com.revanth.twitter.thousandeyes.entity.PersonWithPopularFollower;
import com.revanth.twitter.thousandeyes.entity.Status;
import com.revanth.twitter.thousandeyes.exception.CustomException;
import com.revanth.twitter.thousandeyes.service.PersonService;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by Revanth on 5/30/2017.
 * <p>
 * Controller for Person
 */

@RestController
@RequestMapping("/user")
public class PersonController {

    @Autowired
    private PersonService personService;
    private static final Logger LOGGER = Logger.getLogger(PersonController.class.getName());

    /**
     * Controller to get the followers of the user.
     *
     * @param p The current person
     * @return followers of the person.
     */
    @RequestMapping(value = "/followers", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<List<Person>> getFollowersOfUser(@AuthenticationPrincipal final Person p) {
        List<Person> persons = null;
        HttpStatus status = HttpStatus.BAD_REQUEST;
        try {
            persons = personService.getFollowersOfPerson(p.getId());
            status = HttpStatus.OK;
        } catch (CustomException exception) {
            LOGGER.log(Level.INFO, exception);
        }

        return new ResponseEntity<List<Person>>(persons, status);
    }

    /**
     * Get the followees of the person
     *
     * @param p The current person
     * @return The followees of the person.
     */
    @RequestMapping(value = "/following", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<List<Person>> getUserFollowing(@AuthenticationPrincipal final Person p) {
        List<Person> persons = null;
        HttpStatus status = HttpStatus.BAD_REQUEST;
        try {
            persons = personService.getFollowingOfPerson(p.getId());
            status = HttpStatus.OK;
        } catch (CustomException exception) {
            LOGGER.log(Level.INFO, exception);
        }
        return new ResponseEntity<List<Person>>(persons, status);
    }

    /**
     * Follow a person.
     *
     * @param p                    The current person
     * @param personToBeFollowed The person whom he have to follow.
     * @return Status of the operation
     */
    @RequestMapping(value = "/followPerson", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<?> startFollowingAPerson(@AuthenticationPrincipal final Person p, @RequestParam(value = "personToBeFollowed") String personToBeFollowed) {
        Status status = new Status();
        try {
            if (p.getHandle().equals(personToBeFollowed)) {
                status.setMessage("You cannot follow yourself");
                status.setStatus("Failure");
            }
            boolean result = personService.followAPerson(p.getId(), personService.getPersonByHandle(personToBeFollowed).getId());
            if (result) {
                status.setMessage("Successfully Followed");
                status.setStatus("Success");
            } else {
                status.setMessage("You have already followed");
                status.setStatus("Failure");
            }
        } catch (Exception exception) {
            LOGGER.log(Level.INFO, exception);
        }
        return new ResponseEntity<Status>(status, HttpStatus.OK);
    }

    /**
     * Unfollow a person
     *
     * @param p                      The Current person
     * @param personToBeUnfollowed Person whom we have to un follow.
     * @return Status of the Operation.
     */
    @RequestMapping(value = "/unfollowPerson", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<?> startUnFollowAPerson(@AuthenticationPrincipal final Person p, @RequestParam(value = "personToBeUnFollowed") String personToBeUnfollowed) {
        Status status = new Status();
        try {
            if (p.getHandle().equals(personToBeUnfollowed)) {
                status.setMessage("You cannot unfollow yourself");
                status.setStatus("Failure");
            }
            boolean result = personService.followAPerson(p.getId(), personService.getPersonByHandle(personToBeUnfollowed).getId());
            if (result) {
                status.setMessage("Successfully Unfollowed");
                status.setStatus("Success");
            } else {
                status.setMessage("Please check your query");
                status.setStatus("Failure");
            }
        } catch (Exception exception) {
            LOGGER.log(Level.INFO, exception);
        }
        return new ResponseEntity<Status>(status, HttpStatus.OK);
    }

    /**
     * Returns a list of the most popular follower per person.
     *
     * @return A list of the most popular follower per person.
     */
    @RequestMapping(value = "/mostPopularFollower", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<List<PersonWithPopularFollower>> mostPopularFollower() {
        List<PersonWithPopularFollower> personList = null;
        HttpStatus status = HttpStatus.BAD_REQUEST;
        try {
            personList = personService.mostPopularFollowerPerUser();
            status = HttpStatus.OK;
        } catch (Exception exception) {
            LOGGER.log(Level.INFO, exception);
        }
        return new ResponseEntity<List<PersonWithPopularFollower>>(personList, status);
    }

    /**
     * The shortest distance between two persons.
     *
     * @param source      Source of the person
     * @param destination Destination of the person.
     * @return The distance between two persons.
     */
    @RequestMapping(value = "/shortestDistance", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<?> getShortestDistanceBetweenTwoUsers(@RequestParam(value = "source") String source, @RequestParam(value = "destination") String destination) {
        JSONObject distanceObj = new JSONObject();
        String numberOfHops = "";
        try {
            numberOfHops = personService.shortestDistanceBetweenTwoPersons(source, destination) + "";
            distanceObj.put("distance", numberOfHops);
        } catch (CustomException exception) {
            LOGGER.log(Level.INFO, exception);
        }
        return new ResponseEntity<JSONObject>(distanceObj, HttpStatus.OK);
    }

}
