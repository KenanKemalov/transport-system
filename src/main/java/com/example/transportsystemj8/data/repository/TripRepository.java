package com.example.transportsystemj8.data.repository;

import com.example.transportsystemj8.data.entity.Company;
import com.example.transportsystemj8.data.entity.Trip;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TripRepository extends CrudRepository<Trip, Integer> {
    Trip findTripByTripId(Integer id);

//    @Query("SELECT ")
//    List<Trip> findAllByCompanyId(Company company);
}
