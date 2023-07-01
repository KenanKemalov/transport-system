package com.example.transportsystemj8.services;

import com.example.transportsystemj8.data.entity.Cashier;
import com.example.transportsystemj8.data.entity.Company;
import com.example.transportsystemj8.data.repository.CashierRepository;
import com.example.transportsystemj8.data.repository.CompanyRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class CashierServiceImpl implements CashierService{
    private Pattern namePattern = Pattern.compile("^[a-zA-Z]+(([',. -][a-zA-Z ])?[a-zA-Z]*)*$");

    private CashierRepository cashierRepository;

    public CashierServiceImpl(CashierRepository cashierRepository) {
        this.cashierRepository = cashierRepository;
    }
    @Override
    public void saveCashier(Cashier cashier) {
        cashierRepository.save(cashier);
    }

    public List<Cashier> findAllCashier(){
        return cashierRepository.findAllCashier();
    }

    public Cashier findCashierById(Integer cashierId){
        return cashierRepository.findCashierByCashierId(cashierId);
    }

    public boolean validateName(String name){
        Matcher matcher = namePattern.matcher(name);
        return matcher.matches();
    }
}
