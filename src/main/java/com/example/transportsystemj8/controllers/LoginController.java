package com.example.transportsystemj8.controllers;

import com.example.transportsystemj8.data.entity.Company;
import com.example.transportsystemj8.data.entity.User;
import com.example.transportsystemj8.data.entity.UserRole;
import com.example.transportsystemj8.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Collection;
import java.util.Optional;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Controller
public class LoginController {

    private UserService userService;

    public LoginController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String showLogin(Model model) {
        model.addAttribute("user", new User());
        return "login";
    }

    @GetMapping("/default")
    public String defaultAfterLogin() {
        Collection<? extends GrantedAuthority> authorities;
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        authorities = auth.getAuthorities();
        String myRole = authorities.toArray()[0].toString();
        String admin = "ADMIN";
        String company = "COMPANY";
        String distributor = "DISTRIBUTOR";
        String cashier = "CASHIER";
        if (myRole.equals(admin)) {
            return "redirect:/admin/dashboard";
        } else if (myRole.equals(company)) {
            return "redirect:/company/dashboard";
        } else if (myRole.equals(distributor)) {
            return "redirect:/distributor/dashboard";
        } else if(myRole.equals(cashier)){
            return "redirect:/cashier/dashboard";
        }
        return "redirect:/r/";
    }
}
