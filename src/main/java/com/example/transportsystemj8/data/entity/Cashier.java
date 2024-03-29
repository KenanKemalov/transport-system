package com.example.transportsystemj8.data.entity;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;
import java.util.Set;

@Table(name = "Cashier")
@Entity
public class Cashier implements Serializable {

    private static final long serialVersionUID=1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cashierId", nullable = false)
    private Integer cashierId;

    @Column(name = "cashierName", nullable = false)
    private String cashierName;

//    @Column(name = "username", nullable = false)
//    private String username;
//
//    @Column(name = "password", nullable = false)
//    private String password;

    @Column(name = "honorarium", nullable = false)
    private double honorarium;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    private User user;

    @OneToMany(mappedBy = "cashierId")
    Set<Ticket> ticketSet;

    public Set<Ticket> getTicketSet() {
        return ticketSet;
    }

    public void setTicketSet(Set<Ticket> ticketSet) {
        this.ticketSet = ticketSet;
    }

    public Cashier(String username, String password) {
    }

    public Cashier() {

    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    //    public Cashier(String cashierName, String username, String password, double honorarium) {
//        this.cashierName = cashierName;
//        this.username = username;
//        this.password = password;
//        this.honorarium = honorarium;
//    }
//
//    @Override
//    public String toString() {
//        return "Cashier{" +
//                "cashierId=" + cashierId +
//                ", cashierName='" + cashierName + '\'' +
//                ", username='" + username + '\'' +
//                ", password='" + password + '\'' +
//                ", honorarium=" + honorarium +
//                ", ticketSet2=" + ticketSet +
//                '}';
//    }

    public Integer getCashierId() {
        return cashierId;
    }

    public void setCashierId(Integer cashierId) {
        this.cashierId = cashierId;
    }

    public String getCashierName() {
        return cashierName;
    }

    public void setCashierName(String cashierName) {
        this.cashierName = cashierName;
    }

//    public String getUsername() {
//        return username;
//    }
//
//    public void setUsername(String username) {
//        this.username = username;
//    }
//
//    public String getPassword() {
//        return password;
//    }
//
//    public void setPassword(String password) {
//        this.password = password;
//    }

    public double getHonorarium() {
        return honorarium;
    }

    public void setHonorarium(double honorarium) {
        this.honorarium = honorarium;
    }

//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        Cashier cashier = (Cashier) o;
//        return Objects.equals(username, cashier.username);
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(username);
//    }
}