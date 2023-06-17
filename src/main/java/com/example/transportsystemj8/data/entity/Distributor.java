package com.example.transportsystemj8.data.entity;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;
import java.util.Set;

@Table(name = "Distributor")
@Entity
public class Distributor implements Serializable {
    private static final long serialVersionUID=1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "distributorId", nullable = false)
    private Integer distributorId;

    @Column(name = "distributorName", nullable = false)
    private String distributorName;

    @Column(name = "honorarium", nullable = false)
    private double honorarium;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    private User user;

    @OneToMany(mappedBy = "distributorId")
    Set<Request> requestSet2;

    public Distributor() {
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
    //    public Distributor(String username, String password) {
//        this.username = username;
//        this.password = password;
//    }
//
//    public Distributor(String distributorName, String username, String password, double honorarium) {
//        this.distributorName = distributorName;
//        this.username = username;
//        this.password = password;
//        this.honorarium = honorarium;
//    }

//    @Override
//    public String toString() {
//        return "Distributor{" +
//                "distributorId=" + distributorId +
//                ", distributorName='" + distributorName + '\'' +
//                ", username='" + username + '\'' +
//                ", password='" + password + '\'' +
//                ", honorarium=" + honorarium +
//                ", requestSet2=" + requestSet2 +
//                '}';
//    }

    public Integer getDistributorId() {
        return distributorId;
    }

    public void setDistributorId(Integer distributorId) {
        this.distributorId = distributorId;
    }

    public String getDistributorName() {
        return distributorName;
    }

    public void setDistributorName(String distributorName) {
        this.distributorName = distributorName;
    }

    public double getHonorarium() {
        return honorarium;
    }

    public void setHonorarium(double honorarium) {
        this.honorarium = honorarium;
    }

    public Set<Request> getRequestSet2() {
        return requestSet2;
    }

    public void setRequestSet2(Set<Request> requestSet2) {
        this.requestSet2 = requestSet2;
    }
//
//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        Distributor that = (Distributor) o;
//        return Objects.equals(username, that.username);
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(username);
//    }
}