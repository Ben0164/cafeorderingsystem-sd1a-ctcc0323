package Acosta_menu;

public class Drink {
    private String name;
    private String imagePath; // New field for image path
    private double price;
    private String category;

    public Drink(String name, String imagePath, double price, String category) {
        this.name = name;
        this.imagePath = imagePath; // Initialize imagePath
        this.price = price;
        this.category = category;
    }

    // Getters and setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
