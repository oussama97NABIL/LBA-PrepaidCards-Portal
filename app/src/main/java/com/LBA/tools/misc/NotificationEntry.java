package com.LBA.tools.misc;

import java.util.Objects;

public class NotificationEntry {

    String id ;
    String title ;
    String body;
    String date;
    String user;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public NotificationEntry(String title, String body, String date) {
        this.title = title;
        this.body = body;
        this.date = date;
    }


    public NotificationEntry(String id, String title, String body , String date) {
        this.id = id;
        this.title = title;
        this.body = body;
        this.date = date;
    }
    public NotificationEntry(String id, String title, String body , String date , String user) {
        this.id = id;
        this.title = title;
        this.body = body;
        this.date = date;
        this.user = user;
    }

    public NotificationEntry(String title, String body) {
        this.title = title;
        this.body = body;
    }


    @Override
    public String toString() {
        return "NotificationEntry{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", body='" + body + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NotificationEntry that = (NotificationEntry) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(title, that.title) &&
                Objects.equals(body, that.body) &&
                Objects.equals(date, that.date) &&
                Objects.equals(user, that.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, body, date, user);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }
}
