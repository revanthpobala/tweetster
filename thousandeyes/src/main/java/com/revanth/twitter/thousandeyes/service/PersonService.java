package com.revanth.twitter.thousandeyes.service;

import com.revanth.twitter.thousandeyes.entity.Person;
import com.revanth.twitter.thousandeyes.entity.PersonWithPopularFollower;
import com.revanth.twitter.thousandeyes.exception.CustomException;

import java.util.List;

/**
 * Class for the Person Service.
 * Created by Revanth on 5/29/2017.
 */
public interface PersonService {
    /**
     * Get the Person based on the id.
     *
     * @param id The id of the person
     * @return The Person.
     * @throws CustomException When something is wrong with the database.
     */
    Person getPersonById(int id) throws CustomException;

    /**
     * Get the followers of the Person
     *
     * @param id The Id of the Person
     * @return A list of followers of that person.
     * @throws CustomException When something is wrong with the database.
     */
    List<Person> getFollowersOfPerson(int id) throws CustomException;

    /**
     * Get the Followees of the person based on the id.
     *
     * @param id Id of the Person
     * @return The followees of the person
     * @throws CustomException When something is wrong with the database.
     */
    List<Person> getFollowingOfPerson(int id) throws CustomException;

    /**
     * @param currentPersonId  Current person id
     * @param followerPersonId The person whom he/she want to follow.
     * @return True or False
     * @throws CustomException When something is wrong with the database.
     */
    boolean followAPerson(int currentPersonId, int followerPersonId) throws CustomException;

    /**
     * Unfollow a person
     *
     * @param currentPersonId  The current person
     * @param unFollowPersonId Person whom he want to unfollow
     * @return True or False
     * @throws CustomException When something is wrong with the database.
     */
    boolean unFollowAPerson(int currentPersonId, int unFollowPersonId) throws CustomException;

    /**
     * Method to get a list of popular followers per user.
     *
     * @return A list of popular followers per user
     * @throws CustomException When something is wrong with the database.
     */
    List<PersonWithPopularFollower> mostPopularFollowerPerUser() throws CustomException;

    /**
     * Find the Shortest distance between two users.
     *
     * @param source      Source person
     * @param destination Destination Person
     * @return Distance between two persons.
     * @throws CustomException When something is wrong with the database.
     */
    int shortestDistanceBetweenTwoPersons(int source, int destination) throws CustomException;

}
