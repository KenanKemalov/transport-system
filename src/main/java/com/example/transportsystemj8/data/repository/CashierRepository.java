package com.example.transportsystemj8.data.repository;

import com.example.transportsystemj8.data.entity.Cashier;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CashierRepository extends CrudRepository<Cashier, Integer> {
    Cashier findCashierByCashierId(Integer id);
}
