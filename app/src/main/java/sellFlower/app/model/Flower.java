package sellFlower.app.model;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "flower")
public class Flower {
    @PrimaryKey(autoGenerate = true)
    private int id;

    private String name;
    private String description;
    private double price;
    private String imageUrl;
    private int categoryId;
    private int stock;
    private float rating;
    private String type;
    private boolean isFeatured;
    private String occasion;

    public Flower() {
    }

    @Ignore
    public Flower(int id, String name, String description, double price, String imageUrl, int categoryId, int stock, float rating, String type, String occasion) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.imageUrl = imageUrl;
        this.categoryId = categoryId;
        this.stock = stock;
        this.rating = rating;
        this.type = type;
        this.occasion = occasion;
    }

    public Flower(int id, String name, String description, double price, String imageUrl, int stock, float rating, String type, boolean isFeatured, String occasion) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.imageUrl = imageUrl;
        this.stock = stock;
        this.rating = rating;
        this.type = type;
        this.isFeatured = isFeatured;
        this.occasion = occasion;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isFeatured() {
        return isFeatured;
    }

    public void setFeatured(boolean featured) {
        isFeatured = featured;
    }

    public String getOccasion() {
        return occasion;
    }

    public void setOccasion(String occasion) {
        this.occasion = occasion;
    }
}
