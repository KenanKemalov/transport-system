package com.example.transportsystemj8.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminController {

    @GetMapping("/admin/dashboard")
    public String showCompanyDashboard() {
        return "admin-dashboard";
    }
}
