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
        List<Location> locations = new ArrayList<>();
        locations.add(new Location("Varna"));
        locations.add(new Location("Sofia"));
        locations.add(new Location("Berlin"));
        locations.add(new Location("Rio de Janeiro"));
        when(locationRepository.findAllLocations()).thenReturn(locations);
        List<Location> result = locationRepository.findAllLocations();
        verify(locationRepository, times(1)).findAllLocations();
        assertEquals(locations, result);
    }

    @Test
    public void testFindByLocationName() {
        String locationName = "Varna";
        Location location = new Location(locationName);
        when(locationRepository.findByLocationName(locationName)).thenReturn(location);
        Location result = locationRepository.findByLocationName(locationName);
        verify(locationRepository, times(1)).findByLocationName(locationName);
        assertEquals(location, result);
    }
}
