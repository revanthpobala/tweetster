package com.revanth.twitter.thousandeyes.repository.impl;

import com.revanth.twitter.thousandeyes.entity.Followers;
import com.revanth.twitter.thousandeyes.entity.Person;
import com.revanth.twitter.thousandeyes.entity.PersonWithPopularFollower;
import com.revanth.twitter.thousandeyes.exception.CustomException;
import com.revanth.twitter.thousandeyes.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.*;

/**
 * Implementation class for the Person Repositiory
 * Created by Revanth on 5/28/2017.
 */

@Repository
public class PersonRepositoryImpl implements PersonRepository {


    private static final String FIND_PERSON_BY_HANDLE = "SELECT * FROM PEOPLE where handle = '";
    private static final String FIND_PERSON_BY_NAME = "SELECT * FROM PEOPLE where name = '";
    private static final String FOLLOW_A_PERSON = "INSERT INTO followers (person_id, follower_person_id) VALUES (:person_id, :follower_person_id)";
    private static final String GET_ALL_PERSONS_SQL = "SELECT * FROM people";
    private static final String GET_FOLLOWEES_OF_A_PERSON = "select p.* from people p join followers f on p.id = f.person_id where f.follower_person_id = ";
    private static final String GET_FOLLOWERS_OF_A_PERSON = "select p.* from PEOPLE p join FOLLOWERS f on p.id = f.follower_person_id where f.person_id = ";
    private static final String GET_MOST_POPULAR_PERSON = "SELECT p.id, p.person_id, p.follower_person_id, p.cnt FROM  \n" +
            "     (SELECT t1.id, t1.person_id, t1.follower_person_id, t2.cnt FROM  \n" +
            "    followers AS t1 JOIN (SELECT person_id, COUNT(*) AS cnt FROM followers GROUP BY person_id) AS t2  \n" +
            "     ON t1.follower_person_id = t2.person_id) p INNER JOIN (SELECT person_id, MAX(cnt) AS maxcnt  \n" +
            "     FROM (SELECT t1.person_id, t1.follower_person_id, t2.cnt FROM followers AS t1 \n" +
            "     JOIN (SELECT person_id, COUNT(*) AS cnt FROM followers GROUP BY person_id ) AS t2 \n" +
            "     ON t1.follower_person_id = t2.person_id) GROUP BY person_id) AS q  \n" +
            "     ON p.person_id = q.person_id AND p.cnt = q.maxcnt";
    private static final String GET_PERSON_BY_ID = "SELECT * FROM people WHERE id = ";
    private static final String IS_FOLLOWING = "SELECT * FROM followers WHERE follower_person_id = :followedPerson AND person_id = :currentPerson";
    private static final String MAP_FOLLOWERS_TABLE = "SELECT * FROM followers";
    private static final String UN_FOLLOW_A_PERSON = "DELETE FROM followers WHERE person_id = :person_id AND follower_person_id = :follower_person_id";

    private SqlParameterSource sqlParameterSource = new MapSqlParameterSource();

