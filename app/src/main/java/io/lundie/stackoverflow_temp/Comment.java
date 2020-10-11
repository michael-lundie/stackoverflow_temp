package io.lundie.stackoverflow_temp;

import com.google.firebase.firestore.ServerTimestamp;

import java.util.Date;

public class Comment {

    String comment;
    @ServerTimestamp Date timestamp;

    // A zero argument constructor is required by firestore.
    public Comment() {
    }

    public Comment(String comment) {
        this.comment = comment;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }
}
