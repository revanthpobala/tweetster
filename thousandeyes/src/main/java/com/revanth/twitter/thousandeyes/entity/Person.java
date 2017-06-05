package com.revanth.twitter.thousandeyes.entity;

/**
 * Entity class for the Person table
 * Created by Revanth on 5/30/2017.
 */

public class Person {

    public void setId(int id) {
        this.id = id;
    }

    private int id;

    private String name;

    private String handle;

    public Person() {
    }

    public Person(int id) {
        this.id = id;
    }


    public Person(int id, String name, String handle) {
        this.id = id;
        this.name = name;
        this.handle = handle;
    }


    public String getHandle() {
        return handle;
    }

    public void setHandle(String handle) {
        this.handle = handle;
    }


    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
