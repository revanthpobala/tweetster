package com.revanth.twitter.thousandeyes.service;

import com.revanth.twitter.thousandeyes.entity.Tweet;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

/**
 * Class to test the tweet service.
 * Created by Revanth on 6/2/2017.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class TweetServiceTest {

    @Autowired
    private TweetService tweetService;

    /**
     * Test get all the tweets success scenario.
     *
     * @throws Exception
     */
    @Test
    public void testGetAllTweetsSuccessScenario() throws Exception {
        List<Tweet> tweets = tweetService.getAllTweets();
        assertThat(tweets.size(), is(120));
    }

    /**
     * Test to get the tweets from a particular person.
     *
     * @throws Exception
     */
    @Test
    public void testGetTweetsFromAPersonSuccessScenario() throws Exception {
        List<Tweet> tweets = tweetService.getTweetsFromAPerson(3);
        int size = tweets.size();
        assertThat(tweets.size(), is(15));
        assertThat(tweets.get(0).getContent(), is("Cats come when they feel like, not when they're told."));

    }

    /**
     * Test to search the tweets based on the keyword.
     *
     * @throws Exception
     */
    @Test
    public void testSearchTweetsFromAPersonSuccessScenario() throws Exception {
        List<Tweet> tweets = tweetService.searchTweetsFromAPerson(10, "ellus");
        assertThat(tweets.size(), is(11));
    }

}