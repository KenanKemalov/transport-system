package com.example.transportsystemj8.controllers;

import com.example.transportsystemj8.data.entity.*;
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

import java.util.List;

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

    @Autowired
    private CashierServiceImpl cashierService;

    @Autowired
    private CompanyServiceImpl companyService;

    @Autowired
    private DistributorServiceImpl distributorService;

    @GetMapping("/admin/dashboard")
    public String showCompanyDashboard(Model model) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("user", user);
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
            return "redirect:/update/password?oldpass=true";
        }
        if(userService.validateUser(newPassword)){
            return "redirect:/update/password?pass=true";
        }
        if(!userService.validateNewPassword(newPassword, confirmPassword)){
            return "redirect:/update/password?newpass=true";
        }
        userService.updatePassword(user, newPassword);
        return "redirect:/update/password?success=true";

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
            return "redirect:/add/location?success=true";
        }
        return "redirect:/add/location?error=true";
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
            return "redirect:/add/triptype?success=true";
        }
        return "redirect:/add/triptype?error=true";
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
            return "redirect:/add/transporttype?success=true";
        }
        return "redirect:/add/transporttype?error=true";
    }

    @GetMapping("/delete/location")
    public String showDeleteLocationForm(Model model){
        List<Location> locations = locationService.findAll();
        model.addAttribute("locations", locations);
        return "admin/delete-location";
    }

    @PostMapping("/delete/location")
    public String processDeleteLocationForm(@RequestParam("location") String location){
        try {
            if(locationService.checkIfLocationExists(locationService.findLocationByName(location))){
                locationService.deleteLocation(locationService.findLocationByName(location));
                return "redirect:/delete/location?success=true";
            }
        } catch (Exception e){
            e.printStackTrace();
            return "redirect:/delete/location?error=true";
        }

        return "redirect:/delete/location?error=true";
    }



    @GetMapping("/delete/triptype")
    public String showDeleteTripTypeForm(Model model){
        List<TripType> tripTypes = tripTypeService.findAll();
        model.addAttribute("triptypes", tripTypes);
        return "admin/delete-triptype";
    }

    @PostMapping("/delete/triptype")
    public String processDeleteTripTypeForm(@RequestParam("trip-type") String tripType){

        try {
            if(tripTypeService.checkIfTripTypeExists(tripTypeService.findTripTypeByName(tripType))){
                tripTypeService.deleteTripType(tripTypeService.findTripTypeByName(tripType));
                return "redirect:/delete/triptype?success=true";
            }
        } catch (Exception e){
            e.printStackTrace();
            return "redirect:/delete/triptype?error=true";
        }

        return "redirect:/delete/triptype?error=true";
    }


    @GetMapping("/delete/transporttype")
    public String showDeleteTransportTypeForm(Model model){
        List<TransportType> transportTypes = transportTypeService.findAll();
        model.addAttribute("transporttypes", transportTypes);
        return "admin/delete-transporttype";
    }

    @PostMapping("/delete/transporttype")
    public String processDeleteTransformTypeForm(@RequestParam("transport-type") String transportType){
        try{
            if(transportTypeService.checkIfTransportTypeExists(transportTypeService.findTransportTypeByName(transportType))){
                transportTypeService.deleteTransportType(transportTypeService.findTransportTypeByName(transportType));
                return "redirect:/delete/transporttype?success=true";
            }
        } catch (Exception e){
            e.printStackTrace();
            return "redirect:/delete/transporttype?error=true";
        }

        return "redirect:/delete/transporttype?error=true";
    }


    @GetMapping("/update/cashier")
    public String showUpdateCashierForm(Model model){
        List<Cashier> cashiers = cashierService.findAllCashier();
        model.addAttribute("cashiers", cashiers);
        return "admin/update-cashier";
    }

    @PostMapping("/update/cashier")
    public String processUpdateCashierForm(@RequestParam("selectedCashier") Integer cashierId,
                                           @RequestParam("honorarium") Double honorarium){

        Cashier cashier = cashierService.findCashierById(cashierId);
        cashier.setHonorarium(honorarium);
        cashierService.saveCashier(cashier);
        return "redirect:/update/cashier";
    }

    @GetMapping("/update/company")
    public String showUpdateCompanyForm(Model model){
        List<Company> companies = companyService.findAllCompanies();
        model.addAttribute("companies", companies);
        return "admin/update-company";
    }

    @PostMapping("/update/company")
    public String processUpdateCompanyForm(@RequestParam("selectedCompany") Integer companyId,
                                           @RequestParam("honorarium") Double honorarium){

        Company company = companyService.findCompanyById(companyId);
        company.setHonorarium(honorarium);
        companyService.saveCompany(company);
        return "redirect:/update/company";
    }

    @GetMapping("/update/distributor")
    public String showUpdateDistributorForm(Model model){
        List<Distributor> distributors = distributorService.findAllDistributors();
        model.addAttribute("distributors", distributors);
        return "admin/update-distributor";
    }

    @PostMapping("/update/distributor")
    public String processUpdateDistributorForm(@RequestParam("selectedDistributor") Integer distributorId,
                                           @RequestParam("honorarium") Double honorarium){

        Distributor distributor = distributorService.findDistributorById(distributorId);
        distributor.setHonorarium(honorarium);
        distributorService.saveDistributor(distributor);
        return "redirect:/update/distributor";
    }
}
