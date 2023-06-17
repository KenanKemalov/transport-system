package com.example.transportsystemj8.services;

import com.example.transportsystemj8.data.entity.Cashier;
import com.example.transportsystemj8.data.entity.Company;
import com.example.transportsystemj8.data.repository.CashierRepository;
import com.example.transportsystemj8.data.repository.CompanyRepository;
import org.springframework.stereotype.Service;

@Service
public class CashierServiceImpl implements CashierService{

    private CashierRepository cashierRepository;

    public CashierServiceImpl(CashierRepository cashierRepository) {
        this.cashierRepository = cashierRepository;
    }
    @Override
    public void saveCashier(Cashier cashier) {
        cashierRepository.save(cashier);
    }
}
