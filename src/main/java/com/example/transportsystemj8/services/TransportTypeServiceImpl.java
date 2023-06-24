package com.example.transportsystemj8.services;

import com.example.transportsystemj8.data.entity.Location;
import com.example.transportsystemj8.data.entity.TransportType;
import com.example.transportsystemj8.data.repository.TransportTypeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransportTypeServiceImpl implements TransportTypeService{

    private TransportTypeRepository transportTypeRepository;

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

    public List<TransportType> findAll(){
        return transportTypeRepository.findAllTransportTypes();
    }

    public void deleteTransportType(TransportType transportType){
        transportTypeRepository.delete(transportType);
    }

    public TransportType findTransportTypeByName(String name){
        return transportTypeRepository.findTransportTypeByTransportTypeName(name);
    }


}
