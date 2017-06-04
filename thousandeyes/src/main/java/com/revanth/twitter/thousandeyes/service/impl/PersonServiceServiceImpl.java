package com.revanth.twitter.thousandeyes.service.impl;

import com.revanth.twitter.thousandeyes.entity.Person;
import com.revanth.twitter.thousandeyes.entity.PersonWithPopularFollower;
import com.revanth.twitter.thousandeyes.exception.CustomException;
import com.revanth.twitter.thousandeyes.repository.PersonRepository;
import com.revanth.twitter.thousandeyes.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Created by Revanth on 5/29/2017.
 */
@Service
public class PersonServiceServiceImpl implements PersonService {

    @Autowired
    private PersonRepository personRepository;

    /**
     * {@inheritDoc}
     */
    @Override
    public Person getPersonById(@NotNull int id) throws CustomException {
        List<Person> p = personRepository.getPersonById(id);
        if (p.size() != 1) {
            return null;
        }
        return p.get(0);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Person> getFollowersOfPerson(@NotNull int id) throws CustomException {
        Person p = getPersonById(id);
        return personRepository.getFollowersOfPerson(p);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Person> getFollowingOfPerson(@NotNull int id) throws CustomException {
        Person p = getPersonById(id);
        return personRepository.getFolloweesOfPerson(p);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean followAPerson(@NotNull int currentPersonId, @NotNull int followerPersonId) throws CustomException {
        Person currentPerson = getPersonById(currentPersonId);
        Person followPerson = getPersonById(followerPersonId);
        return personRepository.followPerson(currentPerson, followPerson);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean unFollowAPerson(@NotNull int currentPersonId, @NotNull int unFollowPersonId) throws CustomException {
        Person currentPerson = getPersonById(currentPersonId);
        Person followPerson = getPersonById(unFollowPersonId);
        return personRepository.unFollowPerson(currentPerson, followPerson);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<PersonWithPopularFollower> mostPopularFollowerPerUser() throws CustomException {
        return personRepository.personWithPopularFollower();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int shortestDistanceBetweenTwoPersons(@NotNull int source, @NotNull int destination) throws CustomException {
        Person sourcePerson = getPersonById(source);
        Person destinationPerson = getPersonById(destination);
        return personRepository.shortestDistanceBetweenTwoPersons(sourcePerson, destinationPerson);
    }

}
