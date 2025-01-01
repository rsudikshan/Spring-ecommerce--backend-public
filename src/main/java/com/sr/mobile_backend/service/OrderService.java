package com.sr.mobile_backend.service;

import com.sr.mobile_backend.dtos.OrderDetailsDto;
import com.sr.mobile_backend.dtos.MainPaymentDto;
import com.sr.mobile_backend.dtos.PaymentWrapperDto;
import com.sr.mobile_backend.entity.Client;
import com.sr.mobile_backend.entity.OrderedItemDetails;
import com.sr.mobile_backend.entity.Orders;
import com.sr.mobile_backend.entity.Product;
import com.sr.mobile_backend.enums.PaymentMethod;
import com.sr.mobile_backend.enums.PaymentStatus;
import com.sr.mobile_backend.repository.ClientRepo;
import com.sr.mobile_backend.repository.OrderDetailsRepo;
import com.sr.mobile_backend.repository.OrderRepo;
import com.sr.mobile_backend.repository.ProductRepo;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

@Service
public class OrderService {

    OrderRepo orderRepo;
    OrderDetailsRepo orderDetailsRepo;
    ClientRepo clientRepo;
    ProductRepo productRepo;

    OrderService(OrderRepo orderRepo,
                 OrderDetailsRepo orderDetailsRepo,
                 ClientRepo clientRepo,
                 ProductRepo productRepo){
        this.orderRepo = orderRepo;
        this.orderDetailsRepo = orderDetailsRepo;
        this.clientRepo = clientRepo;
        this.productRepo = productRepo;
    }


    public MainPaymentDto saveTransaction(PaymentWrapperDto paymentWrapperDto){
        MainPaymentDto mainPaymentDto = paymentWrapperDto.getMainPaymentDto();
        OrderDetailsDto orderDetailsDto = paymentWrapperDto.getOrderDetailsDto();
        Authentication authentication = getAuthenticatedEntity();
        String username = authentication.getName();
        System.out.println(username);

        Optional<Product> product = productRepo.findById(orderDetailsDto.getId());


        if(clientRepo.findByUsername(username).isPresent()){
            if(product.isPresent()){
                System.out.println("this is hitting");
                Client client = clientRepo.findByUsername(username).get();
                Orders orders = orderDtoToEntityConverterHelper(mainPaymentDto,client);
                orderRepo.save(orders);



                OrderedItemDetails itemDetails = orderedItemDetailsDtoToEntityConverterHelper(
                        orderDetailsDto,client,product.get()
                );
                orderDetailsRepo.save(itemDetails);


                //response
                mainPaymentDto.setClientEmail(client.getEmail());
                mainPaymentDto.setServerDate(LocalDate.now().toString());
                mainPaymentDto.setServerTime(LocalDate.now().toString());
                return mainPaymentDto;
            }
        }
        System.out.println("ORDER DID NOT HIT");
        return null;
    }

    private Authentication getAuthenticatedEntity(){

        return SecurityContextHolder.getContext().getAuthentication();
    }



    private Orders orderDtoToEntityConverterHelper(MainPaymentDto mainPaymentDto, Client client){
        return Orders.builder()
                .date(LocalDate.now())
                .time(LocalTime.now())
                .clientEmail(client)//need to pull this
                .paymentMethod(PaymentMethod.valueOf(mainPaymentDto.getPaymentMethod()))
                .paymentStatus(PaymentStatus.valueOf(mainPaymentDto.getPaymentStatus()))
                .build();
    }

    private OrderedItemDetails orderedItemDetailsDtoToEntityConverterHelper(OrderDetailsDto dto
            ,Client client,Product product){

        return OrderedItemDetails.builder()
                .productId(product)
                .orgName(product)
                .count(dto.getCount())
                .totalPrice(dto.getTotalPrice())//need to set this server side
                //.serverTotalPrice(null) need to configure it admin side
                .localDate(LocalDate.now())
                .localTime(LocalTime.now())
                .clientEmail(client)
                .build();
    }




}
