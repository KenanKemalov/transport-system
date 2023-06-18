package com.example.transportsystemj8.controllers;

import com.example.transportsystemj8.data.entity.*;
import com.example.transportsystemj8.data.repository.DistributorRepository;
import com.example.transportsystemj8.data.repository.RequestRepository;
import com.example.transportsystemj8.data.repository.TripRepository;
import com.example.transportsystemj8.services.CashierServiceImpl;
import com.example.transportsystemj8.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import static com.example.transportsystemj8.data.entity.UserRole.CASHIER;

@Controller
public class DistributorController {

    @Autowired
    private TripRepository tripRepository;

    @Autowired
    private RequestRepository requestRepository;

    @Autowired
    private UserService userService;
    @Autowired
    private DistributorRepository distributorRepository;

    @Autowired
    private CashierServiceImpl cashierService;

    @GetMapping("/distributor/dashboard")
    public String showCompanyDashboard(Model model) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Distributor distributor = distributorRepository.findDistributorByDistributorId(user.getUserId());
        model.addAttribute("distributor", distributor);
        return "distributor-dashboard";
    }

    @GetMapping("/create/cashier")
    public String showCreateCashierForm(Model model){
        model.addAttribute("cashier", new Cashier());
        return "distributor/create-cashier";
    }

    @PostMapping("/create/cashier")
    public String processCreateCashierForm(@ModelAttribute("cashier") Cashier cashier, BindingResult result){
        System.out.println("post");
        //Optional<User> usernameEntry = userRepository.findByUsername(company.getUser().getUsername());
        //company.getUser().setUserRole(COMPANY);
        if (result.hasErrors()) {
            return "distributor/create-cashier";
        }
        if (!userService.checkIfUserExists(cashier.getUser()) && userService.validateUser(cashier.getUser().getPassword())){
//        if (!usernameEntry.isPresent()){
            User user = new User();
            user.setUsername(cashier.getUser().getUsername());
            user.setPassword(new BCryptPasswordEncoder().encode(cashier.getUser().getPassword()));
            user.setUserRole(CASHIER);
            cashier.setUser(user);
            cashier.setCashierId(user.getUserId());
            System.out.println(cashier.toString());
            cashierService.saveCashier(cashier);
            userService.saveUser(user);
            System.out.println("cashier saved");
            return "redirect:/distributor/dashboard";
        }
        System.out.println("cashier not saved");
        return "redirect:/r/";
    }


    @GetMapping("/request/tickets")
    public String showRequestTicketsForm(Model model){
        model.addAttribute("request", new Request());
        model.addAttribute("trips", tripRepository.findAll());
        return "distributor/request-tickets";
    }

    @PostMapping("/request/tickets")
    public String processRequestTicketsForm(@ModelAttribute("request") Request request,
                                            @RequestParam("selectedTrip") Integer tripId,
                                            BindingResult result){
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        System.out.println("post");
        if (result.hasErrors()) {
            System.out.println(result);
            return "distributor/request-tickets";
        }
        System.out.println(tripId.toString());
        Trip trip = tripRepository.findTripByTripId(tripId);
        System.out.println(trip.toString());
        request.setTripId(trip);
        request.setDistributorId(distributorRepository.findDistributorByDistributorId(user.getUserId()));
        request.setCompanyId(trip.getCompanyId());
        request.setStatus("PENDING");
        requestRepository.save(request);
        System.out.println("request saved");
        return "redirect:/distributor/dashboard";
    }
}
