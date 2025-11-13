package com.notapp.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "notes")
public class Note {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    @Column(nullable = false)
    private String title;
    
    @Column(length = 1500)
    private String content;
    
    @Temporal(TemporalType.TIMESTAMP)
    private Date addedDate;
    
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    
    public Note() {
        this.addedDate = new Date();
    }
    
    public Note(String title, String content, User user) {
        this.title = title;
        this.content = content;
        this.user = user;
        this.addedDate = new Date();
    }
    
    // Getters and Setters
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public String getTitle() {
        return title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    
    public String getContent() {
        return content;
    }
    
    public void setContent(String content) {
        this.content = content;
    }
    
    public Date getAddedDate() {
        return addedDate;
    }
    
    public void setAddedDate(Date addedDate) {
        this.addedDate = addedDate;
    }
    
    public User getUser() {
        return user;
    }
    
    public void setUser(User user) {
        this.user = user;
    }
}