package com.example.transportsystemj8.data.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;
import java.util.Set;

@Table(name = "Location")
@Entity
public class Location implements Serializable {
    private static final long serialVersionUID=1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "locationId", nullable = false)
    private Integer locationId;

    @Column(name = "locationName", nullable = false)
    private String locationName;

    @OneToMany(mappedBy = "locationFrom")
    Set<Trip> tripSet1;

    @OneToMany(mappedBy = "locationTo")
    Set<Trip> tripSet2;

    public Location(String locationName) {
        this.locationName = locationName;
    }

    public Location() {

    }

    @Override
    public String toString() {
        return "Location{" +
                "locationId=" + locationId +
                ", locationName='" + locationName + '\'' +
                //", tripSet1=" + tripSet1 +
                //", tripSet2=" + tripSet2 +
                '}';
    }

    public Integer getLocationId() {
        return locationId;
    }

    public void setLocationId(Integer locationId) {
        this.locationId = locationId;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public Set<Trip> getTripSet1() {
        return tripSet1;
    }

    public void setTripSet1(Set<Trip> tripSet1) {
        this.tripSet1 = tripSet1;
    }

    public Set<Trip> getTripSet2() {
        return tripSet2;
    }

    public void setTripSet2(Set<Trip> tripSet2) {
        this.tripSet2 = tripSet2;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Location location = (Location) o;
        return Objects.equals(locationName, location.locationName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(locationName);
    }
}