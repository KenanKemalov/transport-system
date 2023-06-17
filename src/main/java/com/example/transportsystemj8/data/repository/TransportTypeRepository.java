package com.example.transportsystemj8.data.repository;

import com.example.transportsystemj8.data.entity.TransportType;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransportTypeRepository extends CrudRepository<TransportType, Integer> {
    @Query("SELECT t FROM TransportType t")
    List<TransportType> findAllTransportTypes();
}
