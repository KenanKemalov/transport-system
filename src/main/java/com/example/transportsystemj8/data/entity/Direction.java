package com.example.transportsystemj8.data.entity;

import java.util.Objects;

public class Direction {
    private String locationFrom;
    private String locationTo;

    private int soldTickets;


    public Direction(String locationFrom, String locationTo, int soldTickets) {
        this.locationFrom = locationFrom;
        this.locationTo = locationTo;
        this.soldTickets = soldTickets;
    }

    public Direction() {
    }

    public String getLocationFrom() {
        return locationFrom;
    }

    public void setLocationFrom(String locationFrom) {
        this.locationFrom = locationFrom;
    }

    public String getLocationTo() {
        return locationTo;
    }

    public void setLocationTo(String locationTo) {
        this.locationTo = locationTo;
    }

    public int getSoldTickets() {
        return soldTickets;
    }

    public void setSoldTickets(int soldTickets) {
        this.soldTickets = soldTickets;
    }

    @Override
    public String toString() {
        return "Direction{" +
                "locationFrom='" + locationFrom + '\'' +
                ", locationTo='" + locationTo + '\'' +
                ", soldTickets=" + soldTickets +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Direction direction = (Direction) o;
        return Objects.equals(locationFrom, direction.locationFrom) && Objects.equals(locationTo, direction.locationTo);
    }

}
