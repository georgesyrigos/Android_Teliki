package com.example.android_teliki_v2;

public class Event {
    private String Event;
    private String Username;
    private String Comment;

    public Event(){
        //empty constractor needed
    }

    public Event(String eventType,String username, String comment){
        this.Event = Event;
        this.Username = Username;
        this.Comment = Comment;
    }

    public String getEvent() {
        return Event;
    }

    public String getUsername() {
        return Username;
    }

    public String getComment() {
        return Comment;
    }
}
