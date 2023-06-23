package com.example.transportsystemj8.controllers;

import com.example.transportsystemj8.data.entity.*;
import com.example.transportsystemj8.data.repository.CashierRepository;
import com.example.transportsystemj8.data.repository.LocationRepository;
import com.example.transportsystemj8.data.repository.TicketRepository;
import com.example.transportsystemj8.data.repository.TripRepository;
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
    EmailService emailService;

    @Autowired
    private CashierServiceImpl cashierService;

    @Autowired
    CashierRepository cashierRepository;

    @Autowired
    TicketRepository ticketRepository;

    @Autowired
    private TripRepository tripRepository;

    @Autowired
    private TripServiceImpl tripService;

    @Autowired
    private LocationRepository locationRepository;

    @GetMapping("/cashier/dashboard")
    public String showCashierDashboard() {
        return "cashier-dashboard";
    }


    @GetMapping("/sell/ticket")
    public String showCheckRequestsForm(Model model){
        //model.addAttribute("ticket", new Ticket());

//        List<Trip> allTrips = (List<Trip>) model.getAttribute("allTrips");

        if(model.getAttribute("allTrips") == null){
            model.addAttribute("allTrips", tripRepository.findAll());
        }

        model.addAttribute("selectedTrip", new Trip());
        //model.addAttribute("locations", locationRepository.findAllLocations());
        System.out.println("da ama ne");
        return "cashier/sell-ticket";
        //return "cashier/sell-ticket";
    }

//    @PostMapping(value = "/sell/ticket", params = "filter")
//    public RedirectView showCheckRequestsFilter(Model model, String locationFrom, String locationTo, LocalDate departure, LocalDate arrival){
//        //model.addAttribute("ticket", new Ticket());
//        List<Trip> allTrips;
//        if(departure == null || arrival == null){
//            if (locationFrom != null && locationTo != null){
//                allTrips = tripService.findAllByLocations(locationRepository.findByLocationName(locationFrom), locationRepository.findByLocationName(locationTo));
//            } else {
//                allTrips = tripService.findAll();
//                //allTrips = tripRepository.findAll();
//            }
//        } else if (locationFrom != null && locationTo != null){
//            allTrips = tripService.findAllTripsByFullFilter(departure, arrival,
//                    locationRepository.findByLocationName(locationFrom), locationRepository.findByLocationName(locationTo));
//        } else {
//            allTrips = tripService.findAll();
//        }
//
//        model.addAttribute("allTrips", allTrips);
//        model.addAttribute("selectedTrip", new Trip());
//        System.out.println("success");
//        //model.addAttribute("locations", locationRepository.findAllLocations());
//        return new RedirectView("/sell/ticket");
//        //return "cashier/sell-ticket";
//    }

//    @GetMapping("/sell/ticket/filter")
//    public String Filter(Model model, String locationFrom, String locationTo, LocalDate departure, LocalDate arrival){
//        //model.addAttribute("ticket", new Ticket());
//        List<Trip> allTrips;
//        if(departure == null || arrival == null){
//            if (locationFrom != null && locationTo != null){
//                allTrips = tripService.findAllByLocations(locationRepository.findByLocationName(locationFrom), locationRepository.findByLocationName(locationTo));
//            } else {
//                allTrips = tripService.findAll();
//                //allTrips = tripRepository.findAll();
//            }
//        } else if (locationFrom != null && locationTo != null){
//            allTrips = tripService.findAllTripsByFullFilter(departure, arrival,
//                    locationRepository.findByLocationName(locationFrom), locationRepository.findByLocationName(locationTo));
//        } else {
//            allTrips = tripService.findAll();
//        }
//
//        model.addAttribute("allTrips", allTrips);
//        model.addAttribute("selectedTrip", new Trip());
//        //model.addAttribute("locations", locationRepository.findAllLocations());
//        return "/sell/ticket";
//        //return "cashier/sell-ticket";
//    }

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


        Trip trip = tripRepository.findTripByTripId(tripId);
        System.out.println(trip.toString());
        List<Ticket> allTickets = ticketRepository.findAllByTripId(trip);
        for (Ticket ticket : allTickets){
            if(ticket.getCustomerName().equals("-NOT SOLD-")){
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
//                flag = true;
 //               model.addAttribute("selectedTrip", selectedTrip);//??
                return new RedirectView("/sell/ticket");
            }
        }
        System.out.println("ticket not sold (all tickets are sold)");
        return new RedirectView("/cashier/dashboard");
    }
}
