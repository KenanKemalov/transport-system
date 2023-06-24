package com.example.transportsystemj8.services;

import com.example.transportsystemj8.data.entity.TransportType;
import com.example.transportsystemj8.data.entity.TripType;
import com.example.transportsystemj8.data.repository.TripTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TripTypeServiceImpl implements TripTypeService{
    @Autowired
    private TripTypeRepository tripTypeRepository;

    @Override
    public void saveTripType(TripType tripType) {
        tripTypeRepository.save(tripType);
    }

    public boolean checkIfTripTypeExists(TripType tripType){
//        Optional<User> foundUser = userRepository.findByUsernameAndUserRole(user.getUsername(), user.getUserRole());
        TripType foundTripType = tripTypeRepository.findTripTypeByTripTypeName(tripType.getTripTypeName());
        return foundTripType != null;
    }

    public List<TripType> findAll(){
        return tripTypeRepository.findAllTripTypes();
    }

    public void deleteTripType(TripType tripType){
        tripTypeRepository.delete(tripType);
    }

    public TripType findTripTypeByName(String name){
        return tripTypeRepository.findTripTypeByTripTypeName(name);
    }
//    @Autowired
//    private TripTypeRepository tripTypeRepository;
//
//    public TripTypeServiceImpl(TripTypeRepository tripTypeRepository) {
//        this.tripTypeRepository = tripTypeRepository;
//    }
//
//    List<TripType> allTripTypes = tripTypeRepository.findAllTripTypes();
}
