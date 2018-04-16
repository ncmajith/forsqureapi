package com.example.user.trackingmapusingfrosquire.Model;

import org.w3c.dom.Text;

/**
 * Created by user on 06-01-2018.
 */

public class Tb_User {

int Id;
String Name;
String Email;
String Phone;
String Username;
String Password;
String Food;
String Place;
String  Holy;
String TopPics;

    public Tb_User() {
    }

    public Tb_User(String name, String email, String phone, String username, String password,String food,String place,String Holy,String TopPics) {

        this.Name = name;
        this.Email = email;
        this.Phone = phone;
        this.Username = username;
        this.Password = password;
        this.Food=food;
        this.Place=place;
        this.Holy=Holy;
        this.TopPics=TopPics;
    }

    public String getFood(){return Food;}

    public String getHoly(){return Holy;}

    public String getTopPics(){return TopPics;}



    public String getPlace(){return Place;}

    public int getId() {
        return Id;
    }

    public String getName() {
        return Name;
    }

    public String getEmail() {
        return Email;
    }

    public String getPhone() {
        return Phone;
    }

    public String getUsername() {
        return Username;
    }

    public String getPassword() {
        return Password;
    }

    public void setId(int id) { Id = id; }

    public void setName(String name) {
        Name = name;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public void setFood(String food) { Food = food; }

    public void setPlace(String place) { Place = place;}

    public void setHoly(String Holy) { Holy = Holy;}

    public void setTopPics(String TopPics) { TopPics = TopPics;}
}
