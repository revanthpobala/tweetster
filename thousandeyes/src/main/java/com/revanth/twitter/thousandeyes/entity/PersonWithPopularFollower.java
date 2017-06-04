package com.revanth.twitter.thousandeyes.entity;

/**
 * Class to handle the Person with Popular Follower
 * Created by Revanth on 5/30/2017.
 */
public class PersonWithPopularFollower {


    private int person_id;
    private int follower_person_id;
    private int cnt;


    public PersonWithPopularFollower() {
    }

    public PersonWithPopularFollower(int person_id, int follower_person_id, int cnt) {
        this.person_id = person_id;
        this.follower_person_id = follower_person_id;
        this.cnt = cnt;
    }


    public int getCnt() {
        return cnt;
    }

    public void setCnt(int cnt) {
        this.cnt = cnt;
    }

    public int getPerson_id() {
        return person_id;
    }

    public void setPerson_id(int person_id) {
        this.person_id = person_id;
    }

    public int getFollower_person_id() {
        return follower_person_id;
    }

    public void setFollower_person_id(int follower_person_id) {
        this.follower_person_id = follower_person_id;
    }


}
