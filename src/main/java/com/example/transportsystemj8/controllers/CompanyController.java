package com.example.transportsystemj8.controllers;

import com.example.transportsystemj8.data.entity.*;
import com.example.transportsystemj8.data.repository.*;
import com.example.transportsystemj8.services.DirectionService;
import com.example.transportsystemj8.services.TripServiceImpl;
import com.example.transportsystemj8.services.TripTypeServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static com.example.transportsystemj8.data.entity.UserRole.CASHIER;

@Controller
public class CompanyController {
    @Autowired
    TicketRepository ticketRepository;

    @Autowired
    RequestRepository requestRepository;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private TripServiceImpl tripService;

//    @Autowired
//    private TripTypeServiceImpl tripTypeService;

    @Autowired
    private TripTypeRepository tripTypeRepository;

    @Autowired
    private TransportTypeRepository transportTypeRepository;

    @Autowired
    private LocationRepository locationRepository;

    @Autowired
    private DirectionService directionService;

    @GetMapping("/company/dashboard")
    public String showCompanyDashboard(Model model) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Company company = companyRepository.findCompanyByCompanyId(user.getUserId());
        model.addAttribute("company", company);
        return "company-dashboard";
    }

    @GetMapping("/create/trip")
    public String showCreateTripForm(Model model){
        model.addAttribute("trip", new Trip());
        model.addAttribute("triptypes", tripTypeRepository.findAllTripTypes());
        model.addAttribute("transporttypes", transportTypeRepository.findAllTransportTypes());
        model.addAttribute("locations", locationRepository.findAllLocations());
        return "company/create-trip";
    }

    @PostMapping("/create/trip")
    public String processCreateTripForm(@ModelAttribute("trip") Trip trip, BindingResult result){
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        trip.setCompanyId(companyRepository.findCompanyByCompanyId(user.getUserId()));
        if (result.hasErrors()) {
            System.out.println("trip not saved");
            System.out.println(result);
            System.out.println(trip.toString());
            return "redirect:/r/";
        }

        System.out.println(trip.toString());
        tripService.saveTrip(trip);
        System.out.println("trip saved");
        return "redirect:/company/dashboard";
    }

    @GetMapping("/check/requests")
    public String showCheckRequestsForm(Model model){
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("request", new Request());
        model.addAttribute("allRequests", requestRepository.findAllByCompanyIdAndStatus(companyRepository.findCompanyByCompanyId(user.getUserId()),
                "PENDING"));
        return "company/check-requests";
    }

    @PostMapping(value = "/check/requests", params = "accept")
    public RedirectView processAcceptRequest(@RequestParam("selectedRequest") Integer requestId){
        int maxSeatNum = 0;
        Request request = requestRepository.findByRequestId(requestId);
        int capacity = request.getTripId().getCapacity();
        System.out.println(capacity);
        System.out.println(request.toString());
        List<Ticket> allTickets = ticketRepository.findAllByTripId(request.getTripId());
        if (!allTickets.isEmpty()) {
            maxSeatNum = allTickets.get(allTickets.size() - 1).getSeatNumber();
            System.out.println(maxSeatNum);
        }
        System.out.println(maxSeatNum);
        if (capacity >= maxSeatNum + request.getTicketCount()){
            System.out.println("im here");
            request.setStatus("ACCEPTED");
            for (int i = 1; i <= request.getTicketCount(); i++){
                Ticket ticket = new Ticket();
                ticket.setCustomerName("-NOT SOLD-");
                ticket.setSeatNumber(++maxSeatNum);
                ticket.setPurchaseDate(LocalDate.of(2000, 1, 1));
                //ticket.setCashierId();
                ticket.setTripId(request.getTripId());
                ticketRepository.save(ticket);
            }
            requestRepository.save(request);
            return new RedirectView("/check/requests");
        }
        return new RedirectView("/company/dashboard");

    }

    @PostMapping(value = "/check/requests", params = "reject")
    public RedirectView processRejectRequest(@RequestParam("selectedRequest") Integer requestId){
        Request request = requestRepository.findByRequestId(requestId);
        request.setStatus("REJECTED");
        System.out.println(request.toString());
        requestRepository.save(request);
        return new RedirectView("/check/requests");
    }

    @GetMapping("/check/sold/tickets")
    public String checkSoldTickets(Model model){
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("allTrips", tripService.findAllByCompany(companyRepository.findCompanyByCompanyId(user.getUserId())));
        model.addAttribute("rep", ticketRepository);
        return "company/check-sold-tickets";
    }

//    tuka ima da se opravq dosta i nemam ideq zasq
    @GetMapping("/sold/tickets/direction")
    public String checkSoldTicketsByDirection(Model model,
                                              @RequestParam(value = "location-from", required = false) String locationFrom,
                                              @RequestParam(value = "location-to", required = false) String locationTo){
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<Direction> directions = new ArrayList<>();
        List<Trip> allTrips = tripService.findAllByCompany(companyRepository.findCompanyByCompanyId(user.getUserId()));
        for (Trip t : allTrips) {
            Direction direction = new Direction(t.getLocationFrom().getLocationName(),
                    t.getLocationTo().getLocationName(),
                    ticketRepository.findAllSoldTicketsByTrip(t));
            if (directions.contains(direction)){
                for (Direction d: directions) {
                    if(d.equals(direction)){
                        d.setSoldTickets(d.getSoldTickets()+direction.getSoldTickets());
                    }
                }
            }
            else
                directions.add(direction);
        }
        directions.sort(new Comparator<Direction>() {
            @Override
            public int compare(Direction o1, Direction o2) {
                return o2.getSoldTickets() - o1.getSoldTickets();
            }
        });

//        List<Direction> directions1 = directions;
        if((locationFrom != null && locationTo != null)){
            System.out.println(locationFrom + " " + locationTo);
            directions = directionService.filterTrips(directions, locationFrom, locationTo);
        }

        model.addAttribute("directions", directions);
        model.addAttribute("allTrips", allTrips);
//        model.addAttribute("rep", ticketRepository);
        model.addAttribute("locations", locationRepository.findAllLocations());
        return "company/sold-tickets-direction";
    }


}
