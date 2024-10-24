package com.example.demo.domain;

public class RentalClubs {

    public int clubsID;
    public int courseID;
    public boolean isReserved;
    public int rentedTime;

//constructor
    public RentalClubs (int clubsID, int time, int courseID, boolean reserved){

        this.rentedTime = time;
        this.courseID = courseID;
        this.isReserved = reserved;
        this.clubsID = clubsID;
    }
    //getters and setters
    public int getClubsID(){
        return this.clubsID;
    }
    public void setClubsID(int clubsID){
        this.clubsID = clubsID;
    }
    public int getClubsCourseID(){
        return this.courseID;
    }
    public void setClubsCourseID(int courseID){
        this.courseID = courseID;
    }
    public boolean isReserved(){
        return this.isReserved;
    }
    public void setIsReserved(boolean isReserved){
        this.isReserved = isReserved;
    }
    public int getRentedTime(){
        return this.rentedTime;
    }
    public void setRentedTime(int time){
        this.rentedTime = time;
    }





}
