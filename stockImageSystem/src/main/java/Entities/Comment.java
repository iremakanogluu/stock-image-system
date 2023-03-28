package Entities;

import java.util.ArrayList;

public class Comment {
    private int personId;
    private int imageId;
    private String commentText;

    public Comment(int personId, int imageId, String commentText) {
        this.personId = personId;
        this.imageId = imageId;
        this.commentText = commentText;
    }

    public int getPersonId() {
        return personId;
    }

    public void setPersonId(int personId) {
        this.personId = personId;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public String getCommentText() {
        return commentText;
    }

    public void setCommentText(String commentText) {
        this.commentText = commentText;
    }

    @Override
    public String toString() {
        return "\nComment{" +
                "personId=" + personId +
                ", imageId=" + imageId +
                ", commentText='" + commentText + '\'' +
                '}';
    }


}
