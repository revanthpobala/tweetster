package com.revanth.twitter.thousandeyes.repository;

import com.revanth.twitter.thousandeyes.entity.Person;
import com.revanth.twitter.thousandeyes.entity.Tweet;
import com.revanth.twitter.thousandeyes.exception.CustomException;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Interface for the Tweet Repository
 * Created by Revanth on 5/28/2017.
 */
@Repository
public interface TweetRepository {
    /**
     * Add a tweet
     *
     * @param tweet The tweet to be added
     * @return Status of the tweet.
     * @throws CustomException If something goes wrong with the database.
     */
    boolean add(Tweet tweet) throws CustomException;

    /**
     * Get all the tweets from everyone.
     *
     * @return All the tweets in the website.
     * @throws CustomException If something goes wrong with the database.
     */
    List<Tweet> getAllTweets() throws CustomException;

    /**
     * Get the tweets from the particular person
     *
     * @param person The Person, whose tweets we want to see.
     * @return A list of tweets related to that person.
     * @throws CustomException If something goes wrong with the database.
     */
    List<Tweet> getTweetsFromCurrentPerson(Person person) throws CustomException;

    /**
     * Get the tweets from the person (sent and received).
     *
     * @param person The current Person
     * @return A list of tweets
     * @throws CustomException If something goes wrong with the database.
     */
    List<Tweet> searchTweets(Person person) throws CustomException;

}
