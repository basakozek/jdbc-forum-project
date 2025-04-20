package org.basak.JDBC_Forum.entity;

import java.time.LocalDateTime;

public class Post {
    private long id;
    private long userId;
    private String title;
    private String content;
    private LocalDateTime postedDate;

    //tablodan gelen post kaydını tutmak için constructor:
    public Post(long id, long userId, String title, String content, LocalDateTime postedDate) {
        this.id = id;
        this.userId = userId;
        this.title = title;
        this.content = content;
        this.postedDate = postedDate;
    }
    //tabloya eklenecek verilerden oluşacak post için:
    public Post(long userId, String title, String content) {
        this.userId = userId;
        this.title = title;
        this.content = content;
    }

    public Post() {
    }

    public long getId() {
        return id;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
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

    public LocalDateTime getPostedDate() {
        return postedDate;
    }


    @Override
    public String toString() {
        return "Post{" +
                "id=" + id +
                ", userId=" + userId +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", postedDate=" + postedDate +
                '}';
    }
}
