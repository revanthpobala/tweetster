package com.revanth.twitter.thousandeyes.repository;

import com.revanth.twitter.thousandeyes.entity.Person;
import com.revanth.twitter.thousandeyes.entity.PersonWithPopularFollower;
import com.revanth.twitter.thousandeyes.exception.CustomException;

import java.util.List;

/**
 * Interface for the Person Repository
 * Created by Revanth on 5/30/2017.
 */
public interface PersonRepository {

    /**
     * Query to get all people registered in the application
     *
     * @return A list of people who are present in the people table.
     * @throws CustomException When something goes wrong
     */
    List<Person> getAllPeople() throws CustomException;

    /**
     * Query to get the person when a person id is provided.
     *
     * @param id person id whose we want to get information.
     * @return Person information
     * @throws CustomException When something goes wrong
     */
    List<Person> getPersonById(int id) throws CustomException;

    /**
     * Query to get the person based on the person name
     *
     * @param name Name of the user.
     * @return a list of person/persons with that particular name
     * @throws CustomException When something goes wrong
     */
    List<Person> getPersonByName(String name) throws CustomException;

    /**
     * Query to get the person based on the handle
     *
     * @param handle Handle of the user (Must be unique)
     * @return Person corresponding to that particular user.
     * @throws CustomException When something goes wrong
     */
    Person getPersonByHandle(String handle) throws CustomException;

    /**
     * Query to get the followers of the person.
     *
     * @param p The person whose followers we are querying for.
     * @return List of followers of that person.
     * @throws CustomException When something goes wrong
     */
    List<Person> getFollowersOfPerson(Person p) throws CustomException;

    /**
     * Query to get the followees of the person.
     *
     * @param p The person for whose followees we are querying for.
     * @return List of followees of that person.
     * @throws CustomException When something goes wrong
     */
    List<Person> getFolloweesOfPerson(Person p) throws CustomException;

    /**
     * Query to make the person follow another person.
     *
     * @param currentPerson  The current person
     * @param personToFollow The Person to be followed
     * @return True when the person is followed False otherwise.
     * @throws CustomException When there is a problem with the database or when the person wants to follow him/herself.
     */
    boolean followPerson(Person currentPerson, Person personToFollow) throws CustomException;

    /**
     * Query to make the person unfollow another person.
     *
     * @param currentPerson    The person to be followed
     * @param personToUnFollow The person to be unfollowed
     * @return True when the person is unfollowed False otherwise
     * @throws CustomException When there is a problem with the database or when the person wants to unfollow him/herself.
     */
    boolean unFollowPerson(Person currentPerson, Person personToUnFollow) throws CustomException;

    /**
     * Query to see if the person is following another person.
     *
     * @param currentPerson  The current person whom
     * @param followedPerson The person who has been followed by current person
     * @return True if person is following else False.
     * @throws CustomException When there is a problem with the database
     */
    boolean isFollowing(Person currentPerson, Person followedPerson) throws CustomException;

    /**
     * Query to get the mapping of person to his popular follower.
     *
     * @return returns a list of persons with his popular followers.
     * @throws CustomException When there is a problem with the database
     */
    List<PersonWithPopularFollower> personWithPopularFollower() throws CustomException;

    /**
     * Get the shortest distance between two persons. It uses modified BFS to perform the operation.
     *
     * @param source      source person
     * @param destination destination person
     * @return shortest distance between two persons.
     */
    int shortestDistanceBetweenTwoPersons(Person source, Person destination) throws CustomException;
}
