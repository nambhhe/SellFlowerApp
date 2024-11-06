// app/src/main/java/sellFlower/app/model/CartItem.java
package sellFlower.app.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "cart_items")
public class CartItem {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private int flowerId;
    private String flowerName;
    private double price;
    private int quantity;
    private String imageUrl;

    public CartItem(int flowerId, String flowerName, double price, int quantity, String imageUrl) {
        this.flowerId = flowerId;
        this.flowerName = flowerName;
        this.price = price;
        this.quantity = quantity;
        this.imageUrl = imageUrl;
    }

    // Getters and setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getFlowerId() { return flowerId; }
    public void setFlowerId(int flowerId) { this.flowerId = flowerId; }
    public String getFlowerName() { return flowerName; }
    public void setFlowerName(String flowerName) { this.flowerName = flowerName; }
    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }
    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
}