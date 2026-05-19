package com.example.gorido.Service.Impl;

import com.example.gorido.Model.*;
import com.example.gorido.Repository.*;
import com.example.gorido.Service.AdminService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class AdminServiceImpl implements AdminService {
    private final AdminRepository adminRepository;
    private final StatusRepository statusRepository;
    private final VehicleRepository vehicleRepository;
    private final UserRepository userRepository;
    private final DriverRepository driverRepository;
    private final AdminStatusRepository adminStatusRepository;
    private final HireRepository hireRepository;

    public AdminServiceImpl(AdminRepository adminRepository, VehicleRepository vehicleRepository, UserRepository userRepository,
                            DriverRepository driverRepository, AdminStatusRepository adminStatusRepository, HireRepository hireRepository, StatusRepository statusRepository){
        this.vehicleRepository = vehicleRepository;
        this.userRepository = userRepository;
        this.driverRepository = driverRepository;
        this.adminStatusRepository = adminStatusRepository;
        this.adminRepository = adminRepository;
        this.hireRepository = hireRepository;
        this.statusRepository = statusRepository;
    }

    public String adminSignin(String email, String password, HttpSession session){
        email = email.toLowerCase();

        Optional<Admin> adminObj = adminRepository.findByEmail(email);
        if (adminObj.isEmpty()){
            return "Admin not found, Try again";
        }

        Admin admin = adminObj.get();

        if (admin.getPassword() == null || !admin.getPassword().equals(password)) {
            return "Incorrect Password, Try again";
        }

        if (admin.getAdminStatus() != null && admin.getAdminStatus().getId() == 3) {
            return "Your account has been deactivated";
        }

        session.setAttribute("adminId", admin.getId());
        session.setAttribute("adminEmail", admin.getEmail());

        return "success";
    }

    public String logoutAdmin(HttpSession session){
        session.invalidate();
        return "success";
    }

    public String adminDashboard(Model model, HttpSession session){
        String email = (String) session.getAttribute("adminEmail");

        if (email == null) {
            return "redirect:/adminSignin";
        }

        Admin admin = adminRepository.findByEmail(email).orElse(null);

        if (admin == null) {
            return "redirect:/adminSignin";
        }

        List<User> users = userRepository.findAll();
        List<Driver> drivers = driverRepository.findAll();
        List<Vehicle> vehicles = vehicleRepository.findAll();
        List<Hire> hires = hireRepository.findAll();
        model.addAttribute("users", users);
        model.addAttribute("drivers", drivers);
        model.addAttribute("vehicles", vehicles);
        model.addAttribute("totHires", hires);
        model.addAttribute("hires", hireRepository.findLatest10Hires());
        long todayRides = hireRepository.countTodayRides();
        model.addAttribute("todayRides", todayRides);
        model.addAttribute("activePage", "adminDashboard");

        return "adminDashboard";
    }

    public String addAdmin(String first_name, String last_name, String email, String password, HttpSession session){

        Optional<Admin> activeAdmin = adminRepository.findByEmail(email);
        if (activeAdmin.isPresent()) {
            return "You already have an account";
        }

        Optional<AdminStatus> adminStatus = adminStatusRepository.findById(2);
        if (adminStatus.isEmpty()) {
            return "error: Status not found";
        }

        Optional<Status> status = statusRepository.findById(1);
        if (status.isEmpty()) {
            return "error: Status not found";
        }

        Admin admin = new Admin();
        admin.setFirstName(first_name);
        admin.setLastName(last_name);
        admin.setEmail(email);
        admin.setPassword(password);
        admin.setAdminStatus(adminStatus.get());
        admin.setStatus(status.get());
        admin.setRegisteredDate(new Date());
        adminRepository.save(admin);

        return "success";
    }

    public String manageAdmin(Model model, HttpSession session){
        String email = (String) session.getAttribute("adminEmail");

        if (email == null) {
            return "redirect:/adminSignin";
        }

        List<Admin> admins = adminRepository.findAllExceptEmail(email);
        model.addAttribute("admins", admins);
        model.addAttribute("activePage", "manageAdmin");

        return "manageAdmin";
    }

    public String changeStatus(HttpSession session, String email){

        String adminEmail = (String) session.getAttribute("adminEmail");
        if (adminEmail == null) return "Session expired";

        Admin admin = adminRepository.findByEmail(email).orElse(null);
        if (admin == null) return "Admin not found";

        Optional<Status> activeStatus = statusRepository.findById(1);

        Optional<Status> deactiveStatus = statusRepository.findById(2);

        if (activeStatus.isEmpty() || deactiveStatus.isEmpty()) {
            return "error: Status not found";
        }

        int status = admin.getStatus().getId();

        if (status == 1){
            admin.setStatus(deactiveStatus.get());
        } else if (status == 2){
            admin.setStatus(activeStatus.get());
        }

        adminRepository.save(admin);

        return "success";
    }

    public String delete(HttpSession session, String email){

        String adminEmail = (String) session.getAttribute("adminEmail");
        if (adminEmail == null) return "Session expired";

        Admin admin = adminRepository.findByEmail(email).orElse(null);
        if (admin == null) return "Admin not found";

        adminRepository.delete(admin);

        return "success";
    }

    public String updateAdmin(String first_name, String last_name, String oldEmail, String email, String password, HttpSession session){
        String adminEmail = (String) session.getAttribute("adminEmail");
        if (adminEmail == null) return "Session expired";

        Admin admin = adminRepository.findByEmail(oldEmail).orElse(null);
        if (admin == null) return "Admin not found";

        Optional<Admin> activeAdmin = adminRepository.findByEmailAndNotOldEmail(email, oldEmail);
        if (activeAdmin.isPresent()) {
            return "Email already in use";
        }

        admin.setFirstName(first_name);
        admin.setLastName(last_name);
        admin.setEmail(email);
        admin.setPassword(password);
        adminRepository.save(admin);

        return "success";
    }

    public String manageDrivers(Model model, HttpSession session){
        String email = (String) session.getAttribute("adminEmail");

        if (email == null) {
            return "redirect:/adminSignin";
        }

        List<Driver> drivers = driverRepository.findAll();
        model.addAttribute("drivers", drivers);
        model.addAttribute("activePage", "manageDrivers");

        return "manageDrivers";
    }

    public String changeDriverStatus(HttpSession session, String email){

        String adminEmail = (String) session.getAttribute("adminEmail");
        if (adminEmail == null) return "Session expired";

        User user = userRepository.findByEmail(email).orElse(null);
        if (user == null) return "User not found";

        Driver driver = user.getDriver();
        if (driver == null) return "Driver not found";

        Optional<Status> activeStatus = statusRepository.findById(1);
        Optional<Status> deactiveStatus = statusRepository.findById(2);

        if (activeStatus.isEmpty() || deactiveStatus.isEmpty()) {
            return "error: Status not found";
        }

        int status = driver.getStatusId().getId();

        if (status == 1){
            driver.setStatusId(deactiveStatus.get());
        } else if (status == 2){
            driver.setStatusId(activeStatus.get());
        }

        driverRepository.save(driver);

        return "success";
    }

    public String manageUsers(Model model, HttpSession session){
        String email = (String) session.getAttribute("adminEmail");

        if (email == null) {
            return "redirect:/adminSignin";
        }

        List<User> users = userRepository.findAll();
        model.addAttribute("users", users);
        model.addAttribute("activePage", "manageUsers");

        return "manageUsers";
    }

    public String changeUserStatus(HttpSession session, String email){
        String adminEmail = (String) session.getAttribute("adminEmail");
        if (adminEmail == null) return "Session expired";

        User user = userRepository.findByEmail(email).orElse(null);
        if (user == null) return "User not found";

        Optional<Status> activeStatus = statusRepository.findById(1);
        Optional<Status> deactiveStatus = statusRepository.findById(2);

        if (activeStatus.isEmpty() || deactiveStatus.isEmpty()) {
            return "error: Status not found";
        }

        int status = user.getStatusId().getId();

        if (status == 1){
            user.setStatusId(deactiveStatus.get());
        } else if (status == 2){
            user.setStatusId(activeStatus.get());
        }

        userRepository.save(user);

        return "success";
    }

    public String manageVehicles(Model model, HttpSession session){
        String email = (String) session.getAttribute("adminEmail");

        if (email == null) {
            return "redirect:/adminSignin";
        }

        List<Vehicle> vehicles = vehicleRepository.findAll();
        model.addAttribute("vehicles", vehicles);
        model.addAttribute("activePage", "manageVehiclesAdmin");

        return "manageVehiclesAdmin";
    }

    public String changeVehicleStatus(HttpSession session, int id){
        String adminEmail = (String) session.getAttribute("adminEmail");
        if (adminEmail == null) return "Session expired";

        Vehicle vehicle = vehicleRepository.findById(id).orElse(null);
        if (vehicle == null) return "vehicle not found";

        Optional<Status> activeStatus = statusRepository.findById(1);
        Optional<Status> deactiveStatus = statusRepository.findById(2);

        if (activeStatus.isEmpty() || deactiveStatus.isEmpty()) {
            return "error: Status not found";
        }

        int status = vehicle.getStatusId().getId();

        Driver driver = vehicle.getDriverId();

        if (status == 1) {

            vehicle.setStatusId(deactiveStatus.get());
            vehicleRepository.save(vehicle);

            int activeCount = vehicleRepository.countActiveVehiclesByDriverId(driver.getId(), 1);

            if (activeCount == 0) {
                driver.setStatusId(deactiveStatus.get());
                driverRepository.save(driver);
            }

        } else if (status == 2) {

            vehicle.setStatusId(activeStatus.get());
            vehicleRepository.save(vehicle);

            int activeCount = vehicleRepository.countActiveVehiclesByDriverId(driver.getId(), 1);

            if (activeCount > 0) {
                driver.setStatusId(activeStatus.get());
                driverRepository.save(driver);
            }
        }

        vehicleRepository.save(vehicle);

        return "success";
    }

    public String manageBookings(Model model, HttpSession session){
        String email = (String) session.getAttribute("adminEmail");

        if (email == null) {
            return "redirect:/adminSignin";
        }

        LocalDateTime tomorrow = LocalDate.now().plusDays(1).atStartOfDay();
        List<Hire> upcomingHires = hireRepository.findUpcomingHiresForAdmin(tomorrow);
        List<Hire> todayHires = hireRepository.findTodayHiresForAdmin();
        List<Hire> oldHires = hireRepository.findPastHiresForAdmin();

        long pending = hireRepository.pending();
        long confirmed = hireRepository.confirmed();
        long cancelled = hireRepository.cancelled();
        long completed = hireRepository.completed();

        model.addAttribute("todayHires", todayHires);
        model.addAttribute("hires", upcomingHires);
        model.addAttribute("oldHires", oldHires);
        model.addAttribute("now", LocalDateTime.now());
        model.addAttribute("pending", pending);
        model.addAttribute("confirmed", confirmed);
        model.addAttribute("cancelled", cancelled);
        model.addAttribute("completed", completed);
        model.addAttribute("activePage", "manageBookings");
        return "manageBookings";
    }
}
