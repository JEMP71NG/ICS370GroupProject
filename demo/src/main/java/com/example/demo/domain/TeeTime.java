package com.example.demo.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class TeeTime {
    private LocalDateTime teeTime;
    private int courseID;
    private ArrayList<member> members;

    public TeeTime(LocalDateTime teeTime, int courseID, ArrayList<member> members) {
        this.teeTime = teeTime;
        this.courseID = courseID;
        this.members = members;
    }

    public LocalDateTime getTeeTime() {
        return teeTime;
    }

    public int getCourseID() {
        return courseID;
    }

    public ArrayList<member> getMembers() {
        return members;
    }

    public boolean reserve(member newMember) {
        if (this.members.size() < 4) {  // Maximum of 4 members per tee time
            this.members.add(newMember);
            return true;
        }
        return false;
    }

    public boolean cancelReservation(member existingMember) {
        return this.members.remove(existingMember);
    }
}
