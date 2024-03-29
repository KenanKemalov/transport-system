package com.example.transportsystemj8.data.repository;

import com.example.transportsystemj8.data.entity.Location;
import com.example.transportsystemj8.data.entity.TransportType;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LocationRepository extends CrudRepository<Location, Integer> {
    @Query("SELECT t FROM Location t")
    List<Location> findAllLocations();

    Location findByLocationName(String locationName);
}
