package com.example.gorido.Service;
import com.example.gorido.Model.User;
import jakarta.servlet.http.HttpSession;

public interface UserService {
    String signup(User user);
    String getAllGenders();
    String signin(String email, String password, HttpSession session);
    String sendCode(String email);
    String resetPassword(String email, String code, String newPassword);
}
