package com.example.transportsystemj8.services;

import com.example.transportsystemj8.data.entity.Location;
import com.example.transportsystemj8.data.entity.User;
import com.example.transportsystemj8.data.repository.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LocationServiceImpl implements LocationService {

    @Autowired
    LocationRepository locationRepository;

    @Override
    public void saveLocation(Location location) {
        locationRepository.save(location);
    }

    public boolean checkIfLocationExists(Location location){
//        Optional<User> foundUser = userRepository.findByUsernameAndUserRole(user.getUsername(), user.getUserRole());
        Location foundLocation = locationRepository.findByLocationName(location.getLocationName());
        return foundLocation != null;
    }
}
