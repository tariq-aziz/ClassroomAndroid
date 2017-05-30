package com.aziz.tariq.classroom;

public class Message {
    private String text;
    private String author;

    public Message(){

    }
    public Message(String text, String author){
        this.text = text;
        this.author = author;
    }

    public String getText(){
        return text;
    }
    public String getAuthor(){
        return author;
    }
}
