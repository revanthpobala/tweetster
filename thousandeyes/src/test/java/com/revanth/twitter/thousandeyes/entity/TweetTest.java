package com.revanth.twitter.thousandeyes.entity;

import org.junit.After;
import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

/**
 * Test for Tweet Class.
 * Created by Revanth on 6/1/2017.
 */
public class TweetTest {

    private Tweet tweet = new Tweet();
    private static String TWEET_MESSAGE = "The Fake News Media works hard at disparaging & demeaning my use of social media because they don't want America to hear the real story!";

    @After
    public void tearDown() throws Exception {
        tweet = null;
    }

    @Test
    public void setPersonId() throws Exception {
        tweet.setPersonId(3);
        assertThat(tweet.getPersonId(), is(3));
    }

    @Test
    public void setContent() throws Exception {
        tweet.setContent(TWEET_MESSAGE);
        assertThat(tweet.getContent(), is(TWEET_MESSAGE));
    }

}