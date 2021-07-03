package com.example.project1;

public class PhoneBook {
    private int image;
    private String name;
    private String num;

    PhoneBook(Integer image, String name, String num)
    {
        this.image = image;
        this.name = name;
        this.num = num;
    }
    PhoneBook(String name, String num)
    {
        this.name = name;
        this.num = num;
    }

    public int getImage(){return image;}
    public String getName(){return name;}
    public String getNum(){return num;}

    public void setImage(int image) {
        this.image = image;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNum(String num) {
        this.num = num;
    }
}
