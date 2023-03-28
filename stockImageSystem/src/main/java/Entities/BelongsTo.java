package Entities;

public class BelongsTo {
    private int categoryId;
    private int imageId;

    public BelongsTo(int categoryId, int imageId) {
        this.categoryId = categoryId;
        this.imageId = imageId;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    @Override
    public String toString() {
        return "\nBelongsTo{" +
                "categoryId=" + categoryId +
                ", imageId=" + imageId +
                '}';
    }
}
