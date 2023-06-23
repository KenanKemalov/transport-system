package com.example.transportsystemj8.controllers;

import com.example.transportsystemj8.data.entity.User;
import com.example.transportsystemj8.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AdminController {

    @Autowired
    private UserService userService;

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
    public String showAddLocationForm(){
        return "admin/add-location";
    }

    @GetMapping("/add/triptype")
    public String showAddTripTypeForm(){
        return "admin/add-triptype";
    }

    @GetMapping("/add/transporttype")
    public String showAddTransportTypeForm(){
        return "admin/add-transporttype";
    }
}
