package com.revanth.twitter.thousandeyes.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

/**
 * Created by Revanth on 6/2/2017.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)

public class PersonControllerTest {

    @Autowired
    private TestRestTemplate testRestTemplate;

    /**
     * Check for basic authentication.
     */
    @Test
    public void testForBasicUserAuthentication() {
        ResponseEntity<String> response = testRestTemplate.getForEntity("/user/followers", String.class);
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }

    /**
     * Check for the followers of the current user.
     *
     * @throws Exception
     */
    @Test
    public void testGetFollowersOfUserSuccessScenario() throws Exception {
        List<Map<String, Object>> result = testRestTemplate.withBasicAuth("1", "1")
                .getForObject("/user/followers", List.class);
        assertEquals(6, result.size());
        assertEquals(10, result.get(0).get("id"));
    }

    /**
     * Check who the user is following
     *
     * @throws Exception
     */
    @Test
    public void testGetUserFollowingSuccessScenario() throws Exception {
        List<Map<String, Object>> result = testRestTemplate.withBasicAuth("1", "1")
                .getForObject("/user/following", List.class);
        assertEquals(5, result.size());
        assertEquals(8, result.get(0).get("id"));
    }

    /**
     * Follow a person. Check for correct mappings. This test works if the person is followed
     *
     * @throws Exception
     */
    @Test
    public void testStartFollowingAPersonFaliureScenario() throws Exception {
        MultiValueMap<String, Integer> params = new LinkedMultiValueMap<>();
        params.set("personToBeFollowed", 6);
        ResponseEntity<Object> result = testRestTemplate.withBasicAuth("9", "9").
                postForEntity("/user/followPerson", params, Object.class);
        assertEquals(200, 200);
        HashMap<String, String> status = new LinkedHashMap<>();
        status.put("status", "Success");
        status.put("message", "Successfully Followed");
        assertEquals(status, result.getBody());
    }

    /**
     * Unfollow a person.
     *
     * @throws Exception
     */
    @Test
    public void testStartUnFollowAPersonSuccessScenario() throws Exception {
        MultiValueMap<String, Integer> params = new LinkedMultiValueMap<>();
        params.set("personToBeUnFollowed", 6);
        ResponseEntity<Object> result = testRestTemplate.withBasicAuth("9", "9").
                postForEntity("/user/unfollowPerson", params, Object.class);
        assertEquals(200, 200);
        HashMap<String, String> status = new LinkedHashMap<>();
        status.put("status", "Success");
        status.put("message", "Successfully Unfollowed");
        assertEquals(status, result.getBody());
    }

    /**
     * Follow a person. Success Scenario
     *
     * @throws Exception
     */
    @Test
    public void testStartFollowingAPersonSuccessScenario() throws Exception {
        MultiValueMap<String, Integer> params = new LinkedMultiValueMap<>();
        params.set("personToBeFollowed", 6);
        ResponseEntity<Object> result = testRestTemplate.withBasicAuth("9", "9").
                postForEntity("/user/followPerson", params, Object.class);
        assertEquals(200, 200);
        HashMap<String, String> status = new LinkedHashMap<>();
        status.put("status", "Failure");
        status.put("message", "You have already followed");
        assertEquals(status, result.getBody());
    }

    /**
     * Check for the most popular follower
     *
     * @throws Exception
     */
    @Test
    public void mostPopularFollower() throws Exception {
        List<Map<String, Object>> result = testRestTemplate.withBasicAuth("1", "1")
                .getForObject("/user/mostPopularFollower", List.class);
        assertEquals(18, result.size());
        assertEquals(10, result.get(0).get("person_id"));
    }


}