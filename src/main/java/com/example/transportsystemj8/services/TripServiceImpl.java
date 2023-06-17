package com.example.transportsystemj8.services;

import com.example.transportsystemj8.data.entity.Trip;
import com.example.transportsystemj8.data.repository.TripRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TripServiceImpl implements TripService{
    @Autowired
    TripRepository tripRepository;

    @Override
    public void saveTrip(Trip trip) {
        tripRepository.save(trip);
    }
}
