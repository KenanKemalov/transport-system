package com.example.transportsystemj8.data.repository;

import com.example.transportsystemj8.data.entity.Distributor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DistributorRepository extends CrudRepository<Distributor, Integer> {
    Distributor findDistributorByDistributorId(Integer id);

    @Query("SELECT d FROM Distributor d")
    List<Distributor> findAllDistributors();
}
