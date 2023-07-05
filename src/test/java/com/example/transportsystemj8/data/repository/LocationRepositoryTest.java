package com.example.transportsystemj8.data.repository;

import com.example.transportsystemj8.data.entity.Location;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class LocationRepositoryTest {
    @Mock
    private LocationRepository locationRepository;

//    @BeforeEach
//    public void setUp(){
//        locationRepository = mock(locationRepository.getClass());
//    }

    @Test
    public void testFindAllLocations() {
        // Create a list of dummy locations for testing
        List<Location> locations = new ArrayList<>();
        locations.add(new Location("Varna"));
        locations.add(new Location("Sofia"));
        locations.add(new Location("Berlin"));
        locations.add(new Location("Rio de Janeiro"));
        // ... add more locations as needed

        // Define the behavior of the mock locationRepository when findAllLocations() is called
        when(locationRepository.findAllLocations()).thenReturn(locations);

        // Call the findAllLocations() method directly on the mock locationRepository
        List<Location> result = locationRepository.findAllLocations();

        // Verify that the mock locationRepository.findAllLocations() was called exactly once
        verify(locationRepository, times(1)).findAllLocations();

        // Assert that the result returned by the repository method is the same as the mocked locations
        assertEquals(locations, result);
    }

    @Test
    public void testFindByLocationName() {
        // Create a dummy location for testing
        String locationName = "Varna";
        Location location = new Location(locationName);

        // Define the behavior of the mock locationRepository when findByLocationName() is called
        when(locationRepository.findByLocationName(locationName)).thenReturn(location);

        // Call the findByLocationName() method directly on the mock locationRepository
        Location result = locationRepository.findByLocationName(locationName);

        // Verify that the mock locationRepository.findByLocationName() was called exactly once with the correct argument
        verify(locationRepository, times(1)).findByLocationName(locationName);

        // Assert that the result returned by the repository method is the same as the mocked location
        assertEquals(location, result);
    }
}