    @Autowired
    private NamedParameterJdbcTemplate db;

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Person> getAllPeople() throws CustomException {
        List<Person> users = null;
        try {
            users = db.query(GET_ALL_PERSONS_SQL, new BeanPropertyRowMapper<Person>(Person.class));
        } catch (DataAccessException exception) {
            throw new CustomException(exception.getMessage());
        }
        return users;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Person> getPersonById(int id) throws CustomException {
        List<Person> result = null;
        try {
            result = db.query(GET_PERSON_BY_ID + id, new BeanPropertyRowMapper<Person>(Person.class));
        } catch (DataAccessException exception) {
            throw new CustomException(exception.getMessage());
        }
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Person> getPersonByName(String name) throws CustomException {
        List<Person> persons = null;
        try {
            persons = db.query(FIND_PERSON_BY_NAME + name + "' ", new BeanPropertyRowMapper<Person>(Person.class));
        } catch (DataAccessException exception) {
            throw new CustomException(exception.getMessage());
        }
        return persons;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Person getPersonByHandle(String handle) throws CustomException {
        try {
            List<Person> person = db.query(FIND_PERSON_BY_HANDLE + handle + "' ", new BeanPropertyRowMapper<Person>(Person.class));
            if (person.size() == 1) {
                return person.get(0);
            }
        } catch (DataAccessException exception) {
            throw new CustomException(exception.getMessage());
        }
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Person> getFollowersOfPerson(Person p) throws CustomException {
        List<Person> followers = null;
        try {
            followers = db.query(GET_FOLLOWERS_OF_A_PERSON + p.getId(), new BeanPropertyRowMapper<Person>(Person.class));
        } catch (DataAccessException exception) {
            throw new CustomException(exception.getMessage());
        }
        return followers;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Person> getFolloweesOfPerson(Person p) throws CustomException {
        List<Person> followees = null;
        try {
            followees = db.query(GET_FOLLOWEES_OF_A_PERSON + p.getId(), new BeanPropertyRowMapper<Person>(Person.class));
        } catch (DataAccessException exception) {
            throw new CustomException(exception.getMessage());
        }
        return followees;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean followPerson(Person currentPerson, Person personToFollow) throws CustomException {
        try {
            if (isFollowing(currentPerson, personToFollow)) {
                return false;
            }
            HashMap<String, Integer> sqlParameters = new HashMap<>();
            sqlParameters.put("person_id", currentPerson.getId());
            sqlParameters.put("follower_person_id", personToFollow.getId());
            return db.update(FOLLOW_A_PERSON, sqlParameters) == 1;
        } catch (DataAccessException exception) {
            throw new CustomException(exception.getMessage());
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean unFollowPerson(Person currentPerson, Person personToUnFollow) throws CustomException {
        try {
            if (currentPerson.getId() == personToUnFollow.getId()) {
                throw new CustomException("Cannot unfollow yourself");
            }
            HashMap<String, Integer> sqlParameters = new HashMap<>();
            sqlParameters.put("person_id", currentPerson.getId());
            sqlParameters.put("follower_person_id", personToUnFollow.getId());
            return db.update(UN_FOLLOW_A_PERSON, sqlParameters) == 1;
        } catch (DataAccessException exception) {
            throw new CustomException(exception.getMessage());
        }

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isFollowing(Person currentPerson, Person followedPerson) throws CustomException {
        try {
            HashMap<String, Integer> sqlParameters = new HashMap<>();
            sqlParameters.put("currentPerson", currentPerson.getId());
            sqlParameters.put("followedPerson", followedPerson.getId());
            return db.query(IS_FOLLOWING, sqlParameters, new BeanPropertyRowMapper<Followers>(Followers.class)).size() == 1;
            //       return followersMapping.stream()
//               .anyMatch(f -> f.getFollower_person_id().equals(followedPerson.getId())
//                       && f.getPerson_id().equals(currentPerson.getId()));
        } catch (DataAccessException exception) {
            throw new CustomException(exception.getMessage());
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<PersonWithPopularFollower> personWithPopularFollower() throws CustomException {
        try {
            List<PersonWithPopularFollower> personWithPopularFollowers = db.query(GET_MOST_POPULAR_PERSON,
                    new BeanPropertyRowMapper<PersonWithPopularFollower>(PersonWithPopularFollower.class));
            return personWithPopularFollowers;
        } catch (DataAccessException exception) {
            throw new CustomException(exception.getMessage());
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int shortestDistanceBetweenTwoPersons(Person source, Person destination) throws CustomException {
        try {
            List<Map<String, Object>> followersMap = db.queryForList(MAP_FOLLOWERS_TABLE, sqlParameterSource);
            HashMap<Integer, Set<Integer>> graph = createGraph(followersMap);
            int result = BFS(graph, source.getId(), destination.getId());
//            if(result == -1){
//                throw new CustomException("There is no path between source and destination");
//            }
            return result;
        } catch (DataAccessException exception) {
            throw new CustomException(exception.getMessage());
        }

    }

    /**
     * Create the graph from the followers table.
     *
     * @param followersMap followers table as a list
     * @return graph Graph that is used to do bfs.
     */
    private HashMap<Integer, Set<Integer>> createGraph(List<Map<String, Object>> followersMap) {
        HashMap<Integer, Set<Integer>> graph = new HashMap<>();
        for (Map follower : followersMap) {
            if (graph.containsKey(((BigDecimal) follower.get("follower_person_id")).intValue())) {
                graph.get(((BigDecimal) follower.get("follower_person_id")).intValue())
                        .add(((BigDecimal) follower.get("person_id")).intValue());
            } else {
                graph.put(((BigDecimal) (follower.get("follower_person_id"))).intValue(), new HashSet<>());
                graph.get(((BigDecimal) follower.get("follower_person_id")).intValue())
                        .add(((BigDecimal) follower.get("person_id")).intValue());
            }
        }
        return graph;
    }

    /**
     * Breadth first search method, to find the shortest distance between two users.
     *
     * @param graph               Graph which has mappings of the user with other users.
     * @param sourcePersonId      Source person id
     * @param destinationPersonId Destination person id
     * @return hops least number of hops between two users. -1 if there is no path exists between two users.
     */
    private int BFS(Map<Integer, Set<Integer>> graph, int sourcePersonId, int destinationPersonId) {
        Set<Integer> visited = new HashSet<>();
        Queue<Integer> queue = new LinkedList<>();
        int hops = -1;
        queue.add(sourcePersonId);
        Queue<Integer> holder = new LinkedList<>();
        while (!queue.isEmpty()) {
            int currentPerson = queue.remove();
            if (!visited.contains(currentPerson)) {
                if (currentPerson == destinationPersonId) {
                    return 1 + hops;
                }
                visited.add(currentPerson);
                if (graph.get(currentPerson) != null && !graph.get(currentPerson).isEmpty()) {
                    holder.addAll(graph.get(currentPerson));
                }
            }
            if (queue.isEmpty()) {
                if (!holder.isEmpty()) {
                    queue.addAll(holder);
                    holder.clear();
                    hops++;
                } else {
                    break;
                }
            }
        }
        return -1;
    }

}

