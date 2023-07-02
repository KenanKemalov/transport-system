package com.example.transportsystemj8.controllers;

import com.example.transportsystemj8.data.entity.*;
import com.example.transportsystemj8.data.repository.*;
import com.example.transportsystemj8.services.CashierServiceImpl;
import com.example.transportsystemj8.services.EmailService;
import com.example.transportsystemj8.services.TripServiceImpl;
import com.example.transportsystemj8.services.UserService;
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

import javax.mail.MessagingException;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import static com.example.transportsystemj8.data.entity.UserRole.CASHIER;
import static com.example.transportsystemj8.data.entity.UserRole.COMPANY;

@Controller
public class CashierController {

    @Autowired
    private UserService userService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private CashierServiceImpl cashierService;

    @Autowired
    private TransportTypeRepository transportTypeRepository;

    @Autowired
    private CashierRepository cashierRepository;

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private TripRepository tripRepository;

    @Autowired
    private TripServiceImpl tripService;

    @Autowired
    private LocationRepository locationRepository;

    @GetMapping("/cashier/dashboard")
    public String showCashierDashboard(Model model) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("cashier", cashierRepository.findCashierByCashierId(user.getUserId()));
        return "cashier-dashboard";
    }


    @GetMapping("/sell/ticket")
    public String showCheckRequestsForm(Model model,
                                        @RequestParam(name = "location-from", required = false) String locationFrom,
                                        @RequestParam(name = "location-to", required = false) String locationTo,
                                        @RequestParam(name = "departure", required = false) String departure,
                                        @RequestParam(name = "arrival", required = false) String arrival,
                                        @RequestParam(name = "transport-type", required = false) String transportType){
//        List<Trip> allTrips;
        LocalDate departureDate = null;
        LocalDate arrivalDate = null;
        if(departure != null && !departure.isEmpty() && arrival != null && !arrival.isEmpty()) {
                departureDate = LocalDate.parse(departure);
                arrivalDate = LocalDate.parse(arrival);
        }
        List<Trip> allTrips = tripService.filterTrips(departureDate, arrivalDate,
                locationRepository.findByLocationName(locationFrom), locationRepository.findByLocationName(locationTo),
                transportTypeRepository.findTransportTypeByTransportTypeName(transportType));

        allTrips = tripService.clearOldTrips(allTrips);

        model.addAttribute("allTrips", allTrips);
        model.addAttribute("selectedTrip", new Trip());
        model.addAttribute("locations", locationRepository.findAllLocations());
        model.addAttribute("transporttypes", transportTypeRepository.findAllTransportTypes());
        model.addAttribute("rep", ticketRepository);
        return "cashier/sell-ticket";
    }

    @PostMapping(value = "/sell/ticket", params = "sell")
    public RedirectView processRequestTicketsForm(@RequestParam("customerName") String customerName,
                                                  @RequestParam("selectedTrip") Integer tripId,
                                                  @RequestParam("email") String email) throws MessagingException, IOException {
        //boolean flag = false;
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        LocalDate date = LocalDate.now();
        System.out.println("post");
        System.out.println(customerName);
//        System.out.println(selectedTrip);//??

        if (!cashierService.validateName(customerName)){
            return new RedirectView("/sell/ticket?name=true");
        }
        Trip trip = tripRepository.findTripByTripId(tripId);
        System.out.println(trip.toString());
        List<Ticket> allTickets = ticketRepository.findAllByTripId(trip);
        System.out.println(allTickets.size());
        for (Ticket ticket : allTickets){
            System.out.println(ticket.getCustomerName());
            if(ticket.getCustomerName().equals("-NOT SOLD-")){
                System.out.println("im here bro");
//            if(ticket.getTripId().equals(selectedTrip) && ticket.getCustomerName().equals("-NOT SOLD-")){//??
                ticket.setCustomerName(customerName);
                ticket.setCashierId(cashierRepository.findCashierByCashierId(user.getUserId()));
                ticket.setPurchaseDate(date);
                ticketRepository.save(ticket);
                //create the pdf file from html file and send it over email
                if (!email.isEmpty()){
                    emailService.sendEmail(email, ticket);
                }
                //end here
                System.out.println(ticket);
                System.out.println("ticket sold");
                return new RedirectView("/sell/ticket?success=true");
            }
        }
        System.out.println("ticket not sold (all tickets are sold)");
        return new RedirectView("/sell/ticket?error=true");
    }
}
