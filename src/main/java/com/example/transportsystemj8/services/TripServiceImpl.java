package com.example.transportsystemj8.services;

import com.example.transportsystemj8.data.entity.Location;
import com.example.transportsystemj8.data.entity.Trip;
import com.example.transportsystemj8.data.repository.TripRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class TripServiceImpl implements TripService{
    @Autowired
    TripRepository tripRepository;

    @Override
    public void saveTrip(Trip trip) {
        tripRepository.save(trip);
    }


    public List<Trip> findAll(){
        return tripRepository.findAllTrips();
    }

    public List<Trip> findAllTripsByFullFilter(LocalDate departure, LocalDate arrival, Location locationFrom, Location locationTo){
        return tripRepository.findAllByDepartureAfterAndArrivalBeforeAndLocationFromAndLocationTo(departure, arrival, locationFrom, locationTo);
    }

    public List<Trip> findAllByLocations(Location locationFrom, Location locationTo){
        return tripRepository.findAllByLocationFromAndLocationTo(locationFrom, locationTo);
    }
}
