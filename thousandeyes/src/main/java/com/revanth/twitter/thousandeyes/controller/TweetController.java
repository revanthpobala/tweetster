package com.revanth.twitter.thousandeyes.controller;

import com.revanth.twitter.thousandeyes.entity.Person;
import com.revanth.twitter.thousandeyes.entity.Status;
import com.revanth.twitter.thousandeyes.entity.Tweet;
import com.revanth.twitter.thousandeyes.exception.CustomException;
import com.revanth.twitter.thousandeyes.service.TweetService;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
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
 * Rest Controller to search for tweets.
 * <p>
 * Created by Revanth on 5/30/2017.
 */
@RestController
@RequestMapping(value = "/tweet")
public class TweetController {

    @Autowired
    private TweetService tweetService;

    private static final Logger LOGGER = Logger.getLogger(TweetController.class.getName());

    /**
     * Search for the messages when a search parameter is passed. If a search parameter is not passed then return all the tweets
     *
     * @param p      The Current Person
     * @param search Search keyword for the person
     * @return A list of tweets that has the search keyword.
     */
    @RequestMapping(value = "/searchForMessages", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<List<Tweet>> getAllTweetsOfUsers(@AuthenticationPrincipal final Person p, @RequestParam(name = "search", required = false) final String search) {
        List<Tweet> tweets = null;
        try {
            if (search != null) {
                tweets = tweetService.searchTweetsFromAPerson(p.getId(), search);
            } else {
                tweets = tweetService.getTweetsFromAPerson(p.getId());
            }
        } catch (Exception e) {
            LOGGER.log(Level.INFO, e);
        }
        return new ResponseEntity<List<Tweet>>(tweets, HttpStatus.OK);
    }

    /**
     * @param p       The current person
     * @param content The tweet message that the user intend to create.
     * @return The status of the message created/ not created.
     */
    @RequestMapping(value = "/create", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<Status> createANewTweet(@AuthenticationPrincipal final Person p, @RequestParam(name = "content", required = true) final String content) {
        HttpStatus stats = HttpStatus.CREATED;
        Status status = new Status();
        try {
            Tweet tweet = new Tweet();
            tweet.setPersonId(p.getId());
            tweet.setContent(content);

            if (tweetService.addATweet(tweet)) {
                status.setMessage("Successfully Created");
                status.setStatus("Success");
            } else {
                status.setMessage("Not created");
                status.setStatus("Failure");
                stats = HttpStatus.EXPECTATION_FAILED;
            }
        } catch (CustomException exception) {
            status.setStatus("Could not perform the operation");
            status.setMessage(exception.getMessage());
            LOGGER.log(Level.INFO, exception);
        }
        return new ResponseEntity<Status>(status, stats);
    }


}
