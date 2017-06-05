package com.revanth.twitter.thousandeyes.repository;

import com.revanth.twitter.thousandeyes.entity.Person;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

/**
 * Class to test the Person Repository
 * Created by Revanth on 6/1/2017.
 */

@RunWith(SpringRunner.class)
@SpringBootTest
public class PersonRepositoryImplTest {

    @Autowired
    PersonRepository personRepository;

    @Mock
    private Person mockPerson1;

    @Mock
    private Person mockPerson2;

    @Mock
    private NamedParameterJdbcTemplate mockNamedParameterJdbcTemplate;

    @Mock
    private BeanPropertyRowMapper beanPropertyRowMapperMock;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    /**
     * Test for all people Success scenario
     *
     * @throws Exception
     */
    @Test
    public void testGetAllPeopleSuccessScenario() throws Exception {
        assertThat(personRepository.getAllPeople().size(), is(10));
    }

    /**
     * Check if the query returns correct output
     *
     * @throws Exception
     */
    @Test
    public void testGetPersonByIdSuccessScenario() throws Exception {
        Person result = personRepository.getPersonById(1).get(0);
        assertThat(result.getName(), is("Bruce Wayne"));
        assertThat(result.getHandle(), is("batman"));
    }

    /**
     * Check if the query returns the correct number of followers
     *
     * @throws Exception
     */
    @Test
    public void getFollowersOfPerson() throws Exception {
        when(mockPerson1.getId()).thenReturn(1);
        List<Person> result = personRepository.getFollowersOfPerson(mockPerson1);
        assertThat(result.get(0).getName(), is("Charles Xavier"));
        assertThat(result.get(0).getHandle(), is("profx"));
        assertThat(result.size(), is(6));
    }

    /**
     * Check if the query returns correct number of followees
     *
     * @throws Exception
     */
    @Test
    public void getFolloweesOfPerson() throws Exception {
        when(mockPerson1.getId()).thenReturn(1);
        List<Person> result = personRepository.getFolloweesOfPerson(mockPerson1);
        assertThat(result.size(), is(5));
        assertThat(result.get(0).getName(), is("Peter Parker"));
        assertThat(result.get(0).getHandle(), is("spiderman"));
    }

    /**
     * Query return true if the query executed.
     *
     * @throws Exception
     */
    @Test
    public void testFollowPersonSuccessScenario() throws Exception {
        when(mockPerson1.getId()).thenReturn(2);
        when(mockPerson2.getId()).thenReturn(3);
        assertTrue(personRepository.followPerson(mockPerson1, mockPerson2));
    }


    /**
     * Check for the method un follow a person.
     *
     * @throws Exception
     */
    @Test
    public void testUnFollowPersonSuccessScenario() throws Exception {
        when(mockPerson1.getId()).thenReturn(10);
        when(mockPerson2.getId()).thenReturn(9);
        assertTrue(personRepository.unFollowPerson(mockPerson1, mockPerson2));
    }

    /**
     * Check whether a person is following or not
     *
     * @throws Exception
     */
    @Test
    public void isFollowing() throws Exception {
        when(mockPerson1.getId()).thenReturn(8);
        when(mockPerson2.getId()).thenReturn(4);
        assertTrue(personRepository.isFollowing(mockPerson1, mockPerson2));
    }

    /**
     * Check for popular follower.
     *
     * @throws Exception
     */
    @Test
    public void personWithPopularFollower() throws Exception {
        assertThat(personRepository.personWithPopularFollower().size(), is(18));
    }

    /**
     * Check for the shortest distance between two persons.
     *
     * @throws Exception
     */
    @Test
    public void testShortestDistanceBetweenTwoPersons() throws Exception {
        when(mockPerson1.getId()).thenReturn(8);
        when(mockPerson2.getId()).thenReturn(4);
        assertThat(personRepository.shortestDistanceBetweenTwoPersons(mockPerson1, mockPerson2), is(2));
    }

    /**
     * Check whether we are getting correct person when we query by the person's handle.
     */
    @Test
    public void testForPersonHandle() throws Exception {
        assertThat(personRepository.getPersonByHandle("batman").getName(), is("Bruce Wayne"));
    }

    /**
     * Check whether we are getting correct person when we query by the person's name.
     *
     * @throws Exception
     */
    @Test
    public void testForPersonByName() throws Exception {
        assertThat(personRepository.getPersonByName("Bruce Wayne").get(0).getHandle(), is("batman"));
    }

}