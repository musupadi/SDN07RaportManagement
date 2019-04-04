package com.destinyapp.aplikasisdn07.Session;

public class User {
    private String username;
    private String person;
    public User(){

    }
    public User(String username,String person){
        this.username=username;
        this.person=person;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPerson() {
        return person;
    }

    public void setPerson(String person) {
        this.person = person;
    }
}
