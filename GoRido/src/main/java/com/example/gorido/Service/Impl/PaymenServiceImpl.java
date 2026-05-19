package com.example.gorido.Service.Impl;

import com.example.gorido.Model.*;
import com.example.gorido.Repository.*;
import com.example.gorido.Service.PaymentService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class PaymenServiceImpl implements PaymentService {
    private final HireStatusRepository hireStatusRepository;
    private final UserRepository userRepository;
    private final DriverRepository driverRepository;
    private final HireRepository hireRepository;
    private final PaymentRepository paymentRepository;
    private final PaymentTypeRepository paymentTypeRepository;
    private final PaymentStatusRepository paymentStatusRepository;

    public PaymenServiceImpl(UserRepository userRepository,
                             DriverRepository driverRepository, HireRepository hireRepository,
                             PaymentRepository paymentRepository, PaymentTypeRepository paymentTypeRepository,
                             PaymentStatusRepository paymentStatusRepository, HireStatusRepository hireStatusRepository) {
        this.userRepository = userRepository;
        this.driverRepository = driverRepository;
        this.hireRepository = hireRepository;
        this.paymentRepository = paymentRepository;
        this.paymentStatusRepository = paymentStatusRepository;
        this.paymentTypeRepository = paymentTypeRepository;
        this.hireStatusRepository = hireStatusRepository;
    }

    public String payHire(Model model, int id, HttpSession session){
        String email = (String) session.getAttribute("userEmail");

        if (email == null){
            return "signin";
        }

        Hire hire = hireRepository.findById(id).orElse(null);

        model.addAttribute("hire", hire);
        return "payment";
    }

    public String cashpayment(HttpSession session, int hireId, double totalFare){
        String email = (String) session.getAttribute("userEmail");
        if (email == null) return "Session expired";

        Optional<PaymentStatus> pStatus = paymentStatusRepository.findById(3);
        if (pStatus.isEmpty()) {
            return "error: Payment status not found";
        }

        Optional<PaymentType> pType = paymentTypeRepository.findById(1);
        if (pType.isEmpty()) {
            return "error: Payment type not found";
        }

        Optional<Hire> hireOpt = hireRepository.findById(hireId);
        if (hireOpt.isEmpty()) {
            return "error: Hire not found";
        }

        Optional<HireStatus> status = hireStatusRepository.findById(1);
        if (status.isEmpty()) {
            return "Hire status not found";
        }

        Hire hire = hireOpt.get();
        Payment payment = new Payment();
        payment.setAmount(totalFare);
        payment.setPaidAt(new Date());
        payment.setPaymentStatus(pStatus.get());
        payment.setPaymentType(pType.get());
        paymentRepository.save(payment);


        hire.setHireStatus(status.get());
        hire.setPayment(payment);
        hireRepository.save(hire);
        return "success";
    }

    public String cardPayment(int hireId, double totalFare,
                              String cardHolder, String cardNumber,
                              String exp, String cvv,
                              HttpSession session) {

        String email = (String) session.getAttribute("userEmail");
        if (email == null) return "Session expired";

        Optional<PaymentStatus> pStatus = paymentStatusRepository.findById(1);
        if (pStatus.isEmpty()) return "error: Payment status not found";

        Optional<PaymentType> pType = paymentTypeRepository.findById(2);
        if (pType.isEmpty()) return "error: Payment type not found";

        Optional<Hire> hireOpt = hireRepository.findById(hireId);
        if (hireOpt.isEmpty()) return "error: Hire not found";

        Optional<HireStatus> status = hireStatusRepository.findById(1);
        if (status.isEmpty()) {
            return "Hire status not found";
        }

        Hire hire = hireOpt.get();

        Payment payment = new Payment();
        payment.setAmount(totalFare);
        payment.setCardHolder(cardHolder);

        payment.setTransactionId("TXN-" + System.currentTimeMillis());

        payment.setPaidAt(new Date());
        payment.setPaymentStatus(pStatus.get());
        payment.setPaymentType(pType.get());

        paymentRepository.save(payment);

        hire.setHireStatus(status.get());
        hire.setPayment(payment);
        hireRepository.save(hire);

        return "redirect:/invoice?hireId=" + hireId;
    }

    public String loadInvoice(int hireId, Model model, HttpSession session){
        String email = (String) session.getAttribute("userEmail");
        if (email == null) return "Session expired";

        Optional<Hire> hire = hireRepository.findById(hireId);
        if (hire.isEmpty()) {
            return "error: payment not found";
        }

        model.addAttribute("hire", hire.get());
        return "invoice";
    }

    public String paymentHistryPage(Model model, HttpSession session){
        Integer userId = (Integer) session.getAttribute("userId");

        if (userId == null) {
            return "redirect:/signin";
        }

        Optional<User> userOpt = userRepository.findById(userId);
        User user = userOpt.get();

        Optional<Driver> driver = driverRepository.findByUserId(user);

        List<Hire> hires = hireRepository.findByUserIdPaymentsHistoryAtDesc(user.getId());

        model.addAttribute("hires", hires);
        model.addAttribute("activePage", "paymentHistory");

        return "paymentHistory";
    }
}
