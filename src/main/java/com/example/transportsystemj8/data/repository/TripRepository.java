package com.example.transportsystemj8.data.repository;

import com.example.transportsystemj8.data.entity.Company;
import com.example.transportsystemj8.data.entity.Location;
import com.example.transportsystemj8.data.entity.Trip;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TripRepository extends CrudRepository<Trip, Integer> {
    @Query("SELECT t FROM Trip t")
    List<Trip> findAllTrips();

    List<Trip> findAllByCompanyId(Company company);

    Trip findTripByTripId(Integer id);

    List<Trip> findAllByDepartureAfterAndArrivalBeforeAndLocationFromAndLocationTo(LocalDate departure, LocalDate arrival, Location locationFrom, Location locationTo);

    List<Trip> findAllByLocationFromAndLocationTo(Location locationFrom, Location locationTo);

//    @Query("SELECT ")
//    List<Trip> findAllByCompanyId(Company company);
}
