package com.revanth.twitter.thousandeyes.service.impl;

import com.revanth.twitter.thousandeyes.entity.Person;
import com.revanth.twitter.thousandeyes.entity.Tweet;
import com.revanth.twitter.thousandeyes.exception.CustomException;
import com.revanth.twitter.thousandeyes.repository.PersonRepository;
import com.revanth.twitter.thousandeyes.repository.TweetRepository;
import com.revanth.twitter.thousandeyes.service.TweetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Revanth on 6/2/2017.
 */
@Service
public class TweetServiceImpl implements TweetService {

    @Autowired
    private TweetRepository tweetRepository;

    @Autowired
    private PersonRepository personRepository;

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Tweet> getAllTweets() throws CustomException {
        return tweetRepository.getAllTweets();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Tweet> getTweetsFromAPerson(int id) throws CustomException {
        Person p = personRepository.getPersonById(id).get(0);
        return tweetRepository.getTweetsFromCurrentPerson(p);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Tweet> searchTweetsFromAPerson(int id, String search) throws CustomException {
        Person p = personRepository.getPersonById(id).get(0);
        List<Tweet> tweets = tweetRepository.searchTweets(p);
        return tweets.stream().filter(tweet -> tweet.getContent().contains(search)).collect(Collectors.toList());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean addATweet(Tweet tweet) throws CustomException {
        return tweetRepository.add(tweet);

    }
}
