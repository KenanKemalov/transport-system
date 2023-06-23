package com.example.transportsystemj8.services;

import com.example.transportsystemj8.data.entity.Location;
import com.example.transportsystemj8.data.entity.TransportType;
import com.example.transportsystemj8.data.repository.TransportTypeRepository;
import org.springframework.stereotype.Service;

@Service
public class TransportTypeServiceImpl implements TransportTypeService{

    TransportTypeRepository transportTypeRepository;

    public TransportTypeServiceImpl(TransportTypeRepository transportTypeRepository) {
        this.transportTypeRepository = transportTypeRepository;
    }

    @Override
    public void saveTransportType(TransportType transportType) {
        transportTypeRepository.save(transportType);
    }

    public boolean checkIfTransportTypeExists(TransportType transportType){
//        Optional<User> foundUser = userRepository.findByUsernameAndUserRole(user.getUsername(), user.getUserRole());
        TransportType foundTransportType = transportTypeRepository.findTransportTypeByTransportTypeName(transportType.getTransportTypeName());
        return foundTransportType != null;
    }


}
