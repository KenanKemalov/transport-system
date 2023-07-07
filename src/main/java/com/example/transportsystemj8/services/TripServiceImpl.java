package com.example.transportsystemj8.services;

import com.example.transportsystemj8.data.entity.Company;
import com.example.transportsystemj8.data.entity.Location;
import com.example.transportsystemj8.data.entity.TransportType;
import com.example.transportsystemj8.data.entity.Trip;
import com.example.transportsystemj8.data.repository.TripRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
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

    public List<Trip> findAllByCompany(Company company){
        return tripRepository.findAllByCompanyId(company);
    }

//    public List<Trip> findAllTripsByFullFilter(LocalDate departure, LocalDate arrival, Location locationFrom, Location locationTo){
//        return tripRepository.findAllByDepartureAfterAndArrivalBeforeAndLocationFromAndLocationTo(departure, arrival, locationFrom, locationTo);
//    }
//
//    public List<Trip> findAllByLocations(Location locationFrom, Location locationTo){
//        return tripRepository.findAllByLocationFromAndLocationTo(locationFrom, locationTo);
//    }

    public List<Trip> filterTrips(LocalDate departure, LocalDate arrival, Location locationFrom, Location locationTo, TransportType transportType){
        List<Trip> allTrips = findAll();
        List<Trip> filteredList = new ArrayList<>();

        for(Trip t : allTrips) {
            if(((locationFrom == null && locationTo == null) || (t.getLocationFrom().equals(locationFrom) && t.getLocationTo().equals(locationTo)))
                    //tuk ne sum siguren dali da filtrira samo za departure
                && ((departure == null && arrival == null) || (!t.getDeparture().isBefore(departure) && !t.getDeparture().isAfter(arrival)))
                    && (transportType == null || t.getTransportTypeId().equals(transportType))){
                filteredList.add(t);
            }
        }

        return filteredList;
    }

    public List<Trip> clearOldTrips(List<Trip> allTrips){
        List<Trip> clearedTrips = new ArrayList<>();
        for (Trip t : allTrips){
            if (!t.getDeparture().isBefore(LocalDate.now())){
                clearedTrips.add(t);
            }
        }
        return clearedTrips;
    }
}
