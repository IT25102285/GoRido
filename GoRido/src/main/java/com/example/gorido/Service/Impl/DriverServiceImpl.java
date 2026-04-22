package com.example.gorido.Service.Impl;

import com.example.gorido.Model.User;
import com.example.gorido.Model.VehicleBrand;
import com.example.gorido.Model.VehicleColor;
import com.example.gorido.Model.VehicleType;
import com.example.gorido.Repository.*;
import com.example.gorido.Service.DriverService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Service
public class DriverServiceImpl implements DriverService {
    private final UserRepository userRepository;
    private final VehicleTypeRepository vehicleTypeRepository;
    private final VehicleColorRepository vehicleColorRepository;
    private final VehicleBrandRepository vehicleBrandRepository;
    private final TypeHasBrandRepository typeHasBrandRepository;

    public DriverServiceImpl(UserRepository userRepository, VehicleTypeRepository vehicleTypeRepository,
                             VehicleColorRepository vehicleColorRepository, VehicleBrandRepository vehicleBrandRepository,
                             TypeHasBrandRepository typeHasBrandRepository){
        this.userRepository = userRepository;
        this.vehicleBrandRepository = vehicleBrandRepository;
        this.vehicleColorRepository = vehicleColorRepository;
        this.vehicleTypeRepository = vehicleTypeRepository;
        this.typeHasBrandRepository = typeHasBrandRepository;
    }

    public String loadUser(HttpSession session){
        String email = (String) session.getAttribute("userEmail");

        if (email == null){
            return "not logged";
        }

        User user = userRepository.findByEmail(email).orElse(null);

        if (user == null){
            return "User not found";
        }

        return user.getEmail() + "|" + user.getMobile_number();
    }

    public String loadOptions(){
        List<VehicleType> vehicleType = vehicleTypeRepository.findAll();
        List<VehicleColor> vehicleColor = vehicleColorRepository.findAll();

        StringBuilder typeStr = new StringBuilder();
        for (VehicleType t : vehicleType){
            typeStr.append(t.getId()).append(":").append(t.getName()).append(",");
        }

        StringBuilder colorStr = new StringBuilder();
        for (VehicleColor c : vehicleColor){
            colorStr.append(c.getId()).append(":").append(c.getName()).append(",");
        }

        return typeStr.toString() + "|" + colorStr.toString();
    }

    public String loadBrands(@RequestParam int typeId) {

        List<VehicleBrand> brands = vehicleBrandRepository.findBrandsByTypeId(typeId);

        StringBuilder brandStr = new StringBuilder();

        for (VehicleBrand b : brands) {
            brandStr.append(b.getId())
                    .append(":")
                    .append(b.getName())
                    .append(",");
        }

        return brandStr.toString();
    }

}
