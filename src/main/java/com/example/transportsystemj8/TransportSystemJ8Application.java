package com.example.transportsystemj8;

import com.example.transportsystemj8.data.entity.TransportType;
import com.example.transportsystemj8.data.repository.TransportTypeRepository;
import com.example.transportsystemj8.services.TransportTypeServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TransportSystemJ8Application {

	//@Autowired
	//public static TransportTypeServiceImpl transportTypeService;

	public static void main(String[] args) {

		//transportTypeService.saveTransportType(new TransportType("Plane"));
		SpringApplication.run(TransportSystemJ8Application.class, args);
		//transportTypeService.saveTransportType(new TransportType("Plane"));
	}

}
