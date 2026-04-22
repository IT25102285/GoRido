package com.example.gorido.Service.Impl;

import com.example.gorido.Model.*;
import com.example.gorido.Repository.GenderRepository;
import com.example.gorido.Repository.StatusRepository;
import com.example.gorido.Repository.TypeRepository;
import com.example.gorido.Repository.UserRepository;
import com.example.gorido.Service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final StatusRepository statusRepository;
    private final TypeRepository typeRepository;
    private final GenderRepository genderRepository;
    private final EmailService emailService;

    public UserServiceImpl(UserRepository userRepository,
                       StatusRepository statusRepository,
                       TypeRepository typeRepository,
                       GenderRepository genderRepository, EmailService emailService) {
        this.userRepository = userRepository;
        this.statusRepository = statusRepository;
        this.typeRepository = typeRepository;
        this.genderRepository = genderRepository;
        this.emailService = emailService;
    }

    public String getAllGenders() {

        List<Gender> genders = genderRepository.findAll();

        StringBuilder gen = new StringBuilder();

        for (Gender gender : genders) {
            gen.append(gender.getId())
                    .append(":")
                    .append(gender.getName())
                    .append(",");
        }

        return gen.toString();
    }

    public String signup(User user){
        if (user.getEmail() != null) {
            user.setEmail(user.getEmail().toLowerCase());
        }

        if (user.getFirst_name() == null || user.getFirst_name().trim().isEmpty()) {
            return "error: First name required";
        }

        if (user.getLast_name() == null || user.getLast_name().trim().isEmpty()) {
            return "error: Last name required";
        }

        if (user.getEmail() == null ||
                !user.getEmail().matches("^[^\\s@]+@[^\\s@]+\\.[^\\s@]+$")) {
            return "error: Invalid email";
        }

        Optional<User> activeUser = userRepository.findByEmailAndStatusId_Id(user.getEmail(), 1);

        if (activeUser.isPresent()) {
            return "You already have an account";
        }

        Optional<User> deactiveUser = userRepository.findByEmailAndStatusId_Id(user.getEmail(), 2);

        if (deactiveUser.isPresent()) {
            return "You account is deactivated";
        }

        if (user.getPassword() == null || user.getPassword().length() < 6) {
            return "error: Password must be at least 6 characters";
        }

        if (user.getMobile_number() == null ||
                !user.getMobile_number().trim().matches("^[0-9]{10}$")) {
            return "error: Invalid mobile number";
        }

        Optional<Status> status = statusRepository.findById(1);
        if (status.isEmpty()) {
            return "error: Status not found";
        }

        Optional<Type> userType = typeRepository.findById(1);
        if (userType.isEmpty()){
            return "error: User type not found";
        }

        if (user.getGender_id() == null || user.getGender_id().getId() == 0) {
            return "error: Gender required";
        }

        Optional<Gender> gender = genderRepository.findById(user.getGender_id().getId());
        if (gender.isEmpty()) {
            return "error: Gender not found";
        }

        user.setTypeId(userType.get());
        user.setStatusId(status.get());
        user.setGender_id(gender.get());
        user.setJoined_date(LocalDateTime.now());
        userRepository.save(user);

        return "success";
    }

    public String signin(String email, String password, HttpSession session){
        email = email.toLowerCase();

        Optional<User> userObj = userRepository.findByEmail(email);
        if (userObj.isEmpty()){
            return "User not found, Try again";
        }

        User user = userObj.get();

        if (user.getPassword() == null || !user.getPassword().equals(password)) {
            return "Incorrect Password, Try again";
        }

        if (user.getStatusId() != null && user.getStatusId().getId() == 2) {
            return "Your account has been deactivated";
        }

        session.setAttribute("userId", user.getId());
        session.setAttribute("userEmail", user.getEmail());

        return "success";
    }

    public String sendCode(String email){
        Optional<User> userOpt = userRepository.findByEmail(email);

        if (userOpt.isEmpty()){
            return "User not found, Try again";
        }

        int code = new Random().nextInt(900000)+100000;
        User user = userOpt.get();
        user.setVerificationCode(String.valueOf(code));

        userRepository.save(user);
        emailService.sendCode(email, String.valueOf(code));

        return "success";
    }

    public String resetPassword(String email, String code, String newPassword){
        User user = userRepository.findByEmail(email).orElse(null);

        if (user == null) return "User not found";

        if (user.getVerificationCode() == null || !user.getVerificationCode().equals(code)) {
            return "Invalid code, Try again";
        }

        user.setPassword(newPassword);
        userRepository.save(user);

        return "success";
    }
}