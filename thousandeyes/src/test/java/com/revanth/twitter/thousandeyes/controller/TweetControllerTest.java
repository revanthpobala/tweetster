package com.revanth.twitter.thousandeyes.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

/**
 * Created by Revanth on 6/2/2017.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TweetControllerTest {
    @Autowired
    private TestRestTemplate testRestTemplate;

    /**
     * Success scenario to search the messages.
     *
     * @throws Exception
     */
    @Test
    public void testGetAllTweetsOfUsersSuccessScenario() throws Exception {
        List<Map<String, Object>> result = testRestTemplate.withBasicAuth("catwoman", "catwoman").
                getForObject("/tweet/searchForMessages?search=Cats", List.class);
        assertThat(result.get(0).get("personId"), is(3));
        assertTrue(result.get(0).get("content").toString().contains("Cats"));
    }

    /**
     * Success scenario to create a new tweet.
     *
     * @throws Exception
     */
    @Test
    public void createANewTweet() throws Exception {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.set("content", "Hi this is a new tweet");
        ResponseEntity<Object> result = testRestTemplate.withBasicAuth("ironman", "ironman").
                postForEntity("/tweet/create", params, Object.class);
        assertEquals(200, 200);
        HashMap<String, String> status = new LinkedHashMap<>();
        status.put("status", "Success");
        status.put("message", "Successfully Created");
        assertEquals(status, result.getBody());
    }

}