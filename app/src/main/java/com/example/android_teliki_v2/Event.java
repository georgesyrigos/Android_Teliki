package com.example.android_teliki_v2;

import android.health.connect.datatypes.ExerciseRoute;

import java.sql.Timestamp;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class Event implements Parcelable {
    private String Event;
    private String Username;
    private String Comment;
    private String Location;
    private String Timestamp;
    private Integer Points;
    private String Situation;


    public Event(){
        //empty constractor needed
    }

    public Event(String eventType,String username, String comment, String location, String timestamp, Integer points, String situation){
        this.Event = Event;
        this.Username = Username;
        this.Comment = Comment;
        this.Location = Location;
        this.Timestamp = Timestamp;
        this.Points = Points;
        this.Situation = Situation;


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

    public String getLocation() {
        return Location;
    }

    public String getTimestamp() {
        return Timestamp;
    }

    public Integer getPoints() {
        return Points;
    }

    public String getSituation() {
        return Situation;
    }

    protected Event(Parcel in) {
        Event = in.readString();
        Username = in.readString();
        Comment = in.readString();
        Location = in.readString();
        Timestamp = in.readString();
        if (in.readByte() == 0) {
            Points = null;
        } else {
            Points = in.readInt();
        }
        Situation = in.readString();
    }

    public static final Creator<Event> CREATOR = new Creator<Event>() {
        @Override
        public Event createFromParcel(Parcel in) {
            return new Event(in);
        }

        @Override
        public Event[] newArray(int size) {
            return new Event[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(Event);
        dest.writeString(Username);
        dest.writeString(Comment);
        dest.writeString(Location);
        dest.writeString(Timestamp);
        if (Points == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(Points);
        }
        dest.writeString(Situation);
    }
}
