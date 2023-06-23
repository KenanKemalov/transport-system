package com.example.transportsystemj8.data.repository;

import com.example.transportsystemj8.data.entity.Ticket;
import com.example.transportsystemj8.data.entity.Trip;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TicketRepository extends CrudRepository<Ticket, Integer> {
    @Query("SELECT t FROM Ticket t")
    List<Ticket> findAllTickets();



    List<Ticket> findAllByTripId(Trip trip);
}
