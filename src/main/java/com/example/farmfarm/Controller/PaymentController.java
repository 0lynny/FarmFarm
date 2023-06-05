package com.example.farmfarm.Controller;

import com.example.farmfarm.Entity.OrderEntity;
import com.example.farmfarm.Entity.kakaoPay.ApprovePaymentEntity;
import com.example.farmfarm.Entity.UserEntity;
import com.example.farmfarm.Entity.kakaoPay.KakaoReadyResponse;
import com.example.farmfarm.Entity.kakaoPay.RefundPaymentEntity;
import com.example.farmfarm.Service.OrderService;
import com.example.farmfarm.Service.PaymentService;
import com.example.farmfarm.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/pay")
public class PaymentController {
    @Autowired
    private PaymentService paymentService;
    @Autowired
    private UserService userService;
    @Autowired
    private OrderService orderService;

    @PostMapping("/order/{oId}")
    public ResponseEntity<Object> pay(HttpServletRequest request, @PathVariable("oId") long oId)  {
        OrderEntity order = orderService.getOrder(oId);
        KakaoReadyResponse kakaoReadyResponse = paymentService.kakaoPayReady(order);
        return ResponseEntity.ok().body(kakaoReadyResponse);
    }

    @GetMapping("/success/{oId}")
    public ResponseEntity<Object> afterPayRequest(@RequestParam("pg_token") String pgToken, @PathVariable("oId") long oId) {
        OrderEntity order = orderService.getOrder(oId);
        ApprovePaymentEntity kakaoApprove = paymentService.approveResponse(order, pgToken);

        return new ResponseEntity<>(kakaoApprove, HttpStatus.OK);
    }

    @GetMapping("/cancel")
    public void cancel() {
        System.out.println("결제 실패");
    }

    @GetMapping("/fail")
    public void fail() {
        System.out.println("결제 실패");
    }

    @PostMapping("/refund")
    public ResponseEntity refund() {

        RefundPaymentEntity kakaoCancelResponse = paymentService.kakaoRefund();

        return new ResponseEntity<>(kakaoCancelResponse, HttpStatus.OK);
    }
}
