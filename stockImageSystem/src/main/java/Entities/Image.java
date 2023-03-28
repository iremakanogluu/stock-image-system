package Entities;

import java.sql.Date;

public class Image {
    private int ImageId;
    private String ImageName;
    private String URL;
    private int Width;
    private int Height;
    private int PersonId;
    private Date publishDate;

    public Image(int imageId, String imageName, String URL, int width, int height, int personId, Date publishDate) {
        ImageId = imageId;
        ImageName = imageName;
        this.URL = URL;
        Width = width;
        Height = height;
        PersonId = personId;
        this.publishDate = publishDate;
    }

    public int getImageId() {
        return ImageId;
    }

    public void setImageId(int imageId) {
        ImageId = imageId;
    }

    public String getImageName() {
        return ImageName;
    }

    public void setImageName(String imageName) {
        ImageName = imageName;
    }

    public String getURL() {
        return URL;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }

    public int getWidth() {
        return Width;
    }

    public void setWidth(int width) {
        Width = width;
    }

    public int getHeight() {
        return Height;
    }

    public void setHeight(int height) {
        Height = height;
    }

    public int getPersonId() {
        return PersonId;
    }

    public void setPersonId(int personId) {
        PersonId = personId;
    }

    public Date getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(Date publishDate) {
        this.publishDate = publishDate;
    }

    @Override
    public String toString() {
        return "\nImage{" +
                "ImageId=" + ImageId +
                ", ImageName='" + ImageName + '\'' +
                ", URL='" + URL + '\'' +
                ", Width=" + Width +
                ", Height=" + Height +
                ", PersonId=" + PersonId +
                ", publishDate=" + publishDate +
                '}';
    }
}
