package com.revanth.twitter.thousandeyes.entity;

import org.junit.After;
import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

/**
 * Test for Person Class
 * Created by Revanth on 6/1/2017.
 */

public class PersonTest {

    private Person person = new Person();

    @After
    public void tearDown() throws Exception {
        person = null;
    }

    @Test
    public void setId() throws Exception {
        person.setId(2);
        assertThat(person.getId(), is(2));
    }

    @Test
    public void setHandle() throws Exception {
        person.setHandle("batman");
        assertThat(person.getHandle(), is("batman"));
    }


    @Test
    public void setName() throws Exception {
        person.setName("Bruce Wyane");
        assertThat(person.getName(), is("Bruce Wyane"));
    }

}