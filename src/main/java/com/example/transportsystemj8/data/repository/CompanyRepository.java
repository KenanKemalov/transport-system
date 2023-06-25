package com.example.transportsystemj8.data.repository;

import com.example.transportsystemj8.data.entity.Company;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CompanyRepository extends CrudRepository<Company, Integer> {
    Company findCompanyByCompanyId(Integer id);

    @Query("SELECT c FROM Company  c")
    List<Company> findAllCompanies();

}
