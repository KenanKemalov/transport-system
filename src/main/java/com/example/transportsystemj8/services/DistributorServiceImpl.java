package com.example.transportsystemj8.services;

import com.example.transportsystemj8.data.entity.Company;
import com.example.transportsystemj8.data.entity.Distributor;
import com.example.transportsystemj8.data.repository.CompanyRepository;
import com.example.transportsystemj8.data.repository.DistributorRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DistributorServiceImpl implements DistributorService{
    private DistributorRepository distributorRepository;

    public DistributorServiceImpl(DistributorRepository distributorRepository) {
        this.distributorRepository = distributorRepository;
    }
    @Override
    public void saveDistributor(Distributor distributor){
        distributorRepository.save(distributor);
    }

    public Distributor findDistributorById(Integer distributorId){
        return distributorRepository.findDistributorByDistributorId(distributorId);
    }

    public List<Distributor> findAllDistributors(){
        return distributorRepository.findAllDistributors();
    }
}
