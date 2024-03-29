package com.example.transportsystemj8.data.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;
import java.util.Set;
import java.time.LocalDate;

@Table(name = "Ticket")
@Entity
public class Ticket implements Serializable {
    private static final long serialVersionUID=1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ticketId", nullable = false)
    private Integer ticketId;

    @Column(name = "seatNumber", nullable = false)
    private int seatNumber;

    @Column(name = "customerName", nullable = false)
    private String customerName;

    @Column(name = "purchaseDate", nullable = false)
    private LocalDate purchaseDate;

    @ManyToOne
    @JoinColumn(name = "cashierId")
    private Cashier cashierId;

    @ManyToOne
    @JoinColumn(name = "tripId", nullable = false)
    private Trip tripId;

    @Override
    public String toString() {
        return "Ticket{" +
                "ticketId=" + ticketId +
                ", seatNumber=" + seatNumber +
                ", customerName='" + customerName + '\'' +
                ", purchaseDate=" + purchaseDate +
                ", cashierId=" + cashierId +
                ", tripId=" + tripId +
                '}';
    }

    public Integer getTicketId() {
        return ticketId;
    }

    public void setTicketId(Integer ticketId) {
        this.ticketId = ticketId;
    }

    public int getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(int seatNumber) {
        this.seatNumber = seatNumber;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public LocalDate getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(LocalDate purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public Cashier getCashierId() {
        return cashierId;
    }

    public void setCashierId(Cashier cashierId) {
        this.cashierId = cashierId;
    }

    public Trip getTripId() {
        return tripId;
    }

    public void setTripId(Trip tripId) {
        this.tripId = tripId;
    }

    public Ticket() {
    }

    public Ticket(int seatNumber, String customerName, LocalDate purchaseDate, Cashier cashierId, Trip tripId) {
        this.seatNumber = seatNumber;
        this.customerName = customerName;
        this.purchaseDate = purchaseDate;
        this.cashierId = cashierId;
        this.tripId = tripId;
    }

    public Ticket(String customerName, LocalDate purchaseDate, Cashier cashierId, Trip tripId) {
        this.customerName = customerName;
        this.purchaseDate = purchaseDate;
        this.cashierId = cashierId;
        this.tripId = tripId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ticket ticket = (Ticket) o;
        return Objects.equals(tripId, ticket.tripId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tripId);
    }
}