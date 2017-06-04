package com.revanth.twitter.thousandeyes.entity;

/**
 * Entity for the Tweet table.
 * Created by Revanth on 5/28/2017.
 */
public class Tweet {

    private int id;
    private int personId;
    private String content;

    public Tweet() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPersonId() {
        return personId;
    }

    public void setPersonId(int personId) {
        this.personId = personId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
