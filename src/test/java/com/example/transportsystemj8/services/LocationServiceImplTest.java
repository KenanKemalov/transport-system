package com.example.transportsystemj8.services;
import com.example.transportsystemj8.data.entity.Location;
import com.example.transportsystemj8.data.repository.LocationRepository;
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
public class LocationServiceImplTest {

    @Mock
    private LocationRepository locationRepository;

    @InjectMocks
    private LocationServiceImpl locationService;

    private Location location;
    private Location location1;
    private List<Location> locationList;

    @Before
    public void setup() {
        // Create a dummy Location object for testing
        location = new Location();
        location.setLocationId(1);
        location.setLocationName("Location 1");

        location1 = new Location();
        location1.setLocationId(2);
        location1.setLocationName("Location 2");

        // Create a dummy list of Locations for testing
        locationList = new ArrayList<>();
        locationList.add(location);
    }

    @Test
    public void testSaveLocation() {
        // Call the service method
        locationService.saveLocation(location);

        // Verify that the repository method was called with the expected parameter
        verify(locationRepository).save(location);
    }

    @Test
    public void testCheckIfLocationExists_LocationExists() {
        // Mock the behavior of the repository method
        when(locationRepository.findByLocationName(anyString())).thenReturn(location);

        // Call the service method
        boolean result = locationService.checkIfLocationExists(location);

        // Verify the result
        assertTrue(result);
    }

    @Test
    public void testCheckIfLocationExists_LocationDoesNotExist() {
        // Mock the behavior of the repository method
        when(locationRepository.findByLocationName(anyString())).thenReturn(null);

        // Call the service method
        boolean result = locationService.checkIfLocationExists(location);

        // Verify the result
        assertFalse(result);
    }

    @Test
    public void testFindAll() {
        // Mock the behavior of the repository method
        when(locationRepository.findAllLocations()).thenReturn(locationList);

        // Call the service method
        List<Location> result = locationService.findAll();

        // Verify the result
        assertEquals(1, result.size());
        assertEquals(location, result.get(0));
    }

    @Test
    public void testDeleteLocation() {
        // Call the service method
        locationService.deleteLocation(location);

        // Verify that the repository method was called with the expected parameter
        verify(locationRepository).delete(location);
    }

    @Test
    public void testFindLocationByName() {
        // Mock the behavior of the repository method
        when(locationRepository.findByLocationName(anyString())).thenReturn(location);

        // Call the service method
        Location result = locationService.findLocationByName("Location 1");

        // Verify the result
        assertNotNull(result);
        assertEquals(location, result);
    }
}

