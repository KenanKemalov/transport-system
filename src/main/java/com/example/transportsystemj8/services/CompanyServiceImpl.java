package com.example.transportsystemj8.services;

import com.example.transportsystemj8.data.entity.Company;
import com.example.transportsystemj8.data.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanyServiceImpl implements CompanyService{
    //@Autowired
    private CompanyRepository companyRepository;

    public CompanyServiceImpl(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }
    @Override
    public void saveCompany(Company company) {
        companyRepository.save(company);
    }

    public Company findCompanyById(Integer companyId){
        return companyRepository.findCompanyByCompanyId(companyId);
    }

    public List<Company> findAllCompanies(){
        return companyRepository.findAllCompanies();
    }
}
