package com.example.gorido.Controller;

import com.example.gorido.Service.PaymentService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class PaymentController {
    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService){
        this.paymentService = paymentService;
    }

    @GetMapping("/payment/ui")
    public String payHire(Model model, @RequestParam int id, HttpSession session){
        return paymentService.payHire(model, id, session);
    }

    @PostMapping("/hire/payCash")
    @ResponseBody
    public String cashpayment(@RequestParam int hireId, @RequestParam double totalFare, HttpSession session){
        return paymentService.cashpayment(session, hireId, totalFare);
    }

    @PostMapping("/hire/payCard")
    @ResponseBody
    public String cardPayment(@RequestParam int hireId, @RequestParam double totalFare, @RequestParam String cardHolder, @RequestParam String cardNumber,
                              @RequestParam String expiry, @RequestParam String cvv, HttpSession session) {

        return paymentService.cardPayment(hireId, totalFare, cardHolder, cardNumber, expiry, cvv, session);
    }

    @GetMapping("/invoice")
    public String loadInvoice(@RequestParam int hireId, Model model, HttpSession session){
        return paymentService.loadInvoice(hireId, model, session);
    }

    @GetMapping("/paymentHistory")
    public String paymentHistory(Model model, HttpSession session){
        return paymentService.paymentHistryPage(model, session);
    }
}
