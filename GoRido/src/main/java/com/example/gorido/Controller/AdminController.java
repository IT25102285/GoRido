package com.example.gorido.Controller;
import com.example.gorido.Service.AdminService;
import com.example.gorido.Service.AuthService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class AdminController {
    private final AdminService adminService;

    public AdminController(AdminService adminService){
        this.adminService = adminService;
    }

    @GetMapping("/adminDashboard")
    public String admindashboard(Model model, HttpSession session){
        return adminService.adminDashboard(model, session);
    }

    @GetMapping("/adminSignin")
    public String adminSignin(){
        return "adminSignin";
    }

    @PostMapping("/adminSignin")
    @ResponseBody
    public String adminSignin(@RequestParam String email,
                         @RequestParam String password,
                         HttpSession session){
        return adminService.adminSignin(email, password, session);
    }

    @RestController
    public class adminLogout{
        @GetMapping("/logout/admin")
        public String logoutAdmin(HttpSession session){
            return adminService.logoutAdmin(session);
        }
    }

    @GetMapping("/manageAdmin")
    public String manageAdmin(Model model, HttpSession session){
        return adminService.manageAdmin(model, session);
    }

    @GetMapping("/manageDrivers")
    public String manageDrivers(Model model, HttpSession session){
        return adminService.manageDrivers(model, session);
    }

    @GetMapping("/manageUsers")
    public String manageUsers(Model model, HttpSession session){
        return adminService.manageUsers(model, session);
    }

    @GetMapping("/manageBookings")
    public String manageBookings(Model model, HttpSession session){
        return adminService.manageBookings(model, session);
    }

    @GetMapping("/manageVehiclesAdmin")
    public String manageVehicles(Model model, HttpSession session){
        return adminService.manageVehicles(model, session);
    }

    @RestController
    public class addAdmin{
        @PostMapping("/addAdmin")
        public String addNewAdmin(@RequestParam String first_name, @RequestParam String last_name, @RequestParam String email, @RequestParam String password,
                                 HttpSession session){
            return adminService.addAdmin(first_name, last_name, email, password, session);
        }
    }

    @PostMapping("/manageAdmin/changeStatus")
    @ResponseBody
    public String changeStatus(@RequestParam String email, HttpSession session){
        return adminService.changeStatus(session, email);
    }

    @PostMapping("/manageAdmin/delete")
    @ResponseBody
    public String deleteAdmin(@RequestParam String email, HttpSession session){
        return adminService.delete(session, email);
    }

    @PostMapping("/updateAdmin")
    @ResponseBody
    public String updateAdmin(@RequestParam String first_name, @RequestParam String last_name,
                              @RequestParam String oldEmail, @RequestParam String email, @RequestParam String password,
                                 HttpSession session){
        return adminService.updateAdmin(first_name, last_name, oldEmail, email, password, session);
    }

    @PostMapping("/manageDriver/changeStatus")
    @ResponseBody
    public String changeDriverStatus(@RequestParam String email, HttpSession session){
        return adminService.changeDriverStatus(session, email);
    }

    @PostMapping("/manageUser/changeStatus")
    @ResponseBody
    public String changeUserStatus(@RequestParam String email, HttpSession session){
        return adminService.changeUserStatus(session, email);
    }

    @PostMapping("/manageVehicle/changeStatus")
    @ResponseBody
    public String changeVehicleStatus(@RequestParam int id, HttpSession session){
        return adminService.changeVehicleStatus(session, id);
    }
}
