package com.example.demo.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TeeTimeScheduler {
    private final Map<String, List<TeeTime>> schedule = new HashMap<>();

    public TeeTimeScheduler() {
        initializeScheduler();
    }

    private void initializeScheduler() {
        String[] daysOfWeek = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};
        for (String day : daysOfWeek) {
            List<TeeTime> teeTimesForDay = new ArrayList<>();
            for (int hour = 7; hour <= 17; hour++) {
                for (int minute = 0; minute < 60; minute += 10) {
                    String time = String.format("%02d:%02d", hour, minute);
                    teeTimesForDay.add(new TeeTime(time));
                }
            }
            schedule.put(day, teeTimesForDay);
        }
    }

    public List<String> getTeeTimesForDay(String dayOfWeek) {
        List<String> teeTimeStrings = new ArrayList<>();
        for (TeeTime teeTime : schedule.get(dayOfWeek)) {
            teeTimeStrings.add(teeTime.getTime() + " (" + teeTime.getPlayers().size() + "/4 booked)");
        }
        return teeTimeStrings;
    }

    public boolean bookTeeTime(String dayOfWeek, String time, String playerName) {
        List<TeeTime> teeTimes = schedule.get(dayOfWeek);
        for (TeeTime teeTime : teeTimes) {
            if (teeTime.getTime().equals(time) && !teeTime.isFull()) {
                teeTime.addPlayer(playerName);
                return true;
            }
        }
        return false;
    }

    public boolean cancelBooking(String dayOfWeek, String time, String playerName) {
        List<TeeTime> teeTimes = schedule.get(dayOfWeek);
        for (TeeTime teeTime : teeTimes) {
            if (teeTime.getTime().equals(time)) {
                return teeTime.removePlayer(playerName);
            }
        }
        return false;
    }
}
