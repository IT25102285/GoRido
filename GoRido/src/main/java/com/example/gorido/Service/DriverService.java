package com.example.gorido.Service;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.RequestParam;

public interface DriverService {
    String loadUser(HttpSession session);
    String loadOptions();
    String loadBrands(@RequestParam int typeId);
}
