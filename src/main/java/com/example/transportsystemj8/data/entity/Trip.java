package com.example.transportsystemj8.data.entity;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;
import java.util.Set;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Table(name = "Trip")
@Entity
public class Trip implements Serializable {
    private static final long serialVersionUID=1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tripId", nullable = false)
    private Integer tripId;

    @Column(name = "departure", nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate departure;

    @Column(name = "arrival", nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate arrival;

    @Column(name = "capacity", nullable = false)
    private int capacity;

    @ManyToOne
    @JoinColumn(name = "tripTypeId", nullable = false)
    private TripType tripTypeId;

    @ManyToOne
    @JoinColumn(name = "transportTypeId", nullable = false)
    private TransportType transportTypeId;

    @ManyToOne
    @JoinColumn(name = "companyId", nullable = false)
    private Company companyId;

    @ManyToOne
    @JoinColumn(name = "locationFromId", nullable = false)
    private Location locationFrom;

    @ManyToOne
    @JoinColumn(name = "locationToId", nullable = false)
    private Location locationTo;

    @Column(name = "timeOfDeparture", nullable = false)
    private String timeOfDeparture;

    @Column(name = "timeOfArrival", nullable = false)
    private String timeOfArrival;

    @Column(name = "price", nullable = false)
    private Double price;

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    @OneToMany(mappedBy = "tripId")
    Set<Request> requestSet1;

    @OneToMany(mappedBy = "tripId")
    Set<Ticket> ticketSet1;

    public Integer getTripId() {
        return tripId;
    }

    public void setTripId(Integer tripId) {
        this.tripId = tripId;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public TripType getTripTypeId() {
        return tripTypeId;
    }

    public void setTripTypeId(TripType tripTypeId) {
        this.tripTypeId = tripTypeId;
    }

    public TransportType getTransportTypeId() {
        return transportTypeId;
    }

    public void setTransportTypeId(TransportType transportTypeId) {
        this.transportTypeId = transportTypeId;
    }

    public Company getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Company companyId) {
        this.companyId = companyId;
    }

    public Location getLocationFrom() {
        return locationFrom;
    }

    public void setLocationFrom(Location locationFrom) {
        this.locationFrom = locationFrom;
    }

    public Location getLocationTo() {
        return locationTo;
    }

    public void setLocationTo(Location locationTo) {
        this.locationTo = locationTo;
    }

    public LocalDate getDeparture() {
        return departure;
    }

    public void setDeparture(LocalDate departure) {
        this.departure = departure;
    }

    public LocalDate getArrival() {
        return arrival;
    }

    public void setArrival(LocalDate arrival) {
        this.arrival = arrival;
    }

    public String getTimeOfDeparture() {
        return timeOfDeparture;
    }

    public void setTimeOfDeparture(String timeOfDeparture) {
        this.timeOfDeparture = timeOfDeparture;
    }

    public String getTimeOfArrival() {
        return timeOfArrival;
    }

    public void setTimeOfArrival(String timeOfArrival) {
        this.timeOfArrival = timeOfArrival;
    }

    public Set<Request> getRequestSet1() {
        return requestSet1;
    }

    public void setRequestSet1(Set<Request> requestSet1) {
        this.requestSet1 = requestSet1;
    }

    public Set<Ticket> getTicketSet1() {
        return ticketSet1;
    }

    public void setTicketSet1(Set<Ticket> ticketSet1) {
        this.ticketSet1 = ticketSet1;
    }

    @Override
    public String toString() {
        return "Trip{" +
                "tripId=" + tripId +
                ", departure=" + departure +
                ", arrival=" + arrival +
                ", capacity=" + capacity +
                ", tripTypeId=" + tripTypeId +
                ", transportTypeId=" + transportTypeId +
                ", companyId=" + companyId +
                ", locationFrom=" + locationFrom +
                ", locationTo=" + locationTo +
                ", timeOfDeparture='" + timeOfDeparture + '\'' +
                ", timeOfArrival='" + timeOfArrival + '\'' +
                //", requestSet1=" + requestSet1 +
                //", ticketSet1=" + ticketSet1 +
                '}';
    }

    public Trip() {
    }
    public Trip(int Capacity){

    }

    public Trip(Integer tripId, LocalDate departure, LocalDate arrival, int capacity, TripType tripTypeId, TransportType transportTypeId, Company companyId, Location locationFrom, Location locationTo, String timeOfDeparture, String timeOfArrival, Double price) {
        this.tripId = tripId;
        this.departure = departure;
        this.arrival = arrival;
        this.capacity = capacity;
        this.tripTypeId = tripTypeId;
        this.transportTypeId = transportTypeId;
        this.companyId = companyId;
        this.locationFrom = locationFrom;
        this.locationTo = locationTo;
        this.timeOfDeparture = timeOfDeparture;
        this.timeOfArrival = timeOfArrival;
        this.price = price;
    }

    public Trip(LocalDate departure, LocalDate arrival, int capacity, TripType tripTypeId, TransportType transportTypeId, Company companyId, Location locationFrom, Location locationTo, String timeOfDeparture, String timeOfArrival) {
        this.departure = departure;
        this.arrival = arrival;
        this.capacity = capacity;
        this.tripTypeId = tripTypeId;
        this.transportTypeId = transportTypeId;
        this.companyId = companyId;
        this.locationFrom = locationFrom;
        this.locationTo = locationTo;
        this.timeOfDeparture = timeOfDeparture;
        this.timeOfArrival = timeOfArrival;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Trip trip = (Trip) o;
        return capacity == trip.capacity && Objects.equals(departure, trip.departure) && Objects.equals(arrival, trip.arrival) && Objects.equals(tripTypeId, trip.tripTypeId) && Objects.equals(transportTypeId, trip.transportTypeId) && Objects.equals(companyId, trip.companyId) && Objects.equals(locationFrom, trip.locationFrom) && Objects.equals(locationTo, trip.locationTo) && Objects.equals(timeOfDeparture, trip.timeOfDeparture) && Objects.equals(timeOfArrival, trip.timeOfArrival);
    }

    @Override
    public int hashCode() {
        return Objects.hash(departure, arrival, capacity, tripTypeId, transportTypeId, companyId, locationFrom, locationTo, timeOfDeparture, timeOfArrival);
    }
}
