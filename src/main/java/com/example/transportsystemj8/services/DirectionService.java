package com.example.transportsystemj8.services;

import com.example.transportsystemj8.data.entity.Direction;
import com.example.transportsystemj8.data.entity.Location;
import com.example.transportsystemj8.data.entity.TransportType;
import com.example.transportsystemj8.data.entity.Trip;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class DirectionService {
    public List<Direction> filterTrips(List<Direction> allDirections, String locationFrom, String locationTo) {
        List<Direction> filteredList = new ArrayList<>();
        for (Direction d : allDirections) {
            if (((locationFrom == null && locationTo == null) || (d.getLocationFrom().equals(locationFrom) && d.getLocationTo().equals(locationTo)))) {
                filteredList.add(d);
            }
        }
        return filteredList;
    }
}
