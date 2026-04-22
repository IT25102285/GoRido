package com.example.gorido;
import com.example.gorido.Model.*;
import com.example.gorido.Repository.*;
import com.example.gorido.Service.DriverService;
import com.example.gorido.Service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Controller
public class HomeController {
    private final UserService userService;
    private final DriverService driverService;

    public HomeController(UserService userService, DriverService driverService) {
        this.userService = userService;
        this.driverService = driverService;
    }

    @GetMapping("/signup")
    public String signupPage(Model model) {
        model.addAttribute("user", new User());
        return "signup";
    }

    @GetMapping("/driverregi")
    public String driverRegiPage() {
        return "driverregi";
    }

    @PostMapping("/signup")
    @ResponseBody
    public String signup(@ModelAttribute User user) {
        return userService.signup(user);
    }

    @GetMapping("/signup/options")
    @ResponseBody
    public String getAllGenders() {
        return userService.getAllGenders();
    }

    @GetMapping("/signin")
    public String signinPage() {
        return "signin";
    }

    @PostMapping("/signin")
    @ResponseBody
    public String signin(@RequestParam String email,
                         @RequestParam String password,
                         HttpSession session){
        return userService.signin(email, password, session);
    }

    @RestController
    public class ForgotPasswordController{

        @PostMapping("/send_code")
        public String sendcode(@RequestParam String email){
            return userService.sendCode(email);
        }

        @PostMapping("/reset_password")
        public String resetPassword(@RequestParam String email,
                                    @RequestParam String code,
                                    @RequestParam String newPassword){
            return userService.resetPassword(email, code, newPassword);
        }
    }

    @RestController
    public class driverRegistrationController{
        @GetMapping("/driver/loadUser")
        public String loadUser(HttpSession session){
            return driverService.loadUser(session);
        }

        @GetMapping("/driver/loadDriverOptions")
        @ResponseBody
        public String loadOptions(){
            return driverService.loadOptions();
        }

        @GetMapping("/driver/loadBrands")
        @ResponseBody
        public String loadBrands(@RequestParam int typeId) {
            return driverService.loadBrands(typeId);
        }
    }
}
