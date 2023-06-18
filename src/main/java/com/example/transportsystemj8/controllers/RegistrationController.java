package com.example.transportsystemj8.controllers;

import com.example.transportsystemj8.data.entity.Company;
import com.example.transportsystemj8.data.entity.Distributor;
import com.example.transportsystemj8.data.entity.User;
import com.example.transportsystemj8.data.entity.UserRole;
import com.example.transportsystemj8.data.repository.UserRepository;
import com.example.transportsystemj8.services.CompanyServiceImpl;
import com.example.transportsystemj8.services.DistributorServiceImpl;
import com.example.transportsystemj8.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

import static com.example.transportsystemj8.data.entity.UserRole.*;

@Controller
public class RegistrationController {
    //@Autowired
    private UserService userService;
   // private UserRepository userRepository;

    //@Autowired
    private CompanyServiceImpl companyService;
    private DistributorServiceImpl distributorService;

    public RegistrationController(UserService userService, CompanyServiceImpl companyService,
                                  DistributorServiceImpl distributorService/*, UserRepository userRepository*/) {
        this.userService = userService;
        this.companyService = companyService;
        this.distributorService = distributorService;
        //this.userRepository = userRepository;

    }

    @GetMapping("/register/company")
    public String showCompanyRegistrationForm(Model model) {
        System.out.println("get");
        model.addAttribute("company", new Company());
        return "company-registration";
    }

    @PostMapping("/register/company")
    public String processCompanyRegistrationForm(@ModelAttribute("company") Company company, BindingResult result) {
        System.out.println("post");
        //Optional<User> usernameEntry = userRepository.findByUsername(company.getUser().getUsername());
        //company.getUser().setUserRole(COMPANY);
        if (result.hasErrors()) {
            return "company-registration";
        }
        if (!userService.checkIfUserExists(company.getUser()) && userService.validateUser(company.getUser().getPassword())){
//        if (!usernameEntry.isPresent()){
            User user = new User();
            user.setUsername(company.getUser().getUsername());
            user.setPassword(new BCryptPasswordEncoder().encode(company.getUser().getPassword()));
            user.setUserRole(COMPANY);
            company.setUser(user);
            company.setCompanyId(user.getUserId());
            System.out.println(company.toString());
            companyService.saveCompany(company);
            userService.saveUser(user);
            System.out.println("company saved");
            return "redirect:/login";
        }
        System.out.println("company not saved");
        return "redirect:/r/";
    }

    //Code down below not finished
    @GetMapping("/register/distributor")
    public String showDistributorRegistrationForm(Model model) {
        model.addAttribute("distributor", new Distributor());
        return "distributor-registration";
    }

    @PostMapping("/register/distributor")
    public String processDistributorRegistrationForm(@ModelAttribute("distributor") Distributor distributor,
                                                     BindingResult result) {
        //distributor.getUser().setUserRole(DISTRIBUTOR);
        if (result.hasErrors()) {
            return "distributor-registration";
        }

        if (!userService.checkIfUserExists(distributor.getUser()) && userService.validateUser(distributor.getUser().getPassword())){
            User user = new User();
            user.setUsername(distributor.getUser().getUsername());
            user.setPassword(new BCryptPasswordEncoder().encode(distributor.getUser().getPassword()));
            //user.setPassword(company.getUser().getPassword());
            user.setUserRole(DISTRIBUTOR);
            distributor.setUser(user);
            distributor.setDistributorId(user.getUserId());
            System.out.println(distributor.toString());
            distributorService.saveDistributor(distributor);
            userService.saveUser(user);
            return "redirect:/login";
        }
        return "redirect:/r/";
    }
}
