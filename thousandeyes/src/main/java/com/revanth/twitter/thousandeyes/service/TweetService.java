package com.revanth.twitter.thousandeyes.service;

import com.revanth.twitter.thousandeyes.entity.Tweet;
import com.revanth.twitter.thousandeyes.exception.CustomException;

import java.util.List;

/**
 * A service to search the tweets.
 * Created by Revanth on 6/2/2017.
 */
public interface TweetService {
    /**
     * Get all the tweets from everyone
     *
     * @return A list of tweets from everyone
     * @throws CustomException When Something goes wrong.
     */
    List<Tweet> getAllTweets() throws CustomException;

    /**
     * Get the tweets from the person
     *
     * @param id Id of the person
     * @return A list of tweets from the person
     * @throws CustomException When Something goes wrong.
     */
    List<Tweet> getTweetsFromAPerson(int id) throws CustomException;

    /**
     * Serach the tweets from the person based on the keyword.
     *
     * @param id     Id of the person
     * @param search Keyword to search for
     * @return A list of tweets that match the keyword.
     * @throws CustomException
     */
    List<Tweet> searchTweetsFromAPerson(int id, String search) throws CustomException;

    /**
     * Add a tweet for the user.
     *
     * @param tweet Content of the tweet.
     * @return True or False
     * @throws CustomException When Something goes wrong.
     */
    boolean addATweet(Tweet tweet) throws CustomException;

}
