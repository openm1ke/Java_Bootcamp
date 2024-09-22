package edu.school21.models;

public class Product {
    private int identifier;
    private String name;
    private double price;

    public Product(int identifier, String name, double price) {
        this.identifier = identifier;
        this.name = name;
        this.price = price;
    }

    @Override
    public String toString() {
        return String.format("ID: %d, Name: %s, Price: %.2f", identifier, name, price);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Product product = (Product) obj;
        return identifier == product.identifier && name.equals(product.name) && price == product.price;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(identifier) + name.hashCode() + Double.hashCode(price);
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public long getIdentifier() {
        return identifier;
    }

    public void setName(String s) {
        name = s;
    }

    public void setPrice(double v) {
        price = v;
    }
}
