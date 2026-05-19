package com.example.gorido.Service;

import jakarta.servlet.http.HttpSession;
import org.springframework.ui.Model;

public interface PaymentService {
    String payHire(Model model, int id, HttpSession session);
    String cashpayment(HttpSession session, int hireId, double totalFare);
    String cardPayment(int hireId, double totalFare, String cardHolder, String cardNumber, String exp, String cvv, HttpSession session);
    String loadInvoice(int hireId, Model model, HttpSession session);
    String paymentHistryPage(Model model, HttpSession session);
}
