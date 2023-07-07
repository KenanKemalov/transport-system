package com.example.transportsystemj8.services;

import com.example.transportsystemj8.data.entity.Cashier;
import com.example.transportsystemj8.data.repository.CashierRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class CashierServiceImplTest {

    @Mock
    private CashierRepository cashierRepository;

    @InjectMocks
    private CashierServiceImpl cashierService;

    private Cashier cashier;
    private List<Cashier> cashierList;

    @Before
    public void setup() {
        cashier = new Cashier();
        cashier.setCashierId(1);
        cashier.setCashierName("John Doe");

        cashierList = new ArrayList<>();
        cashierList.add(cashier);
    }

    @Test
    public void testSaveCashier() {
        cashierService.saveCashier(cashier);
        verify(cashierRepository).save(cashier);
    }

    @Test
    public void testFindAllCashier() {
        when(cashierRepository.findAllCashier()).thenReturn(cashierList);
        List<Cashier> result = cashierService.findAllCashier();
        assertEquals(1, result.size());
        assertEquals(cashier, result.get(0));
    }

    @Test
    public void testFindCashierById() {
        when(cashierRepository.findCashierByCashierId(1)).thenReturn(cashier);
        Cashier result = cashierService.findCashierById(1);
        assertNotNull(result);
        assertEquals(cashier, result);
    }

    @Test
    public void testValidateName_ValidName() {
        String name = "John Doe";
        boolean result = cashierService.validateName(name);
        assertTrue(result);
    }

    @Test
    public void testValidateName_InvalidName() {
        String name = "123";
        boolean result = cashierService.validateName(name);
        assertFalse(result);
    }
}

