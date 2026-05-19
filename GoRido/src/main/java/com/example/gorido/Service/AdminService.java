package com.example.gorido.Service;
import jakarta.servlet.http.HttpSession;
import org.springframework.ui.Model;

public interface AdminService {
    String adminSignin(String email, String password, HttpSession session);
    String logoutAdmin(HttpSession session);
    String adminDashboard(Model model, HttpSession session);
    String addAdmin(String first_name, String last_name, String email, String password, HttpSession session);
    String manageAdmin(Model model, HttpSession session);
    String changeStatus(HttpSession session, String email);
    String delete(HttpSession session, String email);
    String updateAdmin(String first_name, String last_name, String oldEmailString, String email, String password, HttpSession session);
    String manageDrivers(Model model, HttpSession session);
    String changeDriverStatus(HttpSession session, String email);
    String manageUsers(Model model, HttpSession session);
    String changeUserStatus(HttpSession session, String email);
    String manageVehicles(Model model, HttpSession session);
    String changeVehicleStatus(HttpSession session, int id);
    String manageBookings(Model model, HttpSession session);
}
