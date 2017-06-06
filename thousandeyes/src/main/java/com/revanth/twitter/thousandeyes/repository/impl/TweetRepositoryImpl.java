package com.revanth.twitter.thousandeyes.repository.impl;

import com.revanth.twitter.thousandeyes.entity.Person;
import com.revanth.twitter.thousandeyes.entity.Tweet;
import com.revanth.twitter.thousandeyes.exception.CustomException;
import com.revanth.twitter.thousandeyes.repository.TweetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Revanth on 5/31/2017.
 */
@Repository
public class TweetRepositoryImpl implements TweetRepository {

    private static String FIND_TWEETS_FROM_A_PERSON = "SELECT * FROM messages WHERE person_id = :id";
    private static String GET_TWEETS_SENT_AND_RECEIVED_BY_A_PERSON =
            "SELECT t.* FROM messages t JOIN followers f ON t.person_id = f.person_id WHERE f.follower_person_id = :id  " +
                    "UNION ALL SELECT * FROM messages WHERE person_id = :id  ORDER BY id DESC";
    private static String INSERT_A_NEW_TWEET = "INSERT INTO messages(person_id, content) VALUES(:id, :content);";
    private static String TWEET_TABLE = "select * from messages";

    @Autowired
    private NamedParameterJdbcTemplate db;

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean add(Tweet tweet) throws CustomException {
        try {
            HashMap<String, Object> sqlParameters = new HashMap<>();
            sqlParameters.put("id", tweet.getPersonId());
            sqlParameters.put("content", tweet.getContent());
            return db.update(INSERT_A_NEW_TWEET, sqlParameters) == 1;
        } catch (DataAccessException exception) {
            throw new CustomException(exception.getMessage());
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Tweet> getAllTweets() throws CustomException {
        List<Tweet> tweetMessages = db.query(TWEET_TABLE, new BeanPropertyRowMapper<Tweet>(Tweet.class));
        return tweetMessages;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Tweet> getTweetsFromCurrentPerson(Person person) throws CustomException {
        try {
            HashMap<String, Integer> sqlParameters = new HashMap<>();
            sqlParameters.put("id", person.getId());
            List<Tweet> tweetsFromPerson = db.query(FIND_TWEETS_FROM_A_PERSON, sqlParameters, new BeanPropertyRowMapper<Tweet>(Tweet.class));
            return tweetsFromPerson;
        } catch (DataAccessException exception) {
            throw new CustomException(exception.getMessage());
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Tweet> searchTweets(Person person) throws CustomException {
        try {
            HashMap<String, Object> sqlParameters = new HashMap<>();
            sqlParameters.put("id", person.getId());
            return db.query(GET_TWEETS_SENT_AND_RECEIVED_BY_A_PERSON, sqlParameters, new BeanPropertyRowMapper<Tweet>(Tweet.class));
        } catch (DataAccessException exception) {
            throw new CustomException(exception.getMessage());
        }

    }

}
