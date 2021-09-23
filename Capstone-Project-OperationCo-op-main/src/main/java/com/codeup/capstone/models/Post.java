package com.codeup.capstone.models;


import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="post")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private Date date;

//    @Column(nullable = false, columnDefinition = "LONGTEXT")
//    private String parent_post_id;

    @Column(nullable = false, columnDefinition = "LONGTEXT")
    private String message_body;

    //Groups to User will be many-to-one because many messages can be posted by one group
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn (name = "group_id")
    private Group group;

    //Messages to User will be many-to-one because many messages can be posted by one group
    @ManyToOne
    @JsonManagedReference
    @JoinColumn (name = "user_id")
    private User user;

    //  ----------- Constructors
    public Post() {
    }

    public Post(long id, Date date, String message_body, Group group, User user) {
        this.id = id;
        this.date = date;
        this.message_body = message_body;
        this.group = group;
        this.user = user;
    }

    //-----getters and setters
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getMessage_body() {
        return message_body;
    }

    public void setMessage_body(String message_body) {
        this.message_body = message_body;
    }

}