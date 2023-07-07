package com.example.transportsystemj8.data.repository;

import com.example.transportsystemj8.data.entity.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TicketRepositoryTest {

    @Mock
    private TicketRepository ticketRepository;

//    @InjectMocks
//    private TicketRepositoryImpl ticketRepositoryImpl;

    private Trip trip;
    private Company company;

    @Before
    public void setup() {
        // Create a dummy Trip object for testing
        trip = new Trip();
        company = new Company();
        company.setUser(new User());
        company.setCompanyName("Test Company");
        Location location = new Location("Varna");
        Location location1 = new Location("Sofia");
        trip.setLocationFrom(location);
        trip.setLocationTo(location1);
        trip.setCompanyId(company);

    }

    @Test
    public void testFindAllTickets() {
        List<Ticket> tickets = new ArrayList<>();
        tickets.add(new Ticket());
        tickets.add(new Ticket());
        when(ticketRepository.findAllTickets()).thenReturn(tickets);
        List<Ticket> result = ticketRepository.findAllTickets();
        assertEquals(tickets, result);
    }

    @Test
    public void testFindAllSoldTicketsByTrip() {
        when(ticketRepository.findAllSoldTicketsByTrip(any(Trip.class))).thenReturn(5);
        int result = ticketRepository.findAllSoldTicketsByTrip(trip);
        assertEquals(5, result);
    }

    @Test
    public void testFindAllCountByTripId() {
        when(ticketRepository.findAllCountByTripId(any(Trip.class))).thenReturn(10);
        int result = ticketRepository.findAllCountByTripId(trip);
        assertEquals(10, result);
    }

    @Test
    public void testFindAllByTripId() {
        List<Ticket> tickets = new ArrayList<>();
        tickets.add(new Ticket());
        tickets.add(new Ticket());
        when(ticketRepository.findAllByTripId(any(Trip.class))).thenReturn(tickets);
        List<Ticket> result = ticketRepository.findAllByTripId(trip);
        assertEquals(tickets, result);
    }
}


