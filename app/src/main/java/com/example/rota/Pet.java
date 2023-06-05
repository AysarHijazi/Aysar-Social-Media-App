package com.example.rota;
public class Pet {
    private String name;
    private String phone;
    private String price;
    private String imageUrl;

    public Pet() {
        // مطلوب للتعامل مع Firebase Database
    }

    public Pet(String name, String phone, String price, String imageUrl) {
        this.name = name;
        this.phone = phone;
        this.price = price;
        this.imageUrl = imageUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
