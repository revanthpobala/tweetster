package com.revanth.twitter.thousandeyes.repository;

import com.revanth.twitter.thousandeyes.entity.Person;
import com.revanth.twitter.thousandeyes.entity.Tweet;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Class to test the Tweet Repository
 * Created by Revanth on 5/31/2017.
 */

@RunWith(SpringRunner.class)
@SpringBootTest
public class TweetRepositoryImplTest {

    @Autowired
    private TweetRepository tweetRepository;

    @Mock
    private Person mockPerson;

    @Test
    public void getAllTweets() throws Exception {
        assertThat(tweetRepository.getAllTweets().size(), is(120));
    }

    /**
     * Test to get the tweets from current person success scenario.
     *
     * @throws Exception
     */
    @Test
    public void testGetTweetsFromCurrentPerson() throws Exception {
        MockitoAnnotations.initMocks(this);
        Mockito.when(mockPerson.getId()).thenReturn(2);
        List<Tweet> tweets = tweetRepository.getTweetsFromCurrentPerson(mockPerson);
        assertThat(tweets.size(), is(15));
        assertThat(tweets.get(0).getPersonId(), is(2));
        assertThat(tweets.get(0).getContent(), is("There is a right and a wrong in the universe. And the distinction is not hard to make."));
    }

    /**
     * Test to search the tweets based on the keyword.
     *
     * @throws Exception
     */
    @Test
    public void searchTweets() throws Exception {
        Mockito.when(mockPerson.getId()).thenReturn(2);
        List<Tweet> tweets = tweetRepository.searchTweets(mockPerson);
        assertThat(tweets.size(), is(49));
        assertThat(tweets.get(0).getContent(), is("Phasellus libero mauris, aliquam eu, accumsan sed, facilisis vitae, orci."));
    }
}