package com.example.transportsystemj8.data.repository;

import com.example.transportsystemj8.data.entity.Company;
import com.example.transportsystemj8.data.entity.Request;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RequestRepository extends CrudRepository<Request, Integer> {
    Request findByRequestId(Integer integer);
    List<Request> findAllByCompanyId(Company company);
    List<Request> findAllByCompanyIdAndStatus(Company company, String status);
}
