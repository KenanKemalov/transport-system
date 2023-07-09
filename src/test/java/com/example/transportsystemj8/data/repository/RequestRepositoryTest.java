package com.example.transportsystemj8.data.repository;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

import com.example.transportsystemj8.data.entity.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;
import java.util.List;

//import org.junit.Before;

@RunWith(MockitoJUnitRunner.class)
public class RequestRepositoryTest {
    @Mock
    private RequestRepository requestRepository;

    @Test
    public void testFindAllByCompanyIdAndStatus() {
        User company = new User();
        company.setUsername("company");
        company.setPassword("company");

        User distributor = new User();
        distributor.setUsername("distributor");
        distributor.setPassword("distributor");

        Company testCompany = new Company();
        testCompany.setCompanyName("Test Company");
        testCompany.setHonorarium(100.0);
        testCompany.setUser(company);

        Distributor testDistributor = new Distributor();
        testDistributor.setDistributorName("Test Distributor");
        testDistributor.setHonorarium(100.0);
        testDistributor.setUser(distributor);

        String testStatus = "PENDING";

        Request request1 = new Request();
        request1.setTicketCount(5);
        request1.setStatus("PENDING");
        request1.setTripId(new Trip());
        request1.setDistributorId(testDistributor);
        request1.setCompanyId(testCompany);

        Request request2 = new Request();
        request2.setTicketCount(5);
        request2.setStatus("PENDING");
        request2.setTripId(new Trip());
        request2.setDistributorId(testDistributor);
        request2.setCompanyId(testCompany);

        Request request3 = new Request();
        request3.setTicketCount(5);
        request3.setStatus("PENDING");
        request3.setTripId(new Trip());
        request3.setDistributorId(testDistributor);
        request3.setCompanyId(testCompany);

        List<Request> expectedRequests = Arrays.asList(request1, request2, request3);

        when(requestRepository.findAllByCompanyIdAndStatus(testCompany, testStatus))
                .thenReturn(expectedRequests);

        List<Request> actualRequests = requestRepository.findAllByCompanyIdAndStatus(testCompany, testStatus);

        verify(requestRepository).findAllByCompanyIdAndStatus(testCompany, testStatus);

        assertEquals(expectedRequests, actualRequests);
    }

    @Test
    public void testFindAllByCompanyIdAndStatusFalse() {
        User company = new User();
        company.setUsername("company");
        company.setPassword("company");

        User distributor = new User();
        distributor.setUsername("distributor");
        distributor.setPassword("distributor");

        Company testCompany = new Company();
        testCompany.setCompanyName("Test Company");
        testCompany.setHonorarium(100.0);
        testCompany.setUser(company);

        Distributor testDistributor = new Distributor();
        testDistributor.setDistributorName("Test Distributor");
        testDistributor.setHonorarium(100.0);
        testDistributor.setUser(distributor);

        String testStatus = "PENDING";

        Request request1 = new Request();
        request1.setTicketCount(5);
        request1.setStatus("PENDING");
        request1.setTripId(new Trip());
        request1.setDistributorId(testDistributor);
        request1.setCompanyId(testCompany);

        Request request2 = new Request();
        request2.setTicketCount(5);
        request2.setStatus("PENDING");
        request2.setTripId(new Trip());
        request2.setDistributorId(testDistributor);
        request2.setCompanyId(testCompany);

        Request request3 = new Request();
        request3.setTicketCount(5);
        request3.setStatus("ACCEPTED");
        request3.setTripId(new Trip());
        request3.setDistributorId(testDistributor);
        request3.setCompanyId(testCompany);

        List<Request> expectedRequests = Arrays.asList(request1, request2, request3);

        when(requestRepository.findAllByCompanyIdAndStatus(testCompany, testStatus))
                .thenReturn(expectedRequests);

        List<Request> actualRequests = requestRepository.findAllByCompanyIdAndStatus(testCompany, testStatus);

        verify(requestRepository).findAllByCompanyIdAndStatus(testCompany, testStatus);

        assertEquals(expectedRequests, actualRequests);
    }
}


