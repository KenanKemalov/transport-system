package com.example.transportsystemj8.controllers;

import com.example.transportsystemj8.data.entity.Location;
import com.example.transportsystemj8.data.entity.TransportType;
import com.example.transportsystemj8.data.entity.TripType;
import com.example.transportsystemj8.data.entity.User;
import com.example.transportsystemj8.services.*;
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

@Controller
public class AdminController {

    @Autowired
    private UserService userService;

    @Autowired
    private LocationServiceImpl locationService;

    @Autowired
    private TripTypeServiceImpl tripTypeService;

    @Autowired
    private TransportTypeServiceImpl transportTypeService;

    @GetMapping("/admin/dashboard")
    public String showCompanyDashboard() {
        return "admin-dashboard";
    }


    @GetMapping("/update/password")
    public String showUpdatePasswordForm(Model model){
        model.addAttribute("user", SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        return "update-password";
    }

    @PostMapping("/update/password")
    public String processUpdatePasswordForm(@RequestParam("password") String password,
                                            @RequestParam("newPassword") String newPassword,
                                            @RequestParam("confirmPassword") String confirmPassword){
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(!new BCryptPasswordEncoder().matches(password, user.getPassword())){
            System.out.println("wrong password");
            return "redirect:/default";
        }

        if(userService.validateNewPassword(newPassword, confirmPassword)){
            userService.updatePassword(user, newPassword);
        }
        return "redirect:/update/password";

    }

    @GetMapping("/add/location")
    public String showAddLocationForm(Model model){
        model.addAttribute("location", new Location());
        return "admin/add-location";
    }

    @PostMapping("/add/location")
    public String processAddLocationForm(@ModelAttribute("location") Location location, BindingResult bindingResult){
        if(bindingResult.hasErrors()) {
            return "admin/add-location";
        }

        if(!locationService.checkIfLocationExists(location)){
            locationService.saveLocation(location);
            return "redirect:/add/location";
        }
        return "redirect:/add/location";
    }

    @GetMapping("/add/triptype")
    public String showAddTripTypeForm(Model model){
        model.addAttribute("triptype", new TripType());
        return "admin/add-triptype";
    }

    @PostMapping("/add/triptype")
    public String processAddTripTypeForm(@ModelAttribute("location") TripType tripType, BindingResult bindingResult){
        if(bindingResult.hasErrors()) {
            return "admin/add-triptype";
        }

        if(!tripTypeService.checkIfTripTypeExists(tripType)){
            tripTypeService.saveTripType(tripType);
            return "redirect:/add/triptype";
        }
        return "redirect:/add/triptype";
    }

    @GetMapping("/add/transporttype")
    public String showAddTransportTypeForm(Model model){
        model.addAttribute("transporttype", new TransportType());
        return "admin/add-transporttype";
    }

    @PostMapping("/add/transporttype")
    public String processTransportTypeForm(@ModelAttribute("location") TransportType transportType, BindingResult bindingResult){
        if(bindingResult.hasErrors()) {
            return "admin/add-transporttype";
        }

        if(!transportTypeService.checkIfTransportTypeExists(transportType)){
            transportTypeService.saveTransportType(transportType);
            return "redirect:/add/transporttype";
        }
        return "redirect:/add/transporttype";
    }}
