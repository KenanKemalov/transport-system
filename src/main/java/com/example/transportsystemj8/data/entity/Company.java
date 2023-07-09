package com.example.transportsystemj8.data.entity;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;
import java.util.Set;

@Table(name = "Company")
@Entity
public class Company implements Serializable {
    private static final long serialVersionUID=1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "companyId", nullable = false)
    private Integer companyId;

    @Column(name = "companyName", nullable = false)
    private String companyName;
    @Column(name = "honorarium", nullable = false)
    private Double honorarium;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    private User user;

    @OneToMany(mappedBy = "companyId")
    Set<Request> requestSet3;

    @OneToMany(mappedBy = "companyId")
    Set<Trip> tripSet3;

    public Company() {
    }

    @Override
    public String toString() {
        return "Company{" +
                "companyId=" + companyId +
                ", companyName='" + companyName + '\'' +
                ", honorarium=" + honorarium +
                ", user=" + user +
                //", requestSet3=" + requestSet3 +
                //", tripSet3=" + tripSet3 +
                '}';
    }
    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Double getHonorarium() {
        return honorarium;
    }

    public void setHonorarium(Double honorarium) {
        this.honorarium = honorarium;
    }

    public Set<Request> getRequestSet3() {
        return requestSet3;
    }

    public void setRequestSet3(Set<Request> requestSet3) {
        this.requestSet3 = requestSet3;
    }

    public Set<Trip> getTripSet3() {
        return tripSet3;
    }

    public void setTripSet3(Set<Trip> tripSet3) {
        this.tripSet3 = tripSet3;
    }

}