package com.sr.mobile_backend.controllers;

import com.sr.mobile_backend.dtos.OrderDetailsDto;
import com.sr.mobile_backend.dtos.MainPaymentDto;
import com.sr.mobile_backend.dtos.PaymentWrapperDto;
import com.sr.mobile_backend.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/payment")
public class OrderController {


    OrderService orderService;

    OrderController(OrderService orderService){
        this.orderService = orderService;
    }



    @PostMapping("/setOrder")
    public ResponseEntity<MainPaymentDto> orderAndSetPayment
            (@RequestBody PaymentWrapperDto paymentWrapperDto ){
        System.out.println(paymentWrapperDto.getMainPaymentDto().getPaymentMethod());
        return ResponseEntity.ok().body(orderService.saveTransaction(paymentWrapperDto));
    }


}
