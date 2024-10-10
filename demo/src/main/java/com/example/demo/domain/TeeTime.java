package com.example.demo.domain;

import java.util.ArrayList;

public class TeeTime {

    public int time;
    public int courseID;
    public ArrayList<member> members;

    public TeeTime(int time, int courseID, ArrayList<member> members){
        this.time = time;
        this.courseID = courseID;
        this.members = members;
    }

    public int getTime(){
        return this.time;
    }
    public int getCourseID(){
        return this.courseID;
    }
    public ArrayList<member> getMembers(){
        return this.members;
    }
}
