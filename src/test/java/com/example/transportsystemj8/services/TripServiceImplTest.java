package com.example.transportsystemj8.services;

import com.example.transportsystemj8.data.entity.Company;
import com.example.transportsystemj8.data.entity.Location;
import com.example.transportsystemj8.data.entity.TransportType;
import com.example.transportsystemj8.data.entity.Trip;
import com.example.transportsystemj8.data.repository.TripRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class TripServiceImplTest {

    @Mock
    private TripRepository tripRepository;

    @InjectMocks
    private TripServiceImpl tripService;

    private Trip trip;
    private List<Trip> tripList;

    @Before
    public void setup() {
        Location locationFrom = new Location("varna");
        Location locationTo = new Location("sofia");
        TransportType transportType = new TransportType("bus");
        trip = new Trip();
        trip.setDeparture(LocalDate.now().plusDays(1));
        trip.setArrival(LocalDate.now().plusDays(2));
        trip.setLocationTo(locationTo);
        trip.setLocationFrom(locationFrom);
        trip.setTransportTypeId(transportType);
        tripList = new ArrayList<>();
        tripList.add(trip);
    }

    @Test
    public void testSaveTrip() {
        tripService.saveTrip(trip);
        verify(tripRepository).save(trip);
    }

    @Test
    public void testFindAll() {
        when(tripRepository.findAllTrips()).thenReturn(tripList);
        List<Trip> result = tripService.findAll();
        assertEquals(1, result.size());
        assertEquals(trip, result.get(0));
    }

    @Test
    public void testFindAllByCompany() {
        Company company = new Company();
        when(tripRepository.findAllByCompanyId(company)).thenReturn(tripList);
        List<Trip> result = tripService.findAllByCompany(company);
        assertEquals(1, result.size());
        assertEquals(trip, result.get(0));
    }

    @Test
    public void testFilterTrips() {
        Location locationFrom = new Location("varna");
        Location locationTo = new Location("sofia");
        TransportType transportType = new TransportType("bus");
        when(tripRepository.findAllTrips()).thenReturn(tripList);
        List<Trip> result = tripService.filterTrips(null, null, locationFrom, locationTo, transportType);
        assertEquals(1, result.size());
        assertEquals(trip, result.get(0));
    }

    @Test
    public void testClearOldTrips() {
        List<Trip> allTrips = new ArrayList<>();
        Trip oldTrip = new Trip();
        oldTrip.setDeparture(LocalDate.now().minusDays(1));
        allTrips.add(oldTrip);
        allTrips.add(trip);
        List<Trip> result = tripService.clearOldTrips(allTrips);
        assertEquals(1, result.size());
        assertEquals(trip, result.get(0));

    }
}

