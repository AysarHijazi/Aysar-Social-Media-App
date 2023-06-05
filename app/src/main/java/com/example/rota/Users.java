package com.example.rota;

public class Users {


    String username, imageURL,Jop,Gender, id, status ,Age ,favoriteteam,favoritefood;

    public Users(String username, String imageURL, String id, String status) {
        this.username = username;
        this.Age = Age;
        this.Jop= Jop;
        this.Gender= Gender;

        this.favoriteteam = favoriteteam;
        this.favoritefood = favoritefood;



        this.imageURL = imageURL;
        this.id = id;
        this.status = status;
    }

    public Users() {
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


    public String getAge() {
        return Age;
    }

    public void setAge(String Age) {
        this.Age = Age;
    }



    public String getJop() {
        return Jop;
    }

    public void setJop(String Jop) {
        this.Jop = Jop;
    }





    public String getGender() {
        return Gender;
    }

    public void setGender(String Gender) {
        this.Gender = Gender;
    }



    public String getfavoriteteam() {
        return favoriteteam;
    }

    public void setfavoriteteam(String favoriteteam) {
        this.favoriteteam = favoriteteam;
    }



    public String getfavoritefood() {
        return favoritefood;
    }

    public void setfavoritefood(String favoritefood) {
        this.favoritefood = favoritefood;
    }



    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
