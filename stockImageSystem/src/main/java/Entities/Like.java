package Entities;

public class Like {
    private int personId;
    private int imageId;

    public Like(int personId, int imageId) {
        this.personId = personId;
        this.imageId = imageId;
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

    @Override
    public String toString() {
        return "\nLike{" +
                "personId=" + personId +
                ", imageId=" + imageId +
                '}';
    }
}
