package com.example.stockimagesystem;

import javafx.beans.property.SimpleStringProperty;

import java.sql.Date;


public class TableData {
    private final SimpleStringProperty  imageName;
    private final SimpleStringProperty  URL;
    private final SimpleStringProperty  resolution;
    private final SimpleStringProperty  publishedBy;
    private final SimpleStringProperty  publishDate;
    private final SimpleStringProperty  category;
    private final SimpleStringProperty  comments;
    private final SimpleStringProperty  like;
    private final SimpleStringProperty  id;

    public TableData(String imageName, String URL, String resolution, String publishedBy, Date publishDate, String category, int comments, int like, int id) {
        this.imageName = new SimpleStringProperty(imageName);
        this.URL = new SimpleStringProperty(URL);
        this.resolution = new SimpleStringProperty(resolution);
        this.publishedBy = new SimpleStringProperty(publishedBy);
        this.publishDate = new SimpleStringProperty(publishDate.toString());
        this.category = new SimpleStringProperty(category);
        this.comments = new SimpleStringProperty(String.valueOf(comments));
        this.like = new SimpleStringProperty(String.valueOf(like));
        this.id = new SimpleStringProperty(String.valueOf(id));
    }

    public String getImageName() {
        return imageName.get();
    }

    public SimpleStringProperty imageNameProperty() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName.set(imageName);
    }

    public String getURL() {
        return URL.get();
    }

    public SimpleStringProperty URLProperty() {
        return URL;
    }

    public void setURL(String URL) {
        this.URL.set(URL);
    }

    public String getResolution() {
        return resolution.get();
    }

    public SimpleStringProperty resolutionProperty() {
        return resolution;
    }

    public void setResolution(String resolution) {
        this.resolution.set(resolution);
    }

    public String getPublishedBy() {
        return publishedBy.get();
    }

    public SimpleStringProperty publishedByProperty() {
        return publishedBy;
    }

    public void setPublishedBy(String publishedBy) {
        this.publishedBy.set(publishedBy);
    }

    public String getPublishDate() {
        return publishDate.get();
    }

    public SimpleStringProperty publishDateProperty() {
        return publishDate;
    }

    public void setPublishDate(String publishDate) {
        this.publishDate.set(publishDate);
    }

    public String getCategory() {
        return category.get();
    }

    public SimpleStringProperty categoryProperty() {
        return category;
    }

    public void setCategory(String category) {
        this.category.set(category);
    }

    public String getComments() {
        return comments.get();
    }

    public SimpleStringProperty commentsProperty() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments.set(comments);
    }

    public String getLike() {
        return like.get();
    }

    public SimpleStringProperty likeProperty() {
        return like;
    }

    public void setLike(String like) {
        this.like.set(like);
    }

    public String getId() {
        return id.get();
    }

    public SimpleStringProperty idProperty() {
        return id;
    }

    public void setId(String id) {
        this.id.set(id);
    }

    @Override
    public String toString() {
        return "\nTableData{" +
                "imageName='" + imageName + '\'' +
                ", URL='" + URL + '\'' +
                ", resolution='" + resolution + '\'' +
                ", publishedBy='" + publishedBy + '\'' +
                ", publishDate=" + publishDate +
                ", category='" + category + '\'' +
                ", comments=" + comments +
                ", like=" + like +
                ", id=" + id +
                '}';
    }
}
