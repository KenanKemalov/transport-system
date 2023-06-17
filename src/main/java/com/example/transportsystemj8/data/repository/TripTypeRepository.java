package com.example.transportsystemj8.data.repository;

import com.example.transportsystemj8.data.entity.TripType;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TripTypeRepository extends CrudRepository<TripType, Integer> {

    @Query("SELECT t FROM TripType t")
    List<TripType> findAllTripTypes();
}
